package ch.fhnw.speech_collection_app.user;

import ch.fhnw.speech_collection_app.jooq.tables.daos.DialectDao;
import ch.fhnw.speech_collection_app.jooq.tables.daos.UserGroupDao;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.Dialect;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.User;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.UserGroup;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.UserGroupRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class UserRestApiController {
    private final CustomUserDetailsService customUserDetailsService;
    private final DialectDao dialectDao;
    private final UserGroupDao userGroupDao;

    @Autowired
    public UserRestApiController(CustomUserDetailsService customUserDetailsService, DialectDao dialectDao, UserGroupDao userGroupDao) {
        this.customUserDetailsService = customUserDetailsService;
        this.dialectDao = dialectDao;
        this.userGroupDao = userGroupDao;
    }

    /**
     * endpoint used for user login validation
     */
    @GetMapping("user")
    public Principal getPrincipal(Principal user) {
        return user;
    }

    @GetMapping("user/{id}")
    public User getUser(@PathVariable long id) {
        return customUserDetailsService.getUser(id);
    }

    @PutMapping("user")
    public void putUser(@RequestBody User user) {
        customUserDetailsService.putUser(user);
    }

    @PutMapping("user/password")
    public void putPassword(@RequestBody ChangePassword changePassword) {
        customUserDetailsService.putPassword(changePassword);
    }


    @PostMapping("public/register")
    public void register(@RequestBody User user) {
        customUserDetailsService.register(user);
    }

    @PutMapping("public/user/email/confirm")
    public boolean confirmEmail(@RequestParam String token) {
        return customUserDetailsService.confirmEmail(token);
    }

    @GetMapping("public/user/email/resend")
    public void resendEmail(@RequestParam String email) {
        customUserDetailsService.resendEmail(email);
    }

    @PutMapping("public/user/password/reset")
    public boolean resetPassword(@RequestParam String token, @RequestBody String newPassword) {
        return customUserDetailsService.resetPassword(token, newPassword);
    }

    @GetMapping("public/user/password/resend")
    public void resendPassword(@RequestParam String email) {
        customUserDetailsService.resendPassword(email);
    }

    @GetMapping("public/dialect")
    public List<Dialect> getDialect() {
        return dialectDao.findAll();
    }

    @GetMapping("user_group")
    public List<UserGroup> getUserGroup() {
        if (customUserDetailsService.isAdmin()) {
            return userGroupDao.findAll();
        } else {
            var ids = customUserDetailsService.getLoggedInUser().userGroupRoles.stream().map(UserGroupRole::getUserGroupId).distinct();
            return userGroupDao.fetchById(ids.toArray(Long[]::new));
        }
    }
}
