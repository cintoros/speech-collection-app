package ch.fhnw.speech_collection_app.features.base.user_group;

import ch.fhnw.speech_collection_app.config.SpeechCollectionAppConfig;
import ch.fhnw.speech_collection_app.jooq.enums.CheckedRecordingLabel;
import ch.fhnw.speech_collection_app.jooq.enums.CheckedTextAudioLabel;
import ch.fhnw.speech_collection_app.jooq.enums.RecordingLabel;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.Excerpt;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.Recording;
import ch.fhnw.speech_collection_app.jooq.tables.records.CheckedRecordingRecord;
import ch.fhnw.speech_collection_app.jooq.tables.records.CheckedTextAudioRecord;
import ch.fhnw.speech_collection_app.jooq.tables.records.RecordingRecord;
import ch.fhnw.speech_collection_app.features.base.user.CustomUserDetailsService;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
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

    public void postRecording(long groupId, Recording recording, MultipartFile file) throws IOException {
        isAllowed(groupId);
        RecordingRecord recordingRecord = dslContext.newRecord(RECORDING, recording);
        recordingRecord.setUserId(customUserDetailsService.getLoggedInUserId());
        recordingRecord.setLabel(RecordingLabel.RECORDED);
        recordingRecord.setWrong(0L);
        recordingRecord.setCorrect(0L);
        recordingRecord.store();
        Files.write(speechCollectionAppConfig.getBasePath().resolve("recording/" + recordingRecord.getId() + ".webm"), file.getBytes());
    }

    public Excerpt getExcerpt(Long groupId) {
        isAllowed(groupId);
        return dslContext.select(EXCERPT.fields())
                .from(EXCERPT.join(ORIGINAL_TEXT).onKey())
                .where(ORIGINAL_TEXT.USER_GROUP_ID.eq(groupId)
                        .and(EXCERPT.IS_SKIPPED.lessOrEqual(3))
                        .and(EXCERPT.IS_SENTENCE_ERROR.isFalse())

                        .and(EXCERPT.ID.notIn(dslContext.select(RECORDING.EXCERPT_ID)
                                .from(RECORDING.join(USER).onKey())
                                //TODO not sure how we want to do the mapping in case of zip_codes?
                                .where(USER.DIALECT_ID.eq(customUserDetailsService.getLoggedInUserDialectId())))))
                .orderBy(DSL.rand())
                .limit(1).fetchOneInto(Excerpt.class);
    }

    public void postCheckedOccurrence(long groupId, CheckedOccurrence checkedOccurrence) {
        isAllowed(groupId);
        if (checkedOccurrence.mode == OccurrenceMode.TEXT_AUDIO)
            postCheckedOccurrenceTextAudio(checkedOccurrence);
        else postCheckedOccurrenceRecording(checkedOccurrence);
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

    private List<Occurrence> getNextOccurrencesTextAudio() {
        return dslContext.select(TEXT_AUDIO.ID, TEXT_AUDIO.TEXT, DSL.inline(OccurrenceMode.TEXT_AUDIO.name()).as("mode"))
                .from(TEXT_AUDIO)
                .where(DSL.abs(TEXT_AUDIO.WRONG.minus(TEXT_AUDIO.CORRECT)).le(3L)
                        .and(TEXT_AUDIO.ID.notIn(dslContext.select(CHECKED_TEXT_AUDIO.TEXT_AUDIO_ID).from(CHECKED_TEXT_AUDIO)
                                .where(CHECKED_TEXT_AUDIO.USER_ID.eq(customUserDetailsService.getLoggedInUserId()))))
                ).orderBy(DSL.rand()).limit(10).fetchInto(Occurrence.class);
    }

    private List<Occurrence> getNextOccurrencesRecoding(long groupId) {
        return dslContext.select(RECORDING.ID, EXCERPT.EXCERPT_, DSL.inline(OccurrenceMode.RECORDING.name()).as("mode"))
                .from(RECORDING.join(EXCERPT).onKey().join(ORIGINAL_TEXT).onKey())
                .where(ORIGINAL_TEXT.USER_GROUP_ID.eq(groupId)
                        .and(DSL.abs(RECORDING.WRONG.minus(RECORDING.CORRECT)).le(3L))
                        .and(RECORDING.LABEL.eq(RecordingLabel.RECORDED))
                        .and(RECORDING.ID.notIn(dslContext.select(CHECKED_RECORDING.RECORDING_ID).from(CHECKED_RECORDING)
                                .where(CHECKED_RECORDING.USER_ID.eq(customUserDetailsService.getLoggedInUserId()))))
                ).orderBy(DSL.rand()).limit(10).fetchInto(Occurrence.class);
    }

    public byte[] getAudio(long groupId, long audioId, OccurrenceMode mode) throws IOException {
        isAllowed(groupId);
        if (mode == OccurrenceMode.RECORDING) checkAudio(groupId, audioId);
        var s = (mode == OccurrenceMode.TEXT_AUDIO) ? "text_audio/" + audioId + ".flac" : "recording/" + audioId + ".webm";
        return Files.readAllBytes(speechCollectionAppConfig.getBasePath().resolve(s));

    }

    public void putExcerptSkipped(long groupId, long excerptId) {
        checkExcerpt(groupId, excerptId);
        dslContext.update(EXCERPT).set(EXCERPT.IS_SKIPPED, EXCERPT.IS_SKIPPED.plus(1)).where(EXCERPT.ID.eq(excerptId)).execute();
        storeRecord(RecordingLabel.SKIPPED, excerptId);
    }

    public void putExcerptPrivate(long groupId, long excerptId) {
        checkExcerpt(groupId, excerptId);
        dslContext.update(EXCERPT).set(EXCERPT.IS_PRIVATE, true).where(EXCERPT.ID.eq(excerptId)).execute();
        storeRecord(RecordingLabel.PRIVATE, excerptId);
    }

    public void putExcerptSentenceError(long groupId, long excerptId) {
        checkExcerpt(groupId, excerptId);
        dslContext.update(EXCERPT).set(EXCERPT.IS_SENTENCE_ERROR, true).where(EXCERPT.ID.eq(excerptId)).execute();
        storeRecord(RecordingLabel.SENTENCE_ERROR, excerptId);

    }

    private void postCheckedOccurrenceRecording(CheckedOccurrence checkedOccurrence) {
        var label = CheckedRecordingLabel.valueOf(checkedOccurrence.label.toString());
        var record = new CheckedRecordingRecord();
        record.setRecordingId(checkedOccurrence.id);
        record.setUserId(customUserDetailsService.getLoggedInUserId());
        record.setLabel(label);
        dslContext.executeInsert(record);
        if (checkedOccurrence.label == CheckedOccurrenceLabel.WRONG) {
            dslContext.update(RECORDING).set(RECORDING.WRONG, RECORDING.WRONG.plus(1)).where(RECORDING.ID.eq(checkedOccurrence.id)).execute();
        } else if (checkedOccurrence.label == CheckedOccurrenceLabel.CORRECT) {
            dslContext.update(RECORDING).set(RECORDING.CORRECT, RECORDING.CORRECT.plus(1)).where(RECORDING.ID.eq(checkedOccurrence.id)).execute();
        }
    }

    private void postCheckedOccurrenceTextAudio(CheckedOccurrence checkedOccurrence) {
        var label = CheckedTextAudioLabel.valueOf(checkedOccurrence.label.toString());
        var record = new CheckedTextAudioRecord();
        record.setTextAudioId(checkedOccurrence.id);
        record.setUserId(customUserDetailsService.getLoggedInUserId());
        record.setLabel(label);
        dslContext.executeInsert(record);
        if (checkedOccurrence.label == CheckedOccurrenceLabel.WRONG) {
            dslContext.update(TEXT_AUDIO).set(TEXT_AUDIO.WRONG, TEXT_AUDIO.WRONG.plus(1)).where(TEXT_AUDIO.ID.eq(checkedOccurrence.id)).execute();
        } else if (checkedOccurrence.label == CheckedOccurrenceLabel.CORRECT) {
            dslContext.update(TEXT_AUDIO).set(TEXT_AUDIO.CORRECT, TEXT_AUDIO.CORRECT.plus(1)).where(TEXT_AUDIO.ID.eq(checkedOccurrence.id)).execute();
        }
    }

    private RecordingRecord storeRecord(RecordingLabel recordingLabel, long excerptId) {
        RecordingRecord recordingRecord = dslContext.newRecord(RECORDING);
        recordingRecord.setUserId(customUserDetailsService.getLoggedInUserId());
        recordingRecord.setExcerptId(excerptId);
        recordingRecord.setLabel(recordingLabel);
        recordingRecord.store();
        return recordingRecord;
    }

    private void checkExcerpt(long groupId, long excerptId) {
        isAllowed(groupId);
        boolean equals = dslContext.selectFrom(EXCERPT.join(ORIGINAL_TEXT).onKey())
                .where(EXCERPT.ID.eq(excerptId))
                .fetchOne(ORIGINAL_TEXT.USER_GROUP_ID).equals(groupId);
        if (!equals) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    private void checkAudio(long groupId, long audioId) {
        boolean equals = dslContext.selectFrom(RECORDING.join(EXCERPT).onKey().join(ORIGINAL_TEXT).onKey())
                .where(RECORDING.ID.eq(audioId))
                .fetchOne(ORIGINAL_TEXT.USER_GROUP_ID).equals(groupId);
        if (!equals) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    private void isAllowed(long userGroupId) {
        if (!customUserDetailsService.isAllowedOnProject(userGroupId, false))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}