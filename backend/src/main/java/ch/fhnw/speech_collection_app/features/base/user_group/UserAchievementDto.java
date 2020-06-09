package ch.fhnw.speech_collection_app.features.base.user_group;

import java.util.Objects;

public class UserAchievementDto {
    private Long id;
    private Long user_id;
    private Long achievements_id;
    private Long points;

    public UserAchievementDto() {
    }

    public UserAchievementDto(Long id, Long user_id, Long achievements_id, Long points) {
        this.id = id;
        this.user_id = user_id;
        this.achievements_id = achievements_id;
        this.points = points;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return this.user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getAchievements_id() {
        return this.achievements_id;
    }

    public void setAchievements_id(Long achievements_id) {
        this.achievements_id = achievements_id;
    }

    public Long getPoints() {
        return this.points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public UserAchievementDto id(Long id) {
        this.id = id;
        return this;
    }

    public UserAchievementDto user_id(Long user_id) {
        this.user_id = user_id;
        return this;
    }

    public UserAchievementDto achievements_id(Long achievements_id) {
        this.achievements_id = achievements_id;
        return this;
    }

    public UserAchievementDto points(Long points) {
        this.points = points;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UserAchievementDto)) {
            return false;
        }
        UserAchievementDto userAchievementDto = (UserAchievementDto) o;
        return Objects.equals(id, userAchievementDto.id) && Objects.equals(user_id, userAchievementDto.user_id)
                && Objects.equals(achievements_id, userAchievementDto.achievements_id)
                && Objects.equals(points, userAchievementDto.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_id, achievements_id, points);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", user_id='" + getUser_id() + "'" + ", achievements_id='"
                + getAchievements_id() + "'" + ", points='" + getPoints() + "'" + "}";
    }

}
