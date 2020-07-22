package ch.fhnw.speech_collection_app.features.base.user_group;

import ch.fhnw.speech_collection_app.config.SpeechCollectionAppConfig;
import ch.fhnw.speech_collection_app.features.base.user.CustomUserDetailsService;
import ch.fhnw.speech_collection_app.jooq.enums.AchievementsDependsOn;
import ch.fhnw.speech_collection_app.jooq.enums.CheckedDataElementType;
import ch.fhnw.speech_collection_app.jooq.enums.CheckedDataTupleType;
import ch.fhnw.speech_collection_app.jooq.enums.DataTupleType;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.*;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import static ch.fhnw.speech_collection_app.jooq.Tables.*;

@Service
public class UserGroupService {
    private final CustomUserDetailsService customUserDetailsService;
    private final DSLContext dslContext;
    private final SpeechCollectionAppConfig speechCollectionAppConfig;
    private final AchievementsService achievementsService;

    @Autowired
    public UserGroupService(CustomUserDetailsService customUserDetailsService, DSLContext dslContext,
                            SpeechCollectionAppConfig speechCollectionAppConfig, AchievementsService achievementsService) {
        this.customUserDetailsService = customUserDetailsService;
        this.dslContext = dslContext;
        this.speechCollectionAppConfig = speechCollectionAppConfig;
        this.achievementsService = achievementsService;
    }

    public void postRecording(long groupId, RecordingDto recording, MultipartFile file, DataElement otherDataElement,
                              ElementType otherElementType) throws IOException {
        checkAllowed(groupId);

        // creation of the Recording component
        var element = dslContext.newRecord(DATA_ELEMENT);
        element.setFinished(true);
        element.setUserGroupId(groupId);
        element.setUserId(customUserDetailsService.getLoggedInUserId());
        element.setIsPrivate(false);
        element.setSourceId(otherDataElement.getSourceId());
        element.store();
        var audio = dslContext.newRecord(AUDIO);
        audio.setNoiseLevel(recording.audioNoiseLevel);
        audio.setBrowserVersion(recording.browserVersion);
        audio.setQuality(recording.audioQuality);
        audio.setDialectId(customUserDetailsService.getLoggedInUserDialectId());
        audio.setPath("default");
        audio.setDataElementId(element.getId());
        audio.store();
        // create the recording element to get an id
        var rawPath = Paths.get("recording", audio.getId() + ".webm");
        // use the given id to save the relative path to the database
        audio.setPath(rawPath.toString());
        // get the complete path to save the audio as webm to the drive
        rawPath = speechCollectionAppConfig.getBasePath().resolve(rawPath);
        Files.write(rawPath, file.getBytes());
        // store the change in the audio entry
        audio.store();

        // create the tuple Element
        var tuple = dslContext.newRecord(DATA_TUPLE);
        // the other element id (either Audio, Text or Image)
        tuple.setDataElementId_1(otherDataElement.getId());
        // the audio id
        tuple.setDataElementId_2(element.getId());
        tuple.setFinished(true);
        // set the tuple type
        switch (otherElementType) {
            case TEXT:
                tuple.setType(DataTupleType.TEXT_AUDIO);
                break;
            case AUDIO:
                tuple.setType(DataTupleType.AUDIO_AUDIO);
                break;
            case IMAGE:
                tuple.setType(DataTupleType.IMAGE_AUDIO);
                break;
            default:
                break;
        }
        tuple.store();

        achievementsService.createAutomaticAchievements();

        achievementsService.updateAllUserAchievements(customUserDetailsService.getLoggedInUserId(),
                AchievementsDependsOn.AUDIO_CREATED, getDomainIdFromSourceId(otherDataElement.getSourceId()));
    }

    private Long getDomainIdFromSourceId(Long sourceId) {
        return dslContext.select(SOURCE.DOMAIN_ID).from(SOURCE).where(SOURCE.ID.eq(sourceId)).limit(1)
                .fetchOneInto(Long.class);
    }

