package ch.fhnw.speech_collection_app.features.base.user_group;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.fhnw.speech_collection_app.features.base.user.CustomUserDetailsService;
import ch.fhnw.speech_collection_app.jooq.enums.AchievementsDependsOn;
import static ch.fhnw.speech_collection_app.jooq.Tables.*;

@Service
public class AchievementsService {

    private final CustomUserDetailsService customUserDetailsService;
    private final DSLContext dslContext;

    private final Long LVL1 = 10L;
    private final Long LVL2 = 20L;
    private final Long LVL3 = 50L;
    private final Long LVL4 = 100L;

    @Autowired
    public AchievementsService(CustomUserDetailsService customUserDetailsService, DSLContext dslContext) {
        this.customUserDetailsService = customUserDetailsService;
        this.dslContext = dslContext;
    }

    public Long createAchievement(String name, String batch_name, String title, Long domain_id, Timestamp start_time,
            Timestamp end_time, Long points_lvl1, Long points_lvl2, Long points_lvl3, Long points_lvl4,
            String description_lvl1, String description_lvl2, String description_lvl3, String description_lvl4,
            AchievementsDependsOn depends_on) {

        AchievementDto batch = dslContext.select().from(ACHIEVEMENTS)
                .where(ACHIEVEMENTS.NAME.eq(name).and(ACHIEVEMENTS.BATCH_NAME.eq(batch_name))
                        .and(ACHIEVEMENTS.TITLE.eq(title)).and(ACHIEVEMENTS.DESCRIPTION_LVL1.eq(description_lvl1))
                        .and(ACHIEVEMENTS.DESCRIPTION_LVL2.eq(description_lvl2))
                        .and(ACHIEVEMENTS.DESCRIPTION_LVL3.eq(description_lvl3))
                        .and(ACHIEVEMENTS.DESCRIPTION_LVL4.eq(description_lvl4))
                        .and(ACHIEVEMENTS.START_TIME.eq(start_time)).and(ACHIEVEMENTS.END_TIME.eq(end_time))
                        .and(ACHIEVEMENTS.DEPENDS_ON.eq(depends_on)))
                .limit(1).fetchOneInto(AchievementDto.class);

        System.out.println(batch);
        if (!(batch == null))
            return batch.getId();

        var achievement = dslContext.newRecord(ACHIEVEMENTS);
        achievement.setName(name);
        achievement.setTitle(title);
        achievement.setBatchName(batch_name);
        achievement.setDescriptionLvl1(description_lvl1);
        achievement.setDescriptionLvl2(description_lvl2);
        achievement.setDescriptionLvl3(description_lvl3);
        achievement.setDescriptionLvl4(description_lvl4);
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

    public Long createMonthAchievement(Timestamp time, String batch_name, String title, String description_lvl1,
            String description_lvl2, String description_lvl3, String description_lvl4,
            AchievementsDependsOn depends_on) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(time);
        String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.GERMAN);
        String year = Integer.toString(cal.get(Calendar.YEAR));
        System.out.println(month);

        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Timestamp start_time = new Timestamp(cal.getTimeInMillis());

        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.SECOND, -1);
        Timestamp end_time = new Timestamp(cal.getTimeInMillis());

        return createAchievement(month, batch_name, title + " " + year, -1L, start_time, end_time, LVL1, LVL2, LVL3,
                LVL4, description_lvl1, description_lvl2, description_lvl3, description_lvl4, depends_on);
    }

    public Long getMonthTextAchievement(Timestamp time) {
        String des1 = "Du hast in diesem Monat " + LVL1 + " Übersetzungen geschrieben.";
        String des2 = "Du hast in diesem Monat " + LVL2 + " Übersetzungen geschrieben.";
        String des3 = "Du hast in diesem Monat " + LVL3 + " Übersetzungen geschrieben.";
        String des4 = "Du hast in diesem Monat " + LVL4 + " Übersetzungen geschrieben.";

        return createMonthAchievement(time, "edit", "Fleissiger schreiber", des1, des2, des3, des4,
                AchievementsDependsOn.TEXT_CREATED);
    }

    public Long getMonthAudioAchievement(Timestamp time) {
        String des1 = "Du hast in diesem Monat " + LVL1 + " Übersetzungen aufgenommen.";
        String des2 = "Du hast in diesem Monat " + LVL2 + " Übersetzungen aufgenommen.";
        String des3 = "Du hast in diesem Monat " + LVL3 + " Übersetzungen aufgenommen.";
        String des4 = "Du hast in diesem Monat " + LVL4 + " Übersetzungen aufgenommen.";

        return createMonthAchievement(time, "microphone-alt", "Fleissiger sprecher", des1, des2, des3, des4,
                AchievementsDependsOn.AUDIO_CREATED);
    }

    public Long getMonthCheckAchievement(Timestamp time) {
        String des1 = "Du hast in diesem Monat " + LVL1 + " Tupel geprüft.";
        String des2 = "Du hast in diesem Monat " + LVL2 + " Tupel geprüft.";
        String des3 = "Du hast in diesem Monat " + LVL3 + " Tupel geprüft.";
        String des4 = "Du hast in diesem Monat " + LVL4 + " Tupel geprüft.";

        return createMonthAchievement(time, "check-square", "Fleissiger prüfer", des1, des2, des3, des4,
                AchievementsDependsOn.TOTAL_CHECKED);
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

    public List<UserAchievementDto> getUserAchievements(Long userId) {
        return dslContext.select().from(USER_ACHIEVEMENTS).where(USER_ACHIEVEMENTS.USER_ID.eq(userId))
                .fetchInto(UserAchievementDto.class);
    }

    public AchievementDto getAchievement(Long achievementId) {
        return dslContext.select().from(ACHIEVEMENTS).where(ACHIEVEMENTS.ID.eq(achievementId)).limit(1)
                .fetchOneInto(AchievementDto.class);
    }

    public List<AchievementWrapper> getAchievements() {
        List<UserAchievementDto> userAchievements = getUserAchievements(customUserDetailsService.getLoggedInUserId());
        List<AchievementWrapper> res = new ArrayList<AchievementWrapper>();
        for (UserAchievementDto userAchievement : userAchievements) {
            res.add(new AchievementWrapper(getAchievement(userAchievement.getAchievements_id()), userAchievement));
        }
        return res;

    }
}
