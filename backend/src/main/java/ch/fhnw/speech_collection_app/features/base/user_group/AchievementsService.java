package ch.fhnw.speech_collection_app.features.base.user_group;

import ch.fhnw.speech_collection_app.config.SpeechCollectionAppConfig;
import ch.fhnw.speech_collection_app.features.base.user.CustomUserDetailsService;
import ch.fhnw.speech_collection_app.features.base.user_group.CantonClass.CantonEnum;
import ch.fhnw.speech_collection_app.jooq.enums.AchievementsDependsOn;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.Achievements;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.UserAchievements;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static ch.fhnw.speech_collection_app.jooq.Tables.*;

/**
 * service that creates/manages the achievements of the user,
 * daily achievements are not displayed, only monthly achievements are displayed.
 * the following achievements are created:
 * day: TOTAL_CHECKED,TOTAL_CREATED
 * month: TEXT_CREATED,AUDIO_CREATED,TOTAL_CHECKED
 */
@Service
//TODO maybe remove descriptions,title etc. from database?
//TODO not sure why there are so many achievements dependsOn/types -> maybe streamline as only TOTAL_CHECKED,TOTAL_CREATED are useful.
public class AchievementsService {
    private final CustomUserDetailsService customUserDetailsService;
    private final DSLContext dslContext;
    private final int[] pointPerLevel;
    private final String[] activeAchievements;

    @Autowired
    public AchievementsService(
            CustomUserDetailsService customUserDetailsService, DSLContext dslContext,
            SpeechCollectionAppConfig speechCollectionAppConfig) {
        this.customUserDetailsService = customUserDetailsService;
        this.dslContext = dslContext;
        this.pointPerLevel = speechCollectionAppConfig.getFeatures().getGamification().getPointPerLevel();
        this.activeAchievements = speechCollectionAppConfig.getFeatures().getGamification().getActiveAchievements();
    }

