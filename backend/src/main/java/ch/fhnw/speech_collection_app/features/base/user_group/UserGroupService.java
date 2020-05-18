package ch.fhnw.speech_collection_app.features.base.user_group;

import ch.fhnw.speech_collection_app.config.SpeechCollectionAppConfig;
import ch.fhnw.speech_collection_app.features.base.user.CustomUserDetailsService;
import ch.fhnw.speech_collection_app.features.base.user_group.ReturnWrapper.ElementType;
import ch.fhnw.speech_collection_app.jooq.enums.CheckedDataElementType;
import ch.fhnw.speech_collection_app.jooq.enums.CheckedDataTupleType;
import ch.fhnw.speech_collection_app.jooq.enums.DataTupleType;
import ch.fhnw.speech_collection_app.jooq.tables.DataElement;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectLimitPercentStep;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static ch.fhnw.speech_collection_app.jooq.Tables.*;

@Service
public class UserGroupService {
    private final CustomUserDetailsService customUserDetailsService;
    private final DSLContext dslContext;
    private final SpeechCollectionAppConfig speechCollectionAppConfig;

    @Autowired
    public UserGroupService(CustomUserDetailsService customUserDetailsService, DSLContext dslContext,
            SpeechCollectionAppConfig speechCollectionAppConfig) {
        this.customUserDetailsService = customUserDetailsService;
        this.dslContext = dslContext;
        this.speechCollectionAppConfig = speechCollectionAppConfig;
    }

