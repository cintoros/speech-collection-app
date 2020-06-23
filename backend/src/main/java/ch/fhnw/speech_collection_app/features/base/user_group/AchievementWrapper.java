package ch.fhnw.speech_collection_app.features.base.user_group;

import java.util.Objects;

public class AchievementWrapper {
    private AchievementDto achievementDto;
    private UserAchievementDto userAchievementDto;
    private Long percentOfUsers;

    public AchievementWrapper() {
    }

    public AchievementWrapper(AchievementDto achievementDto, UserAchievementDto userAchievementDto,
            Long percentOfUsers) {
        this.achievementDto = achievementDto;
        this.userAchievementDto = userAchievementDto;
        this.percentOfUsers = percentOfUsers;
    }

    public AchievementDto getAchievementDto() {
        return this.achievementDto;
    }

    public void setAchievementDto(AchievementDto achievementDto) {
        this.achievementDto = achievementDto;
    }

    public UserAchievementDto getUserAchievementDto() {
        return this.userAchievementDto;
    }

    public void setUserAchievementDto(UserAchievementDto userAchievementDto) {
        this.userAchievementDto = userAchievementDto;
    }

    public Long getPercentOfUsers() {
        return this.percentOfUsers;
    }

    public void setPercentOfUsers(Long percentOfUsers) {
        this.percentOfUsers = percentOfUsers;
    }

    public AchievementWrapper achievementDto(AchievementDto achievementDto) {
        this.achievementDto = achievementDto;
        return this;
    }

    public AchievementWrapper userAchievementDto(UserAchievementDto userAchievementDto) {
        this.userAchievementDto = userAchievementDto;
        return this;
    }

    public AchievementWrapper percentOfUsers(Long percentOfUsers) {
        this.percentOfUsers = percentOfUsers;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AchievementWrapper)) {
            return false;
        }
        AchievementWrapper achievementWrapper = (AchievementWrapper) o;
        return Objects.equals(achievementDto, achievementWrapper.achievementDto)
                && Objects.equals(userAchievementDto, achievementWrapper.userAchievementDto)
                && Objects.equals(percentOfUsers, achievementWrapper.percentOfUsers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(achievementDto, userAchievementDto, percentOfUsers);
    }

    @Override
    public String toString() {
        return "{" + " achievementDto='" + getAchievementDto() + "'" + ", userAchievementDto='"
                + getUserAchievementDto() + "'" + ", percentOfUsers='" + getPercentOfUsers() + "'" + "}";
    }

}
