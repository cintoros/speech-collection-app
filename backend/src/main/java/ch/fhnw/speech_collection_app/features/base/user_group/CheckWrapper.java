package ch.fhnw.speech_collection_app.features.base.user_group;

import java.util.Objects;

public class CheckWrapper {

    private TupleDto tupleDto;
    private AchievementWrapper achievementWrapper;

    public CheckWrapper() {
    }

    public CheckWrapper(TupleDto tupleDto, AchievementWrapper achievementWrapper) {
        this.tupleDto = tupleDto;
        this.achievementWrapper = achievementWrapper;
    }

    public TupleDto getTupleDto() {
        return this.tupleDto;
    }

    public void setTupleDto(TupleDto tupleDto) {
        this.tupleDto = tupleDto;
    }

    public AchievementWrapper getAchievementWrapper() {
        return this.achievementWrapper;
    }

    public void setAchievementWrapper(AchievementWrapper achievementWrapper) {
        this.achievementWrapper = achievementWrapper;
    }

    public CheckWrapper tupleDto(TupleDto tupleDto) {
        this.tupleDto = tupleDto;
        return this;
    }

    public CheckWrapper achievementWrapper(AchievementWrapper achievementWrapper) {
        this.achievementWrapper = achievementWrapper;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CheckWrapper)) {
            return false;
        }
        CheckWrapper checkWrapper = (CheckWrapper) o;
        return Objects.equals(tupleDto, checkWrapper.tupleDto)
                && Objects.equals(achievementWrapper, checkWrapper.achievementWrapper);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tupleDto, achievementWrapper);
    }

    @Override
    public String toString() {
        return "{" + " tupleDto='" + getTupleDto() + "'" + ", achievementWrapper='" + getAchievementWrapper() + "'"
                + "}";
    }

}
