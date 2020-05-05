package ch.fhnw.speech_collection_app.features.base.user;

import ch.fhnw.speech_collection_app.jooq.tables.pojos.UserGroupRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class CustomUserDetails extends User {
    public final List<UserGroupRole> userGroupRoles;
    private ch.fhnw.speech_collection_app.jooq.tables.pojos.User user;

    public CustomUserDetails(ch.fhnw.speech_collection_app.jooq.tables.pojos.User user, List<GrantedAuthority> authorities, List<UserGroupRole> userGroupRoles) {
        super(user.getUsername(), user.getPassword(), user.getEnabled(), true, true, true, authorities);
        this.user = user;
        user.setPassword(null);
        this.userGroupRoles = userGroupRoles;
    }

    public ch.fhnw.speech_collection_app.jooq.tables.pojos.User getUser() {
        return user;
    }

    public void setUser(ch.fhnw.speech_collection_app.jooq.tables.pojos.User user) {
        this.user = user;
    }
}