    public void postRecording(long groupId, RecordingDto recording, MultipartFile file, DataElementDto otherDataElement,
            ElementType otherElementType) throws IOException {
        checkAllowed(groupId);

        // creation of the Recording component
        var element = dslContext.newRecord(DATA_ELEMENT);
        element.setFinished(true);
        element.setUserGroupId(groupId);
        element.setUserId(customUserDetailsService.getLoggedInUserId());
        element.setIsPrivate(false);
        element.store();
        var audio = dslContext.newRecord(AUDIO);
        audio.setNoiseLevel(recording.getAudioNoiseLevel());
        audio.setQuality(recording.getAudioQuality());
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
        }
        tuple.store();
    }

    public ReturnWrapper postExcerpt(long groupId, TextDto textDto, DataElementDto otherDataElement,
            ElementType otherElementType) {
        checkAllowed(groupId);

        // creation of the Text component
        var element = dslContext.newRecord(DATA_ELEMENT);
        element.setFinished(true);
        element.setUserGroupId(groupId);
        element.setUserId(customUserDetailsService.getLoggedInUserId());
        element.setIsPrivate(false);
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
        }
        tuple.store();

        ReturnWrapper result = new ReturnWrapper(getDataElementDto(element.getId()), getExcerpt(text.getId()), null,
                null, ElementType.TEXT);

        return result;
    }

    public ReturnWrapper getNext(long groupId, ElementType selectedElement) {
        checkAllowed(groupId);

        long dataElementID = 0;
        ElementType eType = null;
        ImageDto image = null;
        AudioDto recording = null;

        TextDto text = null;
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
                image = getImageDto(groupId);
                dataElementID = image.getDataElementId();
                eType = ElementType.IMAGE;
                break;
        }

        DataElementDto data = getDataElementDto(dataElementID);

        return new ReturnWrapper(data, text, recording, image, eType);
    }

    public TupleDto getNextTuple(long groupId, DataTupleType dataTupleTypeSelector) {
        checkAllowed(groupId);

        return dslContext.select().from(DATA_TUPLE).where(DATA_TUPLE.TYPE.eq(dataTupleTypeSelector)).orderBy(DSL.rand())
                .limit(1).fetchOneInto(TupleDto.class);
    }

    public AudioDto getAudio(Long groupId) {
        checkAllowed(groupId);
        return dslContext.select(AUDIO.asterisk()).from(AUDIO.innerJoin(DATA_ELEMENT).onKey()).orderBy(DSL.rand())
                .limit(1).fetchOneInto(AudioDto.class);
    }

    /**
     * return a text to be recorded based on:<br>
     * 1. if the text is not easy to understand (no more than 3 skips)<br>
     * 2. the text does not contain an error<br>
     * 3. the text was not already recorded with the same dialect<br>
     * 4. the text was not already skipped by the user.<br>
     */
    public TextDto getExcerpt(Long groupId) {
        checkAllowed(groupId);
        return dslContext.select(TEXT.ID, TEXT.DIALECT_ID, TEXT.DATA_ELEMENT_ID, TEXT.IS_SENTENCE_ERROR, TEXT.TEXT_)
                .from(TEXT.innerJoin(DATA_ELEMENT).onKey()).where(DATA_ELEMENT.USER_GROUP_ID.eq(groupId)
                        // only show the ones that are good.
                        .and(DATA_ELEMENT.SKIPPED.lessOrEqual(3L)).and(TEXT.IS_SENTENCE_ERROR.isFalse())
                        .and(DATA_ELEMENT.FINISHED.isFalse())
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
                .orderBy(DSL.rand()).limit(1).fetchOneInto(TextDto.class);
    }

    /**
     * return a image to be recorded based on:<br>
     * 1. if the image is not easy to understand (no more than 3 skips)<br>
     * 2. the image was not already recorded with the same dialect<br>
     * 3. the image was not already skipped by the user.<br>
     */
    public ImageDto getImageDto(Long groupId) {
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
                .orderBy(DSL.rand()).limit(1).fetchOneInto(ImageDto.class);
    }

    public DataElementDto getDataElementDto(Long dataElementID) {
        return dslContext.select().from(DATA_ELEMENT).where(DATA_ELEMENT.ID.eq(dataElementID))
                .fetchOneInto(DataElementDto.class);
    }

    public TextDto getTextDto(Long dataElementID) {
        return dslContext.select().from(TEXT.innerJoin(DATA_ELEMENT).onKey()).where(DATA_ELEMENT.ID.eq(dataElementID))
                .fetchOneInto(TextDto.class);
    }

    public void postCheckedOccurrence(long groupId, CheckedOccurrence checkedOccurrence) {
        checkAllowed(groupId);
        var type = CheckedDataTupleType.valueOf(checkedOccurrence.label.toString());
        var checked = dslContext.newRecord(CHECKED_DATA_TUPLE);
        checked.setDataTupleId(checkedOccurrence.id);
        checked.setUserId(customUserDetailsService.getLoggedInUserId());
        checked.setType(type);
        checked.store();
    }

    /**
     * returns the next occurrences to check based on the groupId and available
     * data.<br>
     * occurrences are labeled until it is clear that they clearly
     * wrong|correct.<br>
     */
    public List<Occurrence> getNextOccurrences(long groupId) {
        checkAllowed(groupId);
        var image_element = DATA_TUPLE.as("image_element");
        return dslContext
                .select(DATA_TUPLE.ID, DATA_TUPLE.DATA_ELEMENT_ID_1, DATA_TUPLE.DATA_ELEMENT_ID_2, TEXT.TEXT_,
                        DSL.inline(OccurrenceMode.TEXT_AUDIO.name()).as("mode"),
                        image_element.DATA_ELEMENT_ID_1.as("image_element_id"))
                .from(DATA_TUPLE.join(DATA_ELEMENT).onKey(DATA_TUPLE.DATA_ELEMENT_ID_1).join(TEXT)
                        .onKey(TEXT.DATA_ELEMENT_ID).leftJoin(image_element)
                        .on(image_element.DATA_ELEMENT_ID_2.eq(DATA_ELEMENT.ID)
                                .and(image_element.TYPE.eq(DataTupleType.IMAGE_AUDIO))))
                .where(DSL.abs(DATA_TUPLE.WRONG.minus(DATA_TUPLE.CORRECT))
                        .le(speechCollectionAppConfig.getMinNumChecks()).and(DATA_ELEMENT.USER_GROUP_ID.eq(groupId))
                        .and(DATA_TUPLE.TYPE
                                .eq(DataTupleType.RECORDING).or(DATA_TUPLE.TYPE.eq(DataTupleType.TEXT_AUDIO)))
                        .and(DATA_TUPLE.FINISHED.isFalse())
                        .and(DATA_TUPLE.ID.notIn(
                                dslContext.select(CHECKED_DATA_TUPLE.DATA_TUPLE_ID).from(CHECKED_DATA_TUPLE).where(
                                        CHECKED_DATA_TUPLE.USER_ID.eq(customUserDetailsService.getLoggedInUserId())))))
                .orderBy(DSL.rand()).limit(10).fetchInto(Occurrence.class);
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
