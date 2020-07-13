package ch.fhnw.speech_collection_app.features.base.user;

import ch.fhnw.speech_collection_app.features.email.EmailSenderService;
import ch.fhnw.speech_collection_app.jooq.enums.UserGroupRoleRole;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.*;
import ch.fhnw.speech_collection_app.jooq.tables.records.UserGroupRoleRecord;
import ch.fhnw.speech_collection_app.jooq.tables.records.UserRecord;
import com.google.common.io.Resources;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ch.fhnw.speech_collection_app.jooq.Tables.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final Map<String, String> zipCodes;
    private final EmailSenderService emailSenderService;
    private final DSLContext dslContext;

    @Autowired
    public CustomUserDetailsService(PasswordEncoder passwordEncoder, EmailSenderService emailSenderService,
                                    DSLContext dslContext) throws IOException {
        this.passwordEncoder = passwordEncoder;
        this.emailSenderService = emailSenderService;
        this.dslContext = dslContext;
        // based on https://download.geonames.org/export/zip/
        this.zipCodes = Resources.readLines(Resources.getResource("ch_zip_distinct.csv"), StandardCharsets.UTF_8)
                .stream().distinct().map(l -> l.split(",")).distinct().collect(Collectors.toMap(s -> s[0], s -> s[1]));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return dslContext.selectFrom(USER).where(USER.USERNAME.eq(username)).fetchOptionalInto(User.class).map(user -> {
            var userGroupRoles = dslContext.selectFrom(USER_GROUP_ROLE).where(USER_GROUP_ROLE.USER_ID.eq(user.getId()))
                    .fetchInto(UserGroupRole.class);
            var authorities = userGroupRoles.stream().map(userGroupRole -> userGroupRole.getRole().toString())
                    .distinct().toArray(String[]::new);
            return new CustomUserDetails(user, AuthorityUtils.createAuthorityList(authorities), userGroupRoles);
        }).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public boolean isAdmin() {
        return getLoggedInUser().getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .anyMatch(r -> r.equals("ADMIN"));
    }

    public Long getLoggedInUserId() {
        return getLoggedInUser().getUser().getId();
    }

    public Long getLoggedInUserDialectId() {
        return getLoggedInUser().getUser().getDialectId();
    }

    public boolean isAllowedOnProject(long userGroupId, boolean checkAdminPermission) {
        return isAdmin() || getLoggedInUser().userGroupRoles.stream()
                .anyMatch(userGroupRole -> userGroupRole.getUserGroupId() == userGroupId
                        && (!checkAdminPermission || userGroupRole.getRole() == UserGroupRoleRole.GROUP_ADMIN));
    }

    public CustomUserDetails getLoggedInUser() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public User getUser(long id) {
        return dslContext.selectFrom(USER).where(USER.ID.eq(id)).fetchOneInto(User.class);
    }

    private UserRecord getUserR(long id) {
        return dslContext.selectFrom(USER).where(USER.ID.eq(id)).fetchOne();
    }

    public void putUser(User user) {
        if (!getLoggedInUserId().equals(user.getId()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        user.setEnabled(true);
        checkDialect(user);
        dslContext.newRecord(USER, user).update();
        // on a production server we need to update the cached spring principal
        getLoggedInUser().setUser(user);
    }

    public void putPassword(ChangePassword changePassword) {
        UserRecord user = getUserR(getLoggedInUserId());
        if (passwordEncoder.matches(changePassword.getPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
            user.update();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "BAD_REQUEST");
        }
    }

    /**
     * check if the dialect is set if not it is selected using the zip code
     */
    private void checkDialect(User user) {
        if (user.getDialectId() == null) {
            var id = dslContext.selectFrom(DIALECT).where(DIALECT.COUNTY_ID.eq(zipCodes.get(user.getZipCode())))
                    .fetchOptional(DIALECT.ID).orElse(1L);
            user.setDialectId(id);
        }
    }

    public void register(User user) {
        checkDialect(user);
        UserRecord userRecord = dslContext.newRecord(USER, user);
        userRecord.setPassword(passwordEncoder.encode(userRecord.getPassword()));
        userRecord.setEnabled(false);
        userRecord.setGamificationOn(getGamification());
        userRecord.store();
        // add user to public group
        dslContext.batchInsert(new UserGroupRoleRecord(null, UserGroupRoleRole.USER, userRecord.getId(), 1L)).execute();
        emailSenderService.sendEmailConfirmation(userRecord);
    }

    public Boolean getGamification() {
        Long withGamification = dslContext.selectCount()
                .from(USER.join(USER_GROUP_ROLE).on(USER.ID.eq(USER_GROUP_ROLE.USER_ID)))
                .where(USER.GAMIFICATION_ON.eq(true).and(USER_GROUP_ROLE.USER_GROUP_ID.eq(1L))
                        .and(USER_GROUP_ROLE.ROLE.eq(UserGroupRoleRole.USER)))
                .limit(1).fetchOneInto(Long.class);
        Long withoutGamification = dslContext.selectCount()
                .from(USER.join(USER_GROUP_ROLE).on(USER.ID.eq(USER_GROUP_ROLE.USER_ID)))
                .where(USER.GAMIFICATION_ON.eq(false).and(USER_GROUP_ROLE.USER_GROUP_ID.eq(1L))
                        .and(USER_GROUP_ROLE.ROLE.eq(UserGroupRoleRole.USER)))
                .limit(1).fetchOneInto(Long.class);
        return withoutGamification > withGamification;
    }

    public boolean confirmEmail(String token) {
        return validateToken(token,
                t -> dslContext.update(USER).set(USER.ENABLED, true).where(USER.ID.eq(t.getUserId())).execute() == 1);
    }

    public void resendEmail(String email) {
        dslContext.selectFrom(USER).where(USER.EMAIL.eq(email)).fetchOptional()
                .ifPresent(emailSenderService::sendEmailConfirmation);
    }

    public boolean resetPassword(String token, String newPassword) {
        return validateToken(token, t -> dslContext.update(USER).set(USER.PASSWORD, passwordEncoder.encode(newPassword))
                .where(USER.ID.eq(t.getUserId())).execute() == 1);
    }

    public void resendPassword(String email) {
        dslContext.selectFrom(USER).where(USER.EMAIL.eq(email)).fetchOptional()
                .ifPresent(emailSenderService::sendResetPassword);
    }

    private boolean validateToken(String token, Function<VerificationToken, Boolean> onValid) {
        return dslContext.selectFrom(VERIFICATION_TOKEN).where(VERIFICATION_TOKEN.TOKEN.eq(token))
                .fetchOptionalInto(VerificationToken.class).map(t -> {
                    // NOTE tokens are only valid for one day and only once.
                    dslContext.delete(VERIFICATION_TOKEN).where(VERIFICATION_TOKEN.ID.eq(t.getId())).execute();
                    if (t.getCreatedTime().toLocalDateTime().isBefore(LocalDateTime.now().minusDays(1))) {
                        return false;
                    } else {
                        return onValid.apply(t);
                    }
                }).orElse(false);
    }

    public List<Dialect> getDialect() {
        return dslContext.selectFrom(DIALECT).fetchInto(Dialect.class);
    }

    public List<UserGroup> getUserGroup() {
        if (isAdmin()) {
            return dslContext.selectFrom(USER_GROUP).fetchInto(UserGroup.class);
        } else {
            var ids = getLoggedInUser().userGroupRoles.stream().map(UserGroupRole::getUserGroupId).distinct()
                    .toArray(Long[]::new);
            return dslContext.selectFrom(USER_GROUP).where(USER_GROUP.ID.in(ids)).fetchInto(UserGroup.class);
        }
    }

    public void updateLastLogin(Long id) {
        dslContext.update(USER).set(USER.LAST_ONLINE, DSL.currentTimestamp()).where(USER.ID.eq(id)).execute();
    }
}
