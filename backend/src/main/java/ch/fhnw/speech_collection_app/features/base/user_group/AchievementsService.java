package ch.fhnw.speech_collection_app.features.base.user_group;

import ch.fhnw.speech_collection_app.config.SpeechCollectionAppConfig;
import ch.fhnw.speech_collection_app.features.base.user.CustomUserDetailsService;
import ch.fhnw.speech_collection_app.features.base.user_group.CantonClass.CantonEnum;
import ch.fhnw.speech_collection_app.jooq.enums.AchievementsDependsOn;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.Achievements;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.UserAchievements;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

import static ch.fhnw.speech_collection_app.jooq.Tables.*;

@Service
//TODO maybe remove descriptions etc. from database?
public class AchievementsService {
    private final CustomUserDetailsService customUserDetailsService;
    private final DSLContext dslContext;
    private final SpeechCollectionAppConfig.Features.Gamification gamification;

    @Autowired
    public AchievementsService(
            CustomUserDetailsService customUserDetailsService, DSLContext dslContext,
            SpeechCollectionAppConfig speechCollectionAppConfig) {
        this.customUserDetailsService = customUserDetailsService;
        this.dslContext = dslContext;
        this.gamification = speechCollectionAppConfig.getFeatures().getGamification();
    }

    //TODO ensure that the achievment names are unique ;)
    public Long createAchievement(
            String name, String batch_name, String title, Long domain_id, Timestamp start_time, Timestamp end_time,
            AchievementsDependsOn depends_on, Boolean isVisible) {

        var batch = dslContext.select().from(ACHIEVEMENTS)
                .where(ACHIEVEMENTS.NAME.eq(name).and(ACHIEVEMENTS.BATCH_NAME.eq(batch_name))
                        .and(ACHIEVEMENTS.TITLE.eq(title))
                        .and(ACHIEVEMENTS.START_TIME.eq(start_time)).and(ACHIEVEMENTS.END_TIME.eq(end_time))
                        .and(ACHIEVEMENTS.DEPENDS_ON.eq(depends_on)))
                .limit(1).fetchOneInto(Achievements.class);

        if (!(batch == null))
            return batch.getId();

        var achievement = dslContext.newRecord(ACHIEVEMENTS);
        achievement.setName(name);
        achievement.setTitle(title);
        achievement.setBatchName(batch_name);
        if (!(domain_id == -1L))
            achievement.setDomainId(domain_id);
        achievement.setStartTime(start_time);
        achievement.setEndTime(end_time);
        achievement.setDependsOn(depends_on);
        achievement.setIsVisible(isVisible);
        achievement.store();

        return achievement.getId();
    }

    public Long createMonthAchievement(Timestamp time, String batch_name, String title, AchievementsDependsOn depends_on) {
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

        return createAchievement(month, batch_name, title + " " + year, -1L, start_time, end_time, depends_on, true);
    }

    //TODO why are daily achievements not visible :)
    public Long createDayAchievement(Timestamp time, String batch_name, String title, AchievementsDependsOn depends_on) {
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

        return createAchievement(month, batch_name, title + " " + year, -1L, start_time, end_time, depends_on, false);
    }

    //TODO it would be nice to do this in the frontend. so it can be translated using 18n etc.
    private Long getMonthTextAchievement(Timestamp time) {
        return createMonthAchievement(time, "edit", "Fleissiger Schreiber", AchievementsDependsOn.TEXT_CREATED);
    }

    private Long getMonthAudioAchievement(Timestamp time) {
        return createMonthAchievement(time, "microphone-alt", "Fleissiger Sprecher", AchievementsDependsOn.AUDIO_CREATED);
    }

    private Long getMonthCheckAchievement(Timestamp time) {
        return createMonthAchievement(time, "check-square", "Fleissiger Prüfer", AchievementsDependsOn.TOTAL_CHECKED);
    }

    Long getDayCheckAchievement(Timestamp time) {
        return createDayAchievement(time, "check-square", "Tagesziel prüfen", AchievementsDependsOn.TOTAL_CHECKED);
    }

