package ch.fhnw.speech_collection_app.features.base.user_group;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.fhnw.speech_collection_app.config.SpeechCollectionAppConfig;
import ch.fhnw.speech_collection_app.jooq.enums.AchievementsDependsOn;
import static ch.fhnw.speech_collection_app.jooq.Tables.*;

@Service
public class AchievementsService {

    private final DSLContext dslContext;
    private final SpeechCollectionAppConfig speechCollectionAppConfig;

    private final Long LVL1 = 10L;
    private final Long LVL2 = 20L;
    private final Long LVL3 = 50L;
    private final Long LVL4 = 100L;

    @Autowired
    public AchievementsService(DSLContext dslContext, SpeechCollectionAppConfig speechCollectionAppConfig) {
        this.dslContext = dslContext;
        this.speechCollectionAppConfig = speechCollectionAppConfig;
    }

    public Long createAchievement(String name, String batch_name, String description, Long domain_id,
            Timestamp start_time, Timestamp end_time, Long points_lvl1, Long points_lvl2, Long points_lvl3,
            Long points_lvl4, AchievementsDependsOn depends_on) {

        AchievementDto batch = dslContext.select().from(ACHIEVEMENTS)
                .where(ACHIEVEMENTS.NAME.eq(name).and(ACHIEVEMENTS.BATCH_NAME.eq(batch_name))
                        .and(ACHIEVEMENTS.DESCRIPTION.eq(description)).and(ACHIEVEMENTS.START_TIME.eq(start_time))
                        .and(ACHIEVEMENTS.END_TIME.eq(end_time)).and(ACHIEVEMENTS.DEPENDS_ON.eq(depends_on)))
                .limit(1).fetchOneInto(AchievementDto.class);

        System.out.println(batch);
        if (!(batch == null))
            return batch.getId();

        var achievement = dslContext.newRecord(ACHIEVEMENTS);
        achievement.setName(name);
        achievement.setBatchName(batch_name);
        achievement.setDescription(description);
        if (!(domain_id == -1L))
            achievement.setDomainId(domain_id);
        achievement.setStartTime(start_time);
        achievement.setEndTime(end_time);
        achievement.setPointsLvl1(points_lvl1);
        achievement.setPointsLvl2(points_lvl2);
        achievement.setPointsLvl3(points_lvl3);
        achievement.setPointsLvl4(points_lvl4);
        achievement.setDependsOn(depends_on);
        achievement.store();

        return achievement.getId();
    }

    public Long createMonthAchievement(Timestamp time, String batch_name, String description,
            AchievementsDependsOn depends_on) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(time);
        String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.GERMAN);
        System.out.println(month);

        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Timestamp start_time = new Timestamp(cal.getTimeInMillis());

        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.SECOND, -1);
        Timestamp end_time = new Timestamp(cal.getTimeInMillis());

        return createAchievement(month, batch_name, description, -1L, start_time, end_time, LVL1, LVL2, LVL3, LVL4,
                depends_on);
    }

    public Long getMonthTextAchievement(Timestamp time) {
        return createMonthAchievement(time, "edit", "Fleissiger schreiber", AchievementsDependsOn.TEXT_CREATED);
    }

    public Long getMonthAudioAchievement(Timestamp time) {
        return createMonthAchievement(time, "microphone-alt", "Fleissiger sprecher",
                AchievementsDependsOn.AUDIO_CREATED);
    }

    public Long getMonthCheckAchievement(Timestamp time) {
        return createMonthAchievement(time, "check-square", "Fleissiger prüfer", AchievementsDependsOn.TOTAL_CHECKED);
    }

    public void updateUserAchievement(Long userId, Long achievementId) {
        var userAchievement = dslContext.select().from(USER_ACHIEVEMENTS)
                .where(USER_ACHIEVEMENTS.USER_ID.eq(userId).and(USER_ACHIEVEMENTS.ACHIEVEMENTS_ID.eq(achievementId)))
                .limit(1).fetchOneInto(USER_ACHIEVEMENTS);

        if (userAchievement == null) {
            userAchievement = dslContext.newRecord(USER_ACHIEVEMENTS);
            userAchievement.setUserId(userId);
            userAchievement.setAchievementsId(achievementId);
            userAchievement.setPoints(0L);
            userAchievement.store();
        }

        userAchievement.setPoints(userAchievement.getPoints() + 1L);
        userAchievement.store();

    }
}
