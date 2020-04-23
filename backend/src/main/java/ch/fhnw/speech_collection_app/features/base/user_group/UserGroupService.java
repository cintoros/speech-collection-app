package ch.fhnw.speech_collection_app.features.base.user_group;

import ch.fhnw.speech_collection_app.config.SpeechCollectionAppConfig;
import ch.fhnw.speech_collection_app.features.base.user.CustomUserDetailsService;
import ch.fhnw.speech_collection_app.jooq.enums.CheckedDataElementType;
import ch.fhnw.speech_collection_app.jooq.enums.CheckedDataTupleType;
import ch.fhnw.speech_collection_app.jooq.enums.DataTupleType;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static ch.fhnw.speech_collection_app.jooq.Tables.*;

@Service
public class UserGroupService {
    private final CustomUserDetailsService customUserDetailsService;
    private final DSLContext dslContext;
    private final SpeechCollectionAppConfig speechCollectionAppConfig;

    @Autowired
    public UserGroupService(CustomUserDetailsService customUserDetailsService, DSLContext dslContext, SpeechCollectionAppConfig speechCollectionAppConfig) {
        this.customUserDetailsService = customUserDetailsService;
        this.dslContext = dslContext;
        this.speechCollectionAppConfig = speechCollectionAppConfig;
    }

    public void postRecording(long groupId, RecordingDto recording, MultipartFile file) throws IOException {
        isAllowed(groupId);
        var element = dslContext.newRecord(DATA_ELEMENT);
        element.setFinished(true);
        element.setUserGroupId(groupId);
        element.setUserId(customUserDetailsService.getLoggedInUserId());
        element.store();
        var audio = dslContext.newRecord(AUDIO);
        audio.setNoiseLevel(recording.getAudioNoiseLevel());
        audio.setQuality(recording.getAudioQuality());
        audio.setDialectId(customUserDetailsService.getLoggedInUserDialectId());
        audio.setPath("default");
        audio.setDataElementId(element.getId());
        audio.store();
        Path path = speechCollectionAppConfig.getBasePath().resolve("recording/" + audio.getId() + ".webm");
        Files.write(path, file.getBytes());
        audio.setPath(path.toString());
        audio.store();
        var tuple = dslContext.newRecord(DATA_TUPLE);
        tuple.setDataElementId_1(recording.getExcerptId());
        tuple.setDataElementId_2(element.getId());
        tuple.setType(DataTupleType.RECORDING);
        tuple.store();
    }

    //TODO change logic so it is also possible to return an image
    //TODO maybe over a feature flag and then a dropdown?
    // maybe an additional method?


    /**
     * return a text to be recorded based on:
     * 1. if the text is not easy to understand (no more than 3 skips)
     * 2. the text does not contain an error
     * 3. the text was not already recorded with the same dialect
     * 4. the text was not already skipped by the user.
     */
    public TextDto getExcerpt(Long groupId) {
        isAllowed(groupId);
        return dslContext.select(TEXT.DATA_ELEMENT_ID, DATA_ELEMENT.IS_PRIVATE, TEXT.TEXT_)
                .from(TEXT.innerJoin(DATA_ELEMENT).onKey())
                .where(DATA_ELEMENT.USER_GROUP_ID.eq(groupId)
                        //only show the ones that are good.
                        .and(DATA_ELEMENT.SKIPPED.lessOrEqual(3L))
                        .and(TEXT.IS_SENTENCE_ERROR.isFalse())
                        .and(DATA_ELEMENT.ID.notIn(dslContext.select(DATA_TUPLE.DATA_ELEMENT_ID_1)
                                .from(DATA_TUPLE.innerJoin(DATA_ELEMENT).onKey(DATA_TUPLE.DATA_ELEMENT_ID_2).innerJoin(AUDIO).onKey(AUDIO.DATA_ELEMENT_ID))
                                //only show the ones that need an additional dialect
                                .where(DATA_TUPLE.TYPE.eq(DataTupleType.RECORDING)
                                        .and(AUDIO.DIALECT_ID.eq(customUserDetailsService.getLoggedInUserDialectId()))))
                                //only show the ones that are not already skipped.
                                .and(DATA_ELEMENT.ID.notIn(dslContext.select(CHECKED_DATA_ELEMENT.DATA_ELEMENT_ID)
                                        .from(CHECKED_DATA_ELEMENT)
                                        .where(CHECKED_DATA_ELEMENT.TYPE.eq(CheckedDataElementType.SKIPPED)
                                                .and(CHECKED_DATA_ELEMENT.USER_ID.eq(customUserDetailsService.getLoggedInUserId())))))))
                .orderBy(DSL.rand())
                .limit(1).fetchOneInto(TextDto.class);
    }