    Long getDayCreateAchievement(Timestamp time) {
        return createDayAchievement(time, "equals", "Tagesziel erschaffen", AchievementsDependsOn.TOTAL_CREATED);
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

        var userPoints = userAchievement.getPoints();
        var b = Arrays.stream(gamification.getPointPerLevel()).filter(i -> i > 0).anyMatch(aLong -> aLong == userPoints);
        if (b && amount != 0) {
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

    //FIXME nullpointer in case the user has not visited the home/achievemnts page before record component and/or time based?
    public UserAchievements getUserAchievement(Long userId, Long achievementId) {
        return dslContext.select().from(USER_ACHIEVEMENTS)
                .where(USER_ACHIEVEMENTS.USER_ID.eq(userId).and(USER_ACHIEVEMENTS.ACHIEVEMENTS_ID.eq(achievementId)))
                .limit(1).fetchOneInto(UserAchievements.class);
    }

    public Achievements getAchievement(Long achievementId) {
        return dslContext.select().from(ACHIEVEMENTS).where(ACHIEVEMENTS.ID.eq(achievementId)).limit(1)
                .fetchOneInto(Achievements.class);
    }

    public List<UserAchievements> getActiveUserAchievements(Long userId) {
        createAutomaticAchievements();
        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());

        return dslContext.select(USER_ACHIEVEMENTS.asterisk()).from(USER_ACHIEVEMENTS.innerJoin(ACHIEVEMENTS).onKey())
                .where(USER_ACHIEVEMENTS.USER_ID.eq(userId).and(ACHIEVEMENTS.END_TIME.ge(time))
                        .and(ACHIEVEMENTS.START_TIME.le(time))
                        .and(ACHIEVEMENTS.POINTS_LVL4.ge(USER_ACHIEVEMENTS.POINTS))
                        .and(ACHIEVEMENTS.IS_VISIBLE.eq(true)))
                .fetchInto(UserAchievements.class);
    }

    public List<UserAchievements> getNonActiveUserAchievements(Long userId) {
        createAutomaticAchievements();
        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());

        return dslContext.select(USER_ACHIEVEMENTS.asterisk()).from(USER_ACHIEVEMENTS.innerJoin(ACHIEVEMENTS).onKey())
                .where(USER_ACHIEVEMENTS.USER_ID.eq(userId)
                        .and((ACHIEVEMENTS.END_TIME.le(time)).or(ACHIEVEMENTS.POINTS_LVL4.le(USER_ACHIEVEMENTS.POINTS))
                                .and(ACHIEVEMENTS.IS_VISIBLE.eq(true))))
                .fetchInto(UserAchievements.class);
    }

    public AchievementWrapper getActiveAchievement() {
        createAutomaticAchievements();
        var userAchievement = getActiveUserAchievement(customUserDetailsService.getLoggedInUserId());
        return getAchievementWrapper(userAchievement);
    }

    private int getLevel(UserAchievements userAchievement) {
        int[] pointPerLevel = gamification.getPointPerLevel();
        for (int i = 4; i >= 0; i--) {
            if (pointPerLevel[i] <= userAchievement.getPoints()) {
                return i;
            }
        }
        return 0;
    }

    public long getAchievementPercent(long achievementId, long level) {
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
        return (long) ((achieved / total) * 100f);

    }

    private UserAchievements getActiveUserAchievement(Long userId) {
        createAutomaticAchievements();
        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());

        return dslContext.select(USER_ACHIEVEMENTS.asterisk()).from(USER_ACHIEVEMENTS.innerJoin(ACHIEVEMENTS).onKey())
                .where(USER_ACHIEVEMENTS.USER_ID.eq(userId).and(ACHIEVEMENTS.END_TIME.ge(time))
                        .and(ACHIEVEMENTS.START_TIME.le(time))
                        .and(ACHIEVEMENTS.POINTS_LVL4.ge(USER_ACHIEVEMENTS.POINTS))
                        .and(ACHIEVEMENTS.IS_VISIBLE.eq(true)))
                .orderBy(DSL.rand()).limit(1).fetchOneInto(UserAchievements.class);
    }

    public List<AchievementWrapper> getActiveAchievements() {
        createAutomaticAchievements();
        var userAchievements = getActiveUserAchievements(customUserDetailsService.getLoggedInUserId());
        var res = new ArrayList<AchievementWrapper>();
        for (var userAchievement : userAchievements) {
            res.add(getAchievementWrapper(userAchievement));
        }
        for (AchievementWrapper aw : res) {
            Long id = aw.userAchievements.getId();
            markUserAchievementAsNotNew(id);
        }
        return res;
    }

    //TODO this does not return anything?
    public List<AchievementWrapper> getNonActiveAchievements() {
        createAutomaticAchievements();
        var userAchievements = getNonActiveUserAchievements(customUserDetailsService.getLoggedInUserId());
        List<AchievementWrapper> res = new ArrayList<AchievementWrapper>();
        for (var userAchievement : userAchievements) {
            res.add(getAchievementWrapper(userAchievement));
        }
        for (AchievementWrapper aw : res) {
            Long id = aw.userAchievements.getId();
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

    public List<MapWrapper> getMapPercent() {
        List<MapWrapper> result = new ArrayList<>();
        for (CantonEnum canton : CantonEnum.values()) {
            String county_id = CantonClass.enumToCountyId(canton);
            List<Long> res = dslContext.select(USER_ACHIEVEMENTS.POINTS)
                    .from(USER_ACHIEVEMENTS.join(USER).on(USER_ACHIEVEMENTS.USER_ID.eq(USER.ID)).join(DIALECT)
                            .on(USER.DIALECT_ID.eq(DIALECT.ID)))
                    .where(DIALECT.COUNTY_ID.eq(county_id)).fetchInto(Long.class);
            Long sum = 0L;
            for (Long value : res)
                sum += value;
            result.add(new MapWrapper(canton, sum));
        }
        return result;
    }

    AchievementWrapper getAchievementWrapper(UserAchievements userAchievement) {
        int level = getLevel(userAchievement);
        return new AchievementWrapper(getAchievement(userAchievement.getAchievementsId()), userAchievement,
                getAchievementPercent(userAchievement.getAchievementsId(), Math.min(level, 1)), level);
    }
}
