package ch.fhnw.speech_collection_app.features.base.admin;

public class UserGroupRoleDto {
    public final Long id;
    public final String username, email;

    public UserGroupRoleDto(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
