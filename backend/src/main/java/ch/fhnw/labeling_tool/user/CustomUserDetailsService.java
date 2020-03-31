package ch.fhnw.labeling_tool.user;

import ch.fhnw.labeling_tool.jooq.enums.UserGroupRoleRole;
import ch.fhnw.labeling_tool.jooq.tables.daos.DialectDao;
import ch.fhnw.labeling_tool.jooq.tables.daos.UserDao;
import ch.fhnw.labeling_tool.jooq.tables.daos.UserGroupRoleDao;
import ch.fhnw.labeling_tool.jooq.tables.pojos.Dialect;
import ch.fhnw.labeling_tool.jooq.tables.pojos.User;
import ch.fhnw.labeling_tool.jooq.tables.pojos.UserGroupRole;
import com.google.common.io.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

import static ch.fhnw.labeling_tool.jooq.Tables.USER;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserDao userDao;
    private final UserGroupRoleDao userGroupRoleDao;
    private final PasswordEncoder passwordEncoder;
    private final Map<String, String> zipCodes;
    private final DialectDao dialectDao;

    @Autowired
    public CustomUserDetailsService(UserDao userDao, UserGroupRoleDao userGroupRoleDao, PasswordEncoder passwordEncoder, DialectDao dialectDao) throws IOException {
        this.userDao = userDao;
        this.userGroupRoleDao = userGroupRoleDao;
        this.passwordEncoder = passwordEncoder;
        this.dialectDao = dialectDao;
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        if (user.getDialectId() == null) {
            user.setDialectId(dialectDao.fetchByCountyId(zipCodes.get(user.getZipCode())).stream().map(Dialect::getId).findFirst().orElse(1L));
        }
        userDao.insert(user);
        Long id = userDao.fetchOneByUsername(user.getUsername()).getId();
        //add user to public group
        userGroupRoleDao.insert(new UserGroupRole(null, UserGroupRoleRole.USER, id, 1L));

    }
}