    public ReturnWrapper postExcerpt(long groupId, Text textDto, DataElement otherDataElement,
                                     ElementType otherElementType) {
        checkAllowed(groupId);

        // creation of the Text component
        var element = dslContext.newRecord(DATA_ELEMENT);
        element.setFinished(true);
        element.setUserGroupId(groupId);
        element.setUserId(customUserDetailsService.getLoggedInUserId());
        element.setIsPrivate(false);
        element.setSourceId(otherDataElement.getSourceId());
        element.store();
        var text = dslContext.newRecord(TEXT);
        text.setDialectId(customUserDetailsService.getLoggedInUserDialectId());
        text.setDataElementId(element.getId());
        text.setIsSentenceError(false);
        text.setText(textDto.getText());
        text.store();

        // create the tuple Element
        var tuple = dslContext.newRecord(DATA_TUPLE);

        // the other element id (either Audio, Text or Image)
        tuple.setDataElementId_1(otherDataElement.getId());
        // the audio id
        tuple.setDataElementId_2(element.getId());
        tuple.setFinished(true);
        // set the tuple type
        switch (otherElementType) {
            case TEXT:
                tuple.setType(DataTupleType.TEXT_TEXT);
                break;
            case AUDIO:
                tuple.setType(DataTupleType.AUDIO_TEXT);
                break;
            case IMAGE:
                tuple.setType(DataTupleType.IMAGE_TEXT);
                break;
            default:
                break;
        }
        tuple.store();

        achievementsService.createAutomaticAchievements();

        achievementsService.updateAllUserAchievements(customUserDetailsService.getLoggedInUserId(),
                AchievementsDependsOn.TEXT_CREATED, getDomainIdFromSourceId(otherDataElement.getSourceId()));

        Date date = new Date();
        Long userId = customUserDetailsService.getLoggedInUserId();
        Long achievementId = achievementsService.getDayCreateAchievement(new Timestamp(date.getTime()));
        var userAchievements = achievementsService.getUserAchievement(userId, achievementId);

        return new ReturnWrapper(getDataElement(element.getId()), getTextDto(element.getId()), null,
                null, ElementType.TEXT, achievementsService.getAchievementWrapper(userAchievements));
    }

    public ReturnWrapper getNext(long groupId, ElementType selectedElement) {
        checkAllowed(groupId);

        long dataElementID;
        ElementType eType;
        Image image = null;
        Audio recording = null;
        Text text = null;

        switch (selectedElement) {
            case TEXT:
                text = getExcerpt(groupId);
                dataElementID = text.getDataElementId();
                eType = ElementType.TEXT;
                break;
            case AUDIO:
                recording = getAudio(groupId);
                dataElementID = recording.getDataElementId();
                eType = ElementType.AUDIO;
                break;
            case IMAGE:
                image = getImage(groupId);
                dataElementID = image.getDataElementId();
                eType = ElementType.IMAGE;
                break;
            default:
                if (speechCollectionAppConfig.getFeatures().isImages() && ThreadLocalRandom.current().nextInt(10) <= 2) {
                    image = getImage(groupId);
                    dataElementID = image.getDataElementId();
                    eType = ElementType.IMAGE;
                } else {
                    text = getExcerpt(groupId);
                    dataElementID = text.getDataElementId();
                    eType = ElementType.TEXT;
                }
                break;
        }

        var data = getDataElement(dataElementID);

        Date date = new Date();
        Long userId = customUserDetailsService.getLoggedInUserId();
        Long achievementId = achievementsService.getDayCreateAchievement(new Timestamp(date.getTime()));
        var userAchievements = achievementsService.getUserAchievement(userId, achievementId);

        return new ReturnWrapper(data, text, recording, image, eType, achievementsService.getAchievementWrapper(userAchievements));
    }

    /**
     * returns the next occurrence to check based on the groupId and available data.<br>
     * occurrences are labeled until it is clear that they clearly wrong|correct.<br>
     */
    public CheckWrapper checkNext(long groupId) {
        checkAllowed(groupId);
        var loggedInUserId = customUserDetailsService.getLoggedInUserId();
        Long achievementId = achievementsService.getDayCheckAchievement(new Timestamp(new Date().getTime()));
        var userAchievements = achievementsService.getUserAchievement(loggedInUserId, achievementId);
        var res = dslContext.select(DATA_TUPLE.asterisk())
                .from(DATA_TUPLE.join(DATA_ELEMENT).onKey(DATA_TUPLE.DATA_ELEMENT_ID_1)
                        .join(TEXT).onKey(TEXT.DATA_ELEMENT_ID))
                .where(DSL.abs(DATA_TUPLE.WRONG.plus(DATA_TUPLE.CORRECT)).lessThan(speechCollectionAppConfig.getMinNumChecks())
                        .and(DATA_ELEMENT.USER_GROUP_ID.eq(groupId))
                        //.and(DATA_TUPLE.FINISHED.isFalse())
                        // user should only check once
                        .and(DATA_TUPLE.ID.notIn(dslContext.select(CHECKED_DATA_TUPLE.DATA_TUPLE_ID)
                                .from(CHECKED_DATA_TUPLE)
                                .where(CHECKED_DATA_TUPLE.USER_ID.eq(loggedInUserId)))))
                // user should not check self-generated data
                .except(dslContext.select(DATA_TUPLE.asterisk())
                        .from(DATA_TUPLE.join(DATA_ELEMENT).on(DATA_TUPLE.DATA_ELEMENT_ID_2.eq(DATA_ELEMENT.ID)))
                        .where(DATA_ELEMENT.USER_ID.eq(loggedInUserId)))
                .limit(speechCollectionAppConfig.getNumRandomSelect()).fetchInto(DataTuple.class);
        var dataTuple = res.isEmpty() ? null : res.get(ThreadLocalRandom.current().nextInt(res.size()));
        return new CheckWrapper(dataTuple, achievementsService.getAchievementWrapper(userAchievements));
    }

