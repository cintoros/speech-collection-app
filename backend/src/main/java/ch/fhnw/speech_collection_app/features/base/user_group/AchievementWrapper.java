package ch.fhnw.speech_collection_app.features.base.user_group;

import java.util.Objects;

public class AchievementWrapper {
    private AchievementDto achievementDto;
    private UserAchievementDto userAchievementDto;

    public AchievementWrapper() {
    }

    public AchievementWrapper(AchievementDto achievementDto, UserAchievementDto userAchievementDto) {
        this.achievementDto = achievementDto;
        this.userAchievementDto = userAchievementDto;
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

    public AchievementWrapper achievementDto(AchievementDto achievementDto) {
        this.achievementDto = achievementDto;
        return this;
    }

    public AchievementWrapper userAchievementDto(UserAchievementDto userAchievementDto) {
        this.userAchievementDto = userAchievementDto;
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
                && Objects.equals(userAchievementDto, achievementWrapper.userAchievementDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(achievementDto, userAchievementDto);
    }

    @Override
    public String toString() {
        return "{" + " achievementDto='" + getAchievementDto() + "'" + ", userAchievementDto='"
                + getUserAchievementDto() + "'" + "}";
    }

}
