package ch.fhnw.speech_collection_app.features.base.admin;

public class UserGroupRoleDto {
    public final Long id;
    public final String username, email;
    public final Boolean gamificationOn;

    public UserGroupRoleDto(Long id, String username, String email, Boolean gamificationOn) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.gamificationOn = gamificationOn;
    }
}
