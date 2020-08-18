package ch.fhnw.speech_collection_app.features.base.user_group;

import ch.fhnw.speech_collection_app.jooq.tables.pojos.DataTuple;

public class CheckWrapper {
    public final DataTuple dataTuple;
    public final AchievementWrapper achievementWrapper;

    public CheckWrapper(DataTuple dataTuple, AchievementWrapper achievementWrapper) {
        this.dataTuple = dataTuple;
        this.achievementWrapper = achievementWrapper;
    }
}