    public Audio getAudio(Long groupId) {
        checkAllowed(groupId);
        return dslContext.select(AUDIO.asterisk()).from(AUDIO.innerJoin(DATA_ELEMENT).onKey()).orderBy(DSL.rand())
                .limit(1).fetchOneInto(Audio.class);
    }

    /**
     * return a text to be recorded based on:<br>
     * 1. if the text is not easy to understand (no more than 3 skips)<br>
     * 2. the text does not contain an error<br>
     * 3. the text was not already recorded<br>
     * 4. the text was not already skipped by the user.<br>
     */
    public Text getExcerpt(Long groupId) {
        checkAllowed(groupId);
        var res = dslContext.select(TEXT.ID, TEXT.DIALECT_ID, TEXT.DATA_ELEMENT_ID, TEXT.IS_SENTENCE_ERROR, TEXT.TEXT_)
                .from(TEXT.innerJoin(DATA_ELEMENT).onKey())
                .where(DATA_ELEMENT.USER_GROUP_ID.eq(groupId)
                        //only show the ones that are good.
                        .and(DATA_ELEMENT.SKIPPED.lessOrEqual(3L))
                        .and(TEXT.IS_SENTENCE_ERROR.isFalse())
                        .and(DATA_ELEMENT.FINISHED.isFalse())
                        .and(DATA_ELEMENT.ID.notIn(dslContext.select(DATA_TUPLE.DATA_ELEMENT_ID_1)
                                .from(DATA_TUPLE.innerJoin(DATA_ELEMENT).onKey(DATA_TUPLE.DATA_ELEMENT_ID_2).innerJoin(AUDIO).onKey(AUDIO.DATA_ELEMENT_ID))
                                //only show the ones that need an additional dialect
                                .where(DATA_TUPLE.TYPE.eq(DataTupleType.RECORDING)))
                                //only show the ones that are not already skipped.
                                .and(DATA_ELEMENT.ID.notIn(dslContext.select(CHECKED_DATA_ELEMENT.DATA_ELEMENT_ID)
                                        .from(CHECKED_DATA_ELEMENT)
                                        .where(CHECKED_DATA_ELEMENT.TYPE.eq(CheckedDataElementType.SKIPPED)
                                                .and(CHECKED_DATA_ELEMENT.USER_ID.eq(customUserDetailsService.getLoggedInUserId())))))))
                .limit(speechCollectionAppConfig.getNumRandomSelect()).fetchInto(Text.class);
        return res.get(ThreadLocalRandom.current().nextInt(res.size()));
    }

    /**
     * return a image to be recorded based on:<br>
     * 1. if the image is not easy to understand (no more than 3 skips)<br>
     * 2. the image was not already recorded with the same dialect<br>
     * 3. the image was not already skipped by the user.<br>
     */
    public Image getImage(Long groupId) {
        checkAllowed(groupId);
        return dslContext
                .select(IMAGE.asterisk()).from(
                        IMAGE.innerJoin(DATA_ELEMENT).onKey())
                .where(DATA_ELEMENT.USER_GROUP_ID.eq(groupId)
                        // only show the ones that are good.
                        .and(DATA_ELEMENT.SKIPPED.lessOrEqual(3L)).and(
                                DATA_ELEMENT.FINISHED.isFalse())
                        .and(DATA_ELEMENT.ID
                                .notIn(dslContext.select(DATA_TUPLE.DATA_ELEMENT_ID_1)
                                        .from(DATA_TUPLE.innerJoin(DATA_ELEMENT).onKey(DATA_TUPLE.DATA_ELEMENT_ID_2)
                                                .innerJoin(AUDIO).onKey(AUDIO.DATA_ELEMENT_ID))
                                        // only show the ones that need an additional
                                        // dialect
                                        .where(DATA_TUPLE.TYPE.eq(DataTupleType.RECORDING)
                                                .and(AUDIO.DIALECT_ID
                                                        .eq(customUserDetailsService.getLoggedInUserDialectId()))))
                                // only show the ones that are not already skipped.
                                .and(DATA_ELEMENT.ID.notIn(dslContext.select(CHECKED_DATA_ELEMENT.DATA_ELEMENT_ID)
                                        .from(CHECKED_DATA_ELEMENT)
                                        .where(CHECKED_DATA_ELEMENT.TYPE.eq(CheckedDataElementType.SKIPPED)
                                                .and(CHECKED_DATA_ELEMENT.USER_ID
                                                        .eq(customUserDetailsService.getLoggedInUserId())))))))
                .orderBy(DSL.rand()).limit(1).fetchOneInto(Image.class);
    }

