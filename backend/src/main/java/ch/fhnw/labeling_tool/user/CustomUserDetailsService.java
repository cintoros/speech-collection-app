package ch.fhnw.labeling_tool.user;

import ch.fhnw.labeling_tool.features.email.EmailSenderService;
import ch.fhnw.labeling_tool.jooq.enums.UserGroupRoleRole;
import ch.fhnw.labeling_tool.jooq.tables.daos.DialectDao;
import ch.fhnw.labeling_tool.jooq.tables.daos.UserDao;
import ch.fhnw.labeling_tool.jooq.tables.daos.UserGroupRoleDao;
import ch.fhnw.labeling_tool.jooq.tables.pojos.Dialect;
import ch.fhnw.labeling_tool.jooq.tables.pojos.User;
import ch.fhnw.labeling_tool.jooq.tables.pojos.UserGroupRole;
import ch.fhnw.labeling_tool.jooq.tables.records.UserRecord;
import com.google.common.io.Resources;
import org.jooq.DSLContext;
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
import java.util.Map;
import java.util.stream.Collectors;

import static ch.fhnw.labeling_tool.jooq.Tables.USER;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserDao userDao;
    private final UserGroupRoleDao userGroupRoleDao;
    private final PasswordEncoder passwordEncoder;
    private final Map<String, String> zipCodes;
    private final DialectDao dialectDao;
    private final EmailSenderService emailSenderService;
    private final DSLContext dslContext;

    @Autowired
    public CustomUserDetailsService(UserDao userDao, UserGroupRoleDao userGroupRoleDao, PasswordEncoder passwordEncoder, DialectDao dialectDao, EmailSenderService emailSenderService, DSLContext dslContext) throws IOException {
        this.userDao = userDao;
        this.userGroupRoleDao = userGroupRoleDao;
        this.passwordEncoder = passwordEncoder;
        this.dialectDao = dialectDao;
        this.emailSenderService = emailSenderService;
        this.dslContext = dslContext;
        //based on https://download.geonames.org/export/zip/
        this.zipCodes = Resources.readLines(Resources.getResource("ch_zip_distinct.csv"), StandardCharsets.UTF_8)
                .stream()
                .distinct()
                .map(l -> l.split(","))
                .distinct()
                .collect(Collectors.toMap(s -> s[0], s -> s[1]));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*TODO add custom error message if the user is not validated*/
        return userDao.fetchOptional(USER.USERNAME, username)
                .map(user -> {
                    var userGroupRoles = userGroupRoleDao.fetchByUserId(user.getId());
                    var authorities = userGroupRoles.stream().map(userGroupRole -> userGroupRole.getRole().toString()).distinct().toArray(String[]::new);
                    return new CustomUserDetails(user, AuthorityUtils.createAuthorityList(authorities), userGroupRoles);
                })
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public boolean isAdmin() {
        return getLoggedInUser().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(r -> r.equals("ADMIN"));
    }

    public Long getLoggedInUserId() {
        return getLoggedInUser().user.getId();
    }

    public Long getLoggedInUserDialectId() {
        return getLoggedInUser().user.getDialectId();
    }

    public boolean isAllowedOnProject(long userGroupId, boolean checkAdminPermission) {
        return isAdmin() || getLoggedInUser().userGroupRoles.stream()
                .anyMatch(userGroupRole -> userGroupRole.getUserGroupId() == userGroupId && (!checkAdminPermission || userGroupRole.getRole() == UserGroupRoleRole.GROUP_ADMIN));
    }

    public CustomUserDetails getLoggedInUser() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public User getUser(long id) {
        return userDao.findById(id);
    }

    public void putUser(User user) {
        if (!getLoggedInUserId().equals(user.getId()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        userDao.update(user);
    }

    public void putPassword(ChangePassword changePassword) {
        User user = userDao.fetchOneById(getLoggedInUserId());
        if (passwordEncoder.matches(changePassword.getPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
            userDao.update(user);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "BAD_REQUEST");
        }
    }

    public void register(User user) {
        UserRecord userRecord = dslContext.newRecord(USER, user);
        userRecord.setPassword(passwordEncoder.encode(userRecord.getPassword()));
        userRecord.setEnabled(false);
        if (userRecord.getDialectId() == null) {
            userRecord.setDialectId(dialectDao.fetchByCountyId(zipCodes.get(userRecord.getZipCode())).stream().map(Dialect::getId).findFirst().orElse(1L));
        }
        userRecord.store();
        //add user to public group
        userGroupRoleDao.insert(new UserGroupRole(null, UserGroupRoleRole.USER, userRecord.getId(), 1L));
        emailSenderService.sendEmailConfirmation(userRecord);
    }
}
