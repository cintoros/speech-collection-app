package ch.fhnw.speech_collection_app.user;

import ch.fhnw.speech_collection_app.jooq.tables.pojos.UserGroupRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class CustomUserDetails extends User {
    public final List<UserGroupRole> userGroupRoles;
    public final ch.fhnw.speech_collection_app.jooq.tables.pojos.User user;

    public CustomUserDetails(ch.fhnw.speech_collection_app.jooq.tables.pojos.User user, List<GrantedAuthority> authorities, List<UserGroupRole> userGroupRoles) {
        super(user.getUsername(), user.getPassword(), user.getEnabled(), true, true, true, authorities);
        this.user = user;
        user.setPassword(null);
        this.userGroupRoles = userGroupRoles;
    }
}