    private Long createAchievement(
            String name, String batch_name, Long domain_id, Timestamp start_time, Timestamp end_time,
            AchievementsDependsOn depends_on, Boolean isVisible) {

        var batch = dslContext.select().from(ACHIEVEMENTS)
                .where(ACHIEVEMENTS.NAME.eq(name).and(ACHIEVEMENTS.BATCH_NAME.eq(batch_name))
                        .and(ACHIEVEMENTS.START_TIME.eq(start_time)).and(ACHIEVEMENTS.END_TIME.eq(end_time))
                        .and(ACHIEVEMENTS.DEPENDS_ON.eq(depends_on)))
                .limit(1).fetchOneInto(Achievements.class);

        if (!(batch == null))
            return batch.getId();

        var achievement = dslContext.newRecord(ACHIEVEMENTS);
        achievement.setName(name);
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

    private Long createMonthAchievement(Timestamp time, String batch_name, AchievementsDependsOn depends_on) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(time);
        String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.GERMAN);

        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Timestamp start_time = new Timestamp(cal.getTimeInMillis());

        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.SECOND, -1);
        Timestamp end_time = new Timestamp(cal.getTimeInMillis());

        return createAchievement(month, batch_name, -1L, start_time, end_time, depends_on, true);
    }

    private Long createDayAchievement(Timestamp time, String batch_name, AchievementsDependsOn depends_on) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(time);
        String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.GERMAN);

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Timestamp start_time = new Timestamp(cal.getTimeInMillis());

        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.add(Calendar.SECOND, -1);
        Timestamp end_time = new Timestamp(cal.getTimeInMillis());

        return createAchievement(month, batch_name, -1L, start_time, end_time, depends_on, false);
    }

    Long getDayCheckAchievement(Timestamp time) {
        return createDayAchievement(time, "check-day", AchievementsDependsOn.TOTAL_CHECKED);
    }

    Long getDayCreateAchievement(Timestamp time) {
        return createDayAchievement(time, "create-day", AchievementsDependsOn.TOTAL_CREATED);
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
        var b = Arrays.stream(pointPerLevel).filter(i -> i > 0).anyMatch(aLong -> aLong == userPoints);
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

    public UserAchievements getUserAchievement(Long userId, Long achievementId) {
        return dslContext.select().from(USER_ACHIEVEMENTS)
                .where(USER_ACHIEVEMENTS.USER_ID.eq(userId).and(USER_ACHIEVEMENTS.ACHIEVEMENTS_ID.eq(achievementId)))
                .limit(1).fetchOneInto(UserAchievements.class);
    }

    public Achievements getAchievement(Long achievementId) {
        return dslContext.select().from(ACHIEVEMENTS).where(ACHIEVEMENTS.ID.eq(achievementId)).limit(1)
                .fetchOneInto(Achievements.class);
    }

    private List<AchievementWrapper> getActiveAchievements2() {
        Timestamp time = new Timestamp(new Date().getTime());
        return getUserAchievements(ACHIEVEMENTS.END_TIME.ge(time).and(ACHIEVEMENTS.START_TIME.le(time))
                .and(USER_ACHIEVEMENTS.POINTS.lessThan((long) pointPerLevel[4])));
    }

    private List<AchievementWrapper> getUserAchievements(Condition and) {
        createAutomaticAchievements();
        return dslContext.select(USER_ACHIEVEMENTS.asterisk()).from(USER_ACHIEVEMENTS.innerJoin(ACHIEVEMENTS).onKey())
                .where(USER_ACHIEVEMENTS.USER_ID.eq(customUserDetailsService.getLoggedInUserId())
                        .and(ACHIEVEMENTS.IS_VISIBLE.eq(true)).and(ACHIEVEMENTS.BATCH_NAME.in(this.activeAchievements))
                        .and(and))
                .fetch().map(record -> getAchievementWrapper(record.into(UserAchievements.class)));
    }

    public AchievementWrapper getActiveAchievement() {
        var res = getActiveAchievements2();
        return res.isEmpty() ? null : res.get(ThreadLocalRandom.current().nextInt(res.size()));
    }

    private int getLevel(UserAchievements userAchievement) {
        int[] pointPerLevel = this.pointPerLevel;
        for (int i = 4; i >= 0; i--) {
            if (pointPerLevel[i] <= userAchievement.getPoints()) {
                return i;
            }
        }
        return 0;
    }

    private long getAchievementPercent(long achievementId, int level) {
        float total = dslContext.fetchCount(USER);
        var lvl = Math.max(level, 1);
        float achieved = dslContext.selectCount()
                .from(USER_ACHIEVEMENTS.join(ACHIEVEMENTS).on(USER_ACHIEVEMENTS.ACHIEVEMENTS_ID.eq(ACHIEVEMENTS.ID)))
                .where(USER_ACHIEVEMENTS.ACHIEVEMENTS_ID.eq(achievementId)
                        .and(USER_ACHIEVEMENTS.POINTS.ge((long) pointPerLevel[lvl])))
                .fetchOneInto(Long.class);
        return (long) ((achieved / total) * 100f);

    }

    public List<AchievementWrapper> getActiveAchievements() {
        markUserAchievementsAsNotNew();
        return getActiveAchievements2();
    }

    public List<AchievementWrapper> getNonActiveAchievements() {
        markUserAchievementsAsNotNew();
        Timestamp time = new Timestamp(new Date().getTime());
        return getUserAchievements(ACHIEVEMENTS.END_TIME.le(time).or(USER_ACHIEVEMENTS.POINTS.greaterOrEqual((long) pointPerLevel[4])));
    }

    public void createAutomaticAchievements() {
        Long userId = customUserDetailsService.getLoggedInUserId();
        Timestamp timestamp = new Timestamp(new Date().getTime());

        updateUserAchievement(userId, createMonthAchievement(timestamp, "audio-month", AchievementsDependsOn.AUDIO_CREATED), 0L);
        updateUserAchievement(userId, createMonthAchievement(timestamp, "write-month", AchievementsDependsOn.TEXT_CREATED), 0L);
        updateUserAchievement(userId, createMonthAchievement(timestamp, "check-month", AchievementsDependsOn.TOTAL_CHECKED), 0L);
        updateUserAchievement(userId, getDayCheckAchievement(timestamp), 0L);
        updateUserAchievement(userId, getDayCreateAchievement(timestamp), 0L);
    }

    public Long numberOfNewAchievements() {
        Long userId = customUserDetailsService.getLoggedInUserId();
        return dslContext.selectCount().from(USER_ACHIEVEMENTS.innerJoin(ACHIEVEMENTS).onKey())
                .where(USER_ACHIEVEMENTS.USER_ID.eq(userId).and(USER_ACHIEVEMENTS.IS_NEW.eq(true))
                        .and(ACHIEVEMENTS.IS_VISIBLE.eq(true))
                        .and(USER_ACHIEVEMENTS.POINTS.greaterOrEqual((long) pointPerLevel[1])))
                .limit(1).fetchOneInto(Long.class);
    }

    private void markUserAchievementsAsNotNew() {
        dslContext.update(USER_ACHIEVEMENTS).set(USER_ACHIEVEMENTS.IS_NEW, false)
                .where(USER_ACHIEVEMENTS.USER_ID.eq(customUserDetailsService.getLoggedInUserId()))
                .execute();
    }

    public List<MapWrapper> getMapPercent() {
        List<MapWrapper> result = new ArrayList<>();
        for (CantonEnum canton : CantonEnum.values()) {
            String county_id = CantonClass.enumToCountyId(canton);
            var sum = dslContext.select(DSL.sum(USER_ACHIEVEMENTS.POINTS))
                    .from(USER_ACHIEVEMENTS.join(USER).on(USER_ACHIEVEMENTS.USER_ID.eq(USER.ID)).join(DIALECT)
                            .on(USER.DIALECT_ID.eq(DIALECT.ID)))
                    .where(DIALECT.COUNTY_ID.eq(county_id))
                    .fetchOptional()
                    //NOTE: empty cantons return null
                    .map(Record1::component1).filter(Objects::nonNull)
                    .map(BigDecimal::longValue).orElse(0L);
            result.add(new MapWrapper(canton, sum));
        }
        return result;
    }

    AchievementWrapper getAchievementWrapper(UserAchievements userAchievement) {
        int level = getLevel(userAchievement);
        return new AchievementWrapper(getAchievement(userAchievement.getAchievementsId()), userAchievement,
                getAchievementPercent(userAchievement.getAchievementsId(), level), level);
    }
}