    public DataElement getDataElement(Long dataElementID) {
        return dslContext.select().from(DATA_ELEMENT).where(DATA_ELEMENT.ID.eq(dataElementID))
                .fetchOneInto(DataElement.class);
    }

    public Text getTextDto(Long dataElementID) {
        return dslContext.select().from(TEXT.innerJoin(DATA_ELEMENT).onKey()).where(DATA_ELEMENT.ID.eq(dataElementID))
                .fetchOneInto(Text.class);
    }

    public void postCheckedOccurrence(long groupId, CheckedOccurrence checkedOccurrence) {
        checkAllowed(groupId);
        var type = CheckedDataTupleType.valueOf(checkedOccurrence.label.toString());
        var checked = dslContext.newRecord(CHECKED_DATA_TUPLE);
        checked.setDataTupleId(checkedOccurrence.id);
        checked.setUserId(customUserDetailsService.getLoggedInUserId());
        checked.setType(type);
        checked.store();

        var tupleType = dslContext.selectFrom(DATA_TUPLE).where(DATA_TUPLE.ID.eq(checkedOccurrence.id)).fetchOne(DATA_TUPLE.TYPE);
        achievementsService.createAutomaticAchievements();
        AchievementsDependsOn achievementType;
        switch (tupleType) {
            case AUDIO_AUDIO:
                achievementType = AchievementsDependsOn.AUDIO_AUDIO_CHECKED;
                break;
            case AUDIO_TEXT:
                achievementType = AchievementsDependsOn.AUDIO_TEXT_CHECKED;
                break;
            case IMAGE_AUDIO:
                achievementType = AchievementsDependsOn.IMAGE_AUDIO_CHECKED;
                break;
            case IMAGE_TEXT:
                achievementType = AchievementsDependsOn.IMAGE_TEXT_CHECKED;
                break;
            case TEXT_AUDIO:
                achievementType = AchievementsDependsOn.TEXT_AUDIO_CHECKED;
                break;
            case TEXT_TEXT:
                achievementType = AchievementsDependsOn.TEXT_TEXT_CHECKED;
                break;
            default:
                achievementType = AchievementsDependsOn.TOTAL_CHECKED;
                break;
        }
        achievementsService.updateAllUserAchievements(customUserDetailsService.getLoggedInUserId(), achievementType, -1L);
    }

    public byte[] getAudio(long groupId, long dataElementId) throws IOException {
        checkDataElement(groupId, dataElementId);
        var path = dslContext.selectFrom(AUDIO).where(AUDIO.DATA_ELEMENT_ID.eq(dataElementId)).fetchOne(AUDIO.PATH);
        return Files.readAllBytes(speechCollectionAppConfig.getBasePath().resolve(path));
    }

    public byte[] getImage(long groupId, long dataElementId) throws IOException {
        checkDataElement(groupId, dataElementId);
        var path = dslContext.selectFrom(IMAGE).where(IMAGE.DATA_ELEMENT_ID.eq(dataElementId)).fetchOne(IMAGE.PATH);
        return Files.readAllBytes(speechCollectionAppConfig.getBasePath().resolve(path));
    }

    public void postCheckedDataElement(long groupId, long dataElementId, CheckedDataElementType type) {
        checkDataElement(groupId, dataElementId);
        var checked = dslContext.newRecord(CHECKED_DATA_ELEMENT);
        checked.setType(type);
        checked.setUserId(customUserDetailsService.getLoggedInUserId());
        checked.setDataElementId(dataElementId);
        checked.store();
    }

    private void checkDataElement(long groupId, long dataElementId) {
        checkAllowed(groupId);
        boolean equals = dslContext.selectFrom(DATA_ELEMENT).where(DATA_ELEMENT.ID.eq(dataElementId))
                .fetchOne(DATA_ELEMENT.USER_GROUP_ID).equals(groupId);
        if (!equals)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    private void checkAllowed(long userGroupId) {
        if (!customUserDetailsService.isAllowedOnProject(userGroupId, false))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