    //TODO rename dto/method
    public void postCheckedOccurrence(long groupId, CheckedOccurrence checkedOccurrence) {
        isAllowed(groupId);
        //TODO check user_group tuple mapping
        var type = CheckedDataTupleType.valueOf(checkedOccurrence.label.toString());
        var checked = dslContext.newRecord(CHECKED_DATA_TUPLE);
        checked.setDataTupleId(checkedOccurrence.id);
        checked.setUserId(customUserDetailsService.getLoggedInUserId());
        checked.setType(type);
        checked.store();
    }

    /**
     * returns the next occurrences to check based on the groupId and available data.
     * occurrences are labeled until it is clear that they clearly wrong|correct
     */
    public List<Occurrence> getNextOccurrences(long groupId) {
        isAllowed(groupId);
        if (speechCollectionAppConfig.getPublicGroupId() == groupId) {
            var nextOccurrencesTextAudio = getNextOccurrencesTextAudio();
            if (!nextOccurrencesTextAudio.isEmpty()) {
                return nextOccurrencesTextAudio;
            }
        }
        return getNextOccurrencesRecoding(groupId);
    }

    //TODO refactor endpoints/logic so it can be expanded with images etc.
    private List<Occurrence> getNextOccurrencesTextAudio() {
//        return dslContext.select(TEXT_AUDIO.ID, TEXT_AUDIO.TEXT, DSL.inline(OccurrenceMode.TEXT_AUDIO.name()).as("mode"))
//                .from(TEXT_AUDIO)
//                .where(DSL.abs(TEXT_AUDIO.WRONG.minus(TEXT_AUDIO.CORRECT)).le(3L)
//                        .and(TEXT_AUDIO.ID.notIn(dslContext.select(CHECKED_TEXT_AUDIO.TEXT_AUDIO_ID).from(CHECKED_TEXT_AUDIO)
//                                .where(CHECKED_TEXT_AUDIO.USER_ID.eq(customUserDetailsService.getLoggedInUserId()))))
//                ).orderBy(DSL.rand()).limit(10).fetchInto(Occurrence.class);
        return List.of();
    }

    private List<Occurrence> getNextOccurrencesRecoding(long groupId) {
//        return dslContext.select(RECORDING.ID, EXCERPT.EXCERPT_, DSL.inline(OccurrenceMode.RECORDING.name()).as("mode"))
//                .from(RECORDING.join(EXCERPT).onKey().join(ORIGINAL_TEXT).onKey())
//                .where(ORIGINAL_TEXT.USER_GROUP_ID.eq(groupId)
//                        .and(DSL.abs(RECORDING.WRONG.minus(RECORDING.CORRECT)).le(3L))
//                        .and(RECORDING.LABEL.eq(RecordingLabel.RECORDED))
//                        .and(RECORDING.ID.notIn(dslContext.select(CHECKED_RECORDING.RECORDING_ID).from(CHECKED_RECORDING)
//                                .where(CHECKED_RECORDING.USER_ID.eq(customUserDetailsService.getLoggedInUserId()))))
//                ).orderBy(DSL.rand()).limit(10).fetchInto(Occurrence.class);
        return List.of();
    }

    public byte[] getAudio(long groupId, long audioId, OccurrenceMode mode) throws IOException {
        isAllowed(groupId);
        //TODO instead of the audioId use the elementId
        if (mode == OccurrenceMode.RECORDING) checkAudio(groupId, audioId);
        //TODO instead get the actual path from the database
        var s = (mode == OccurrenceMode.TEXT_AUDIO) ? "text_audio/" + audioId + ".flac" : "recording/" + audioId + ".webm";
        return Files.readAllBytes(speechCollectionAppConfig.getBasePath().resolve(s));

    }

    public void postCheckedDataElement(long groupId, long dataElementId, CheckedDataElementType type) {
        checkElement(groupId, dataElementId);
        var checked = dslContext.newRecord(CHECKED_DATA_ELEMENT);
        checked.setType(type);
        checked.setUserId(customUserDetailsService.getLoggedInUserId());
        checked.setDataElementId(dataElementId);
        checked.store();
    }


    private void checkElement(long groupId, long elementId) {
        isAllowed(groupId);
        boolean equals = dslContext.selectFrom(DATA_ELEMENT)
                .where(DATA_ELEMENT.ID.eq(elementId))
                .fetchOne(DATA_ELEMENT.USER_GROUP_ID).equals(groupId);
        if (!equals) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    private void checkAudio(long groupId, long audioId) {
//        boolean equals = dslContext.selectFrom(RECORDING.join(EXCERPT).onKey().join(ORIGINAL_TEXT).onKey())
//                .where(RECORDING.ID.eq(audioId))
//                .fetchOne(ORIGINAL_TEXT.USER_GROUP_ID).equals(groupId);
//        if (!equals) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    private void isAllowed(long userGroupId) {
        if (!customUserDetailsService.isAllowedOnProject(userGroupId, false))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
