package ch.fhnw.speech_collection_app.features.base.user_group;

import ch.fhnw.speech_collection_app.jooq.tables.pojos.Achievements;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.UserAchievements;

public class AchievementWrapper {
    public final Achievements achievements;
    public final UserAchievements userAchievements;
    public final long percentOfUsers;
    public final long level;

    public AchievementWrapper(Achievements achievements, UserAchievements userAchievements, long percentOfUsers, long level) {
        this.achievements = achievements;
        this.userAchievements = userAchievements;
        this.percentOfUsers = percentOfUsers;
        this.level = level;
    }
}
