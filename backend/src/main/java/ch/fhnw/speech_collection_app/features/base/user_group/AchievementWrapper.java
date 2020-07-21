package ch.fhnw.speech_collection_app.features.base.user_group;

import ch.fhnw.speech_collection_app.jooq.tables.pojos.Achievements;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.UserAchievements;

public class AchievementWrapper {
    public final Achievements achievements;
    public final UserAchievements userAchievements;
    public final Long percentOfUsers;

    public AchievementWrapper(Achievements achievements, UserAchievements userAchievements, Long percentOfUsers) {
        this.achievements = achievements;
        this.userAchievements = userAchievements;
        this.percentOfUsers = percentOfUsers;
    }
}
