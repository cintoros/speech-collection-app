package ch.fhnw.speech_collection_app.features.base.user_group;

import ch.fhnw.speech_collection_app.features.base.user.CustomUserDetailsService;
import ch.fhnw.speech_collection_app.jooq.enums.AchievementsDependsOn;
import net.sf.ehcache.search.aggregator.Count;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

import static ch.fhnw.speech_collection_app.jooq.Tables.ACHIEVEMENTS;
import static ch.fhnw.speech_collection_app.jooq.Tables.USER_ACHIEVEMENTS;

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
            AchievementsDependsOn depends_on, Boolean isVisible) {

        AchievementDto batch = dslContext.select().from(ACHIEVEMENTS)
                .where(ACHIEVEMENTS.NAME.eq(name).and(ACHIEVEMENTS.BATCH_NAME.eq(batch_name))
                        .and(ACHIEVEMENTS.TITLE.eq(title)).and(ACHIEVEMENTS.DESCRIPTION_LVL1.eq(description_lvl1))
                        .and(ACHIEVEMENTS.DESCRIPTION_LVL2.eq(description_lvl2))
                        .and(ACHIEVEMENTS.DESCRIPTION_LVL3.eq(description_lvl3))
                        .and(ACHIEVEMENTS.DESCRIPTION_LVL4.eq(description_lvl4))
                        .and(ACHIEVEMENTS.START_TIME.eq(start_time)).and(ACHIEVEMENTS.END_TIME.eq(end_time))
                        .and(ACHIEVEMENTS.DEPENDS_ON.eq(depends_on)))
                .limit(1).fetchOneInto(AchievementDto.class);

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
        achievement.setIsVisible(isVisible);
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
                LVL4, description_lvl1, description_lvl2, description_lvl3, description_lvl4, depends_on, true);
    }

    public Long createDayAchievement(Timestamp time, String batch_name, String title, String description_lvl1,
            String description_lvl2, String description_lvl3, String description_lvl4,
            AchievementsDependsOn depends_on) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(time);
        String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.GERMAN);
        String year = Integer.toString(cal.get(Calendar.YEAR));

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Timestamp start_time = new Timestamp(cal.getTimeInMillis());

        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.add(Calendar.SECOND, -1);
        Timestamp end_time = new Timestamp(cal.getTimeInMillis());

        return createAchievement(month, batch_name, title + " " + year, -1L, start_time, end_time, LVL1 / 2, LVL2 / 2,
                LVL3 / 2, LVL4 / 2, description_lvl1, description_lvl2, description_lvl3, description_lvl4, depends_on,
                false);
    }

    private Long getMonthTextAchievement(Timestamp time) {
        String des1 = "Du hast in diesem Monat " + LVL1 + " Übersetzungen geschrieben.";
        String des2 = "Du hast in diesem Monat " + LVL2 + " Übersetzungen geschrieben.";
        String des3 = "Du hast in diesem Monat " + LVL3 + " Übersetzungen geschrieben.";
        String des4 = "Du hast in diesem Monat " + LVL4 + " Übersetzungen geschrieben.";

        return createMonthAchievement(time, "edit", "Fleissiger Schreiber", des1, des2, des3, des4,
                AchievementsDependsOn.TEXT_CREATED);
    }

    private Long getMonthAudioAchievement(Timestamp time) {
        String des1 = "Du hast in diesem Monat " + LVL1 + " Übersetzungen aufgenommen.";
        String des2 = "Du hast in diesem Monat " + LVL2 + " Übersetzungen aufgenommen.";
        String des3 = "Du hast in diesem Monat " + LVL3 + " Übersetzungen aufgenommen.";
        String des4 = "Du hast in diesem Monat " + LVL4 + " Übersetzungen aufgenommen.";

        return createMonthAchievement(time, "microphone-alt", "Fleissiger Sprecher", des1, des2, des3, des4,
                AchievementsDependsOn.AUDIO_CREATED);
    }

    private Long getMonthCheckAchievement(Timestamp time) {
        String des1 = "Du hast in diesem Monat " + LVL1 + " Tupel geprüft.";
        String des2 = "Du hast in diesem Monat " + LVL2 + " Tupel geprüft.";
        String des3 = "Du hast in diesem Monat " + LVL3 + " Tupel geprüft.";
        String des4 = "Du hast in diesem Monat " + LVL4 + " Tupel geprüft.";

        return createMonthAchievement(time, "check-square", "Fleissiger Prüfer", des1, des2, des3, des4,
                AchievementsDependsOn.TOTAL_CHECKED);
    }

    Long getDayCheckAchievement(Timestamp time) {
        String des1 = "Du hast an diesem Tag " + LVL1 / 2 + " Tupel geprüft.";
        String des2 = "Du hast an diesem Tag " + LVL2 / 2 + " Tupel geprüft.";
        String des3 = "Du hast an diesem Tag " + LVL3 / 2 + " Tupel geprüft.";
        String des4 = "Du hast an diesem Tag " + LVL4 / 2 + " Tupel geprüft.";

        return createDayAchievement(time, "check-square", "Tagesziel prüfen", des1, des2, des3, des4,
                AchievementsDependsOn.TOTAL_CHECKED);
    }

    Long getDayCreateAchievement(Timestamp time) {
        String des1 = "Du hast an diesem Tag " + LVL1 / 2 + " Tupel erschaffen.";
        String des2 = "Du hast an diesem Tag " + LVL2 / 2 + " Tupel erschaffen.";
        String des3 = "Du hast an diesem Tag " + LVL3 / 2 + " Tupel erschaffen.";
        String des4 = "Du hast an diesem Tag " + LVL4 / 2 + " Tupel erschaffen.";

        return createDayAchievement(time, "equals", "Tagesziel erschaffen", des1, des2, des3, des4,
                AchievementsDependsOn.TOTAL_CREATED);
    }

    public void updateUserAchievement(Long userId, Long achievementId, Long amount) {
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

        userAchievement.setPoints(userAchievement.getPoints() + amount);
        userAchievement.store();

        AchievementDto achievementDto = getAchievement(achievementId);
        Long user_points = userAchievement.getPoints();
        Long lvl1 = achievementDto.getPoints_lvl1();
        Long lvl2 = achievementDto.getPoints_lvl1();
        Long lvl3 = achievementDto.getPoints_lvl1();
        Long lvl4 = achievementDto.getPoints_lvl1();

        if ((user_points == lvl1 || user_points == lvl2 || user_points == lvl3 || user_points == lvl4) && amount != 0) {
            userAchievement.setIsNew(true);
            userAchievement.store();
        }
    }

    public void updateAllUserAchievements(Long userId, AchievementsDependsOn achievementsDependsOn, Long domainId) {
        // find all achievements, which fulfill the depends on AND are currently active
        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());

        // we create a list with all the corresponding depends on
        List<AchievementsDependsOn> dependsOns = new ArrayList<AchievementsDependsOn>();
        dependsOns.add(achievementsDependsOn);

        // we have to add the ALL and Total created case to the Audio,Text and Image
        // Created case
        if (achievementsDependsOn == AchievementsDependsOn.AUDIO_CREATED
                || achievementsDependsOn == AchievementsDependsOn.TEXT_CREATED
                || achievementsDependsOn == AchievementsDependsOn.IMAGE_CREATED) {
            dependsOns.add(AchievementsDependsOn.ALL);
            dependsOns.add(AchievementsDependsOn.TOTAL_CREATED);
            // we have to add the ALL and TOTAL_CHECKED case to all the checked cases
        } else if (achievementsDependsOn == AchievementsDependsOn.AUDIO_AUDIO_CHECKED
                || achievementsDependsOn == AchievementsDependsOn.AUDIO_TEXT_CHECKED
                || achievementsDependsOn == AchievementsDependsOn.IMAGE_AUDIO_CHECKED
                || achievementsDependsOn == AchievementsDependsOn.IMAGE_TEXT_CHECKED
                || achievementsDependsOn == AchievementsDependsOn.TEXT_AUDIO_CHECKED
                || achievementsDependsOn == AchievementsDependsOn.TEXT_TEXT_CHECKED) {
            dependsOns.add(AchievementsDependsOn.ALL);
            dependsOns.add(AchievementsDependsOn.TOTAL_CHECKED);
        }

        List<Long> res = new ArrayList<Long>();
        if (!(domainId == -1)) {
            res = dslContext.select(ACHIEVEMENTS.ID).from(ACHIEVEMENTS)
                    .where(ACHIEVEMENTS.DEPENDS_ON.in(dependsOns).and(ACHIEVEMENTS.DOMAIN_ID.eq(domainId))
                            .and(ACHIEVEMENTS.END_TIME.ge(time)).and(ACHIEVEMENTS.START_TIME.le(time)))
                    .fetchInto(Long.class);
        }

        res.addAll(dslContext.select(ACHIEVEMENTS.ID).from(ACHIEVEMENTS)
                .where(ACHIEVEMENTS.DEPENDS_ON.in(dependsOns).and(ACHIEVEMENTS.DOMAIN_ID.isNull())
                        .and(ACHIEVEMENTS.END_TIME.ge(time)).and(ACHIEVEMENTS.START_TIME.le(time)))
                .fetchInto(Long.class));

        for (Long id : res) {
            updateUserAchievement(userId, id, 1L);
        }
    }

    public List<UserAchievementDto> getUserAchievements(Long userId) {
        return dslContext.select().from(USER_ACHIEVEMENTS).where(USER_ACHIEVEMENTS.USER_ID.eq(userId))
                .fetchInto(UserAchievementDto.class);
    }

    public UserAchievementDto getUserAchievement(Long userId, Long achievementId) {
        return dslContext.select().from(USER_ACHIEVEMENTS)
                .where(USER_ACHIEVEMENTS.USER_ID.eq(userId).and(USER_ACHIEVEMENTS.ACHIEVEMENTS_ID.eq(achievementId)))
                .limit(1).fetchOneInto(UserAchievementDto.class);
    }

    public AchievementDto getAchievement(Long achievementId) {
        return dslContext.select().from(ACHIEVEMENTS).where(ACHIEVEMENTS.ID.eq(achievementId)).limit(1)
                .fetchOneInto(AchievementDto.class);
    }

    public List<UserAchievementDto> getActiveUserAchievements(Long userId) {
        createAutomaticAchievements();
        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());

        return dslContext.select(USER_ACHIEVEMENTS.asterisk()).from(USER_ACHIEVEMENTS.innerJoin(ACHIEVEMENTS).onKey())
                .where(USER_ACHIEVEMENTS.USER_ID.eq(userId).and(ACHIEVEMENTS.END_TIME.ge(time))
                        .and(ACHIEVEMENTS.START_TIME.le(time))
                        .and(ACHIEVEMENTS.POINTS_LVL4.ge(USER_ACHIEVEMENTS.POINTS))
                        .and(ACHIEVEMENTS.IS_VISIBLE.eq(true)))
                .fetchInto(UserAchievementDto.class);
    }

    public List<UserAchievementDto> getNonActiveUserAchievements(Long userId) {
        createAutomaticAchievements();
        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());

        return dslContext.select(USER_ACHIEVEMENTS.asterisk()).from(USER_ACHIEVEMENTS.innerJoin(ACHIEVEMENTS).onKey())
                .where(USER_ACHIEVEMENTS.USER_ID.eq(userId)
                        .and((ACHIEVEMENTS.END_TIME.le(time)).or(ACHIEVEMENTS.POINTS_LVL4.le(USER_ACHIEVEMENTS.POINTS))
                                .and(ACHIEVEMENTS.IS_VISIBLE.eq(true))))
                .fetchInto(UserAchievementDto.class);
    }

    public AchievementWrapper getActiveAchievement() {
        createAutomaticAchievements();
        UserAchievementDto userAchievement = getActiveUserAchievement(customUserDetailsService.getLoggedInUserId());
        return new AchievementWrapper(getAchievement(userAchievement.getAchievements_id()), userAchievement,
                getAchievementPercent(userAchievement.getAchievements_id(), getLevel(userAchievement)));
    }

    public Long getLevel(UserAchievementDto userAchievement) {
        AchievementDto achiev = getAchievement(userAchievement.getAchievements_id());
        if (achiev.getPoints_lvl4() <= userAchievement.getPoints())
            return 4L;
        if (achiev.getPoints_lvl3() <= userAchievement.getPoints())
            return 3L;
        if (achiev.getPoints_lvl2() <= userAchievement.getPoints())
            return 2L;
        if (achiev.getPoints_lvl1() <= userAchievement.getPoints())
            return 1L;
        return 1L;
    }

    public long getAchievementPercent(Long achievementId, Long level) {
        float total = dslContext.selectCount().from(USER_ACHIEVEMENTS)
                .where(USER_ACHIEVEMENTS.ACHIEVEMENTS_ID.eq(achievementId)).limit(1).fetchOneInto(Long.class);
        float achieved = total;
        if (level == 1) {
            achieved = dslContext.selectCount()
                    .from(USER_ACHIEVEMENTS.join(ACHIEVEMENTS)
                            .on(USER_ACHIEVEMENTS.ACHIEVEMENTS_ID.eq(ACHIEVEMENTS.ID)))
                    .where(USER_ACHIEVEMENTS.ACHIEVEMENTS_ID.eq(achievementId)
                            .and(USER_ACHIEVEMENTS.POINTS.ge(ACHIEVEMENTS.POINTS_LVL1)))
                    .limit(1).fetchOneInto(Long.class);
        } else if (level == 2) {
            achieved = dslContext.selectCount()
                    .from(USER_ACHIEVEMENTS.join(ACHIEVEMENTS)
                            .on(USER_ACHIEVEMENTS.ACHIEVEMENTS_ID.eq(ACHIEVEMENTS.ID)))
                    .where(USER_ACHIEVEMENTS.ACHIEVEMENTS_ID.eq(achievementId)
                            .and(USER_ACHIEVEMENTS.POINTS.ge(ACHIEVEMENTS.POINTS_LVL2)))
                    .limit(1).fetchOneInto(Long.class);
        } else if (level == 3) {
            achieved = dslContext.selectCount()
                    .from(USER_ACHIEVEMENTS.join(ACHIEVEMENTS)
                            .on(USER_ACHIEVEMENTS.ACHIEVEMENTS_ID.eq(ACHIEVEMENTS.ID)))
                    .where(USER_ACHIEVEMENTS.ACHIEVEMENTS_ID.eq(achievementId)
                            .and(USER_ACHIEVEMENTS.POINTS.ge(ACHIEVEMENTS.POINTS_LVL3)))
                    .limit(1).fetchOneInto(Long.class);
        } else if (level == 4) {
            achieved = dslContext.selectCount()
                    .from(USER_ACHIEVEMENTS.join(ACHIEVEMENTS)
                            .on(USER_ACHIEVEMENTS.ACHIEVEMENTS_ID.eq(ACHIEVEMENTS.ID)))
                    .where(USER_ACHIEVEMENTS.ACHIEVEMENTS_ID.eq(achievementId)
                            .and(USER_ACHIEVEMENTS.POINTS.ge(ACHIEVEMENTS.POINTS_LVL4)))
                    .limit(1).fetchOneInto(Long.class);
        }
        long res = (long) ((achieved / total) * 100f);
        return res;

    }

    private UserAchievementDto getActiveUserAchievement(Long userId) {
        createAutomaticAchievements();
        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());

        return dslContext.select(USER_ACHIEVEMENTS.asterisk()).from(USER_ACHIEVEMENTS.innerJoin(ACHIEVEMENTS).onKey())
                .where(USER_ACHIEVEMENTS.USER_ID.eq(userId).and(ACHIEVEMENTS.END_TIME.ge(time))
                        .and(ACHIEVEMENTS.START_TIME.le(time))
                        .and(ACHIEVEMENTS.POINTS_LVL4.ge(USER_ACHIEVEMENTS.POINTS))
                        .and(ACHIEVEMENTS.IS_VISIBLE.eq(true)))
                .orderBy(DSL.rand()).limit(1).fetchOneInto(UserAchievementDto.class);
    }

    public List<AchievementWrapper> getActiveAchievements() {
        createAutomaticAchievements();
        List<UserAchievementDto> userAchievements = getActiveUserAchievements(
                customUserDetailsService.getLoggedInUserId());
        List<AchievementWrapper> res = new ArrayList<AchievementWrapper>();
        for (UserAchievementDto userAchievement : userAchievements) {
            res.add(new AchievementWrapper(getAchievement(userAchievement.getAchievements_id()), userAchievement,
                    getAchievementPercent(userAchievement.getAchievements_id(), getLevel(userAchievement))));
        }
        for (AchievementWrapper aw : res) {
            Long id = aw.getUserAchievementDto().getId();
            markUserAchievementAsNotNew(id);
        }
        return res;
    }

    public List<AchievementWrapper> getNonActiveAchievements() {
        createAutomaticAchievements();
        List<UserAchievementDto> userAchievements = getNonActiveUserAchievements(
                customUserDetailsService.getLoggedInUserId());
        List<AchievementWrapper> res = new ArrayList<AchievementWrapper>();
        for (UserAchievementDto userAchievement : userAchievements) {
            res.add(new AchievementWrapper(getAchievement(userAchievement.getAchievements_id()), userAchievement,
                    getAchievementPercent(userAchievement.getAchievements_id(), getLevel(userAchievement))));
        }
        for (AchievementWrapper aw : res) {
            Long id = aw.getUserAchievementDto().getId();
            markUserAchievementAsNotNew(id);
        }
        return res;
    }

    public void createAutomaticAchievements() {
        Date date = new Date();
        Long userId = customUserDetailsService.getLoggedInUserId();
        updateUserAchievement(userId, getMonthAudioAchievement(new Timestamp(date.getTime())), 0L);
        updateUserAchievement(userId, getMonthTextAchievement(new Timestamp(date.getTime())), 0L);
        updateUserAchievement(userId, getMonthCheckAchievement(new Timestamp(date.getTime())), 0L);
        updateUserAchievement(userId, getDayCheckAchievement(new Timestamp(date.getTime())), 0L);
        updateUserAchievement(userId, getDayCreateAchievement(new Timestamp(date.getTime())), 0L);
    }

    public Long numberOfNewAchievements() {
        Long userId = customUserDetailsService.getLoggedInUserId();
        return dslContext.selectCount().from(USER_ACHIEVEMENTS.innerJoin(ACHIEVEMENTS).onKey())
                .where(USER_ACHIEVEMENTS.USER_ID.eq(userId).and(USER_ACHIEVEMENTS.IS_NEW.eq(true))
                        .and(ACHIEVEMENTS.IS_VISIBLE.eq(true))
                        .and(USER_ACHIEVEMENTS.POINTS.ge(ACHIEVEMENTS.POINTS_LVL1)))
                .limit(1).fetchOneInto(Long.class);
    }

    private void markUserAchievementAsNotNew(Long id) {
        dslContext.update(USER_ACHIEVEMENTS).set(USER_ACHIEVEMENTS.IS_NEW, false).where(USER_ACHIEVEMENTS.ID.eq(id))
                .execute();
    }
}
