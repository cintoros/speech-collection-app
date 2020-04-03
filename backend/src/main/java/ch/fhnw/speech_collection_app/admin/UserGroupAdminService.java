package ch.fhnw.speech_collection_app.admin;

import ch.fhnw.speech_collection_app.config.SpeechCollectionAppConfig;
import ch.fhnw.speech_collection_app.jooq.enums.RecordingLabel;
import ch.fhnw.speech_collection_app.jooq.enums.UserGroupRoleRole;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.Domain;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.TextAudio;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.UserGroupRole;
import ch.fhnw.speech_collection_app.jooq.tables.records.TextAudioRecord;
import ch.fhnw.speech_collection_app.user.CustomUserDetailsService;
import ch.fhnw.speech_collection_app.user_group.OccurrenceMode;
import ch.fhnw.speech_collection_app.user_group.OverviewOccurrence;
import org.apache.tika.io.IOUtils;
import org.jooq.DSLContext;
import org.jooq.Record5;
import org.jooq.SelectOrderByStep;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static ch.fhnw.speech_collection_app.jooq.Tables.*;

@Service
public class UserGroupAdminService {
    private final CustomUserDetailsService customUserDetailsService;
    private final DSLContext dslContext;
    private final SpeechCollectionAppConfig speechCollectionAppConfig;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public UserGroupAdminService(CustomUserDetailsService customUserDetailsService, DSLContext dslContext, SpeechCollectionAppConfig speechCollectionAppConfig) {
        this.customUserDetailsService = customUserDetailsService;
        this.dslContext = dslContext;
        this.speechCollectionAppConfig = speechCollectionAppConfig;
    }

    public void putTextAudio(long groupId, TextAudio textAudio) {
        isAllowed(groupId);
        TextAudioRecord textAudioRecord = dslContext.newRecord(TEXT_AUDIO, textAudio);
        textAudioRecord.setCorrect(0L);
        textAudioRecord.setWrong(0L);
        textAudioRecord.update();
        dslContext.delete(CHECKED_TEXT_AUDIO).where(CHECKED_TEXT_AUDIO.TEXT_AUDIO_ID.eq(textAudio.getId())).execute();
        try {
            var process = Runtime.getRuntime().exec(speechCollectionAppConfig.getCondaExec() + " 2 " + textAudio.getId());
            List<String> list = IOUtils.readLines(process.getErrorStream());
            if (!list.isEmpty()) {
                logger.error(String.join("\n", list));
            }
        } catch (IOException e) {
            logger.error("Exception Raised", e);
        }

    }

    public List<OverviewOccurrence> getOverviewOccurrence(long groupId) {
        isAllowed(groupId);
        //TODO implement pagination for increased performance
        var step = dslContext.select(RECORDING.ID, RECORDING.CORRECT, RECORDING.WRONG, EXCERPT.EXCERPT_.as("text"), DSL.inline(OccurrenceMode.RECORDING.name()).as("mode"))
                .from(RECORDING.join(EXCERPT).onKey().join(ORIGINAL_TEXT).onKey())
                .where(RECORDING.LABEL.eq(RecordingLabel.RECORDED).and(ORIGINAL_TEXT.USER_GROUP_ID.eq(groupId).and(RECORDING.CORRECT.plus(RECORDING.WRONG).ge(0L))));
        if (speechCollectionAppConfig.getPublicGroupId() == groupId) {
            SelectOrderByStep<Record5<Long, Long, Long, String, String>> mode = dslContext.select(TEXT_AUDIO.ID, TEXT_AUDIO.CORRECT, TEXT_AUDIO.WRONG, TEXT_AUDIO.TEXT, DSL.inline(OccurrenceMode.TEXT_AUDIO.name()).as("mode"))
                    .from(TEXT_AUDIO)
                    .where(TEXT_AUDIO.CORRECT.plus(TEXT_AUDIO.WRONG).ge(0L))
                    .union(step);
            return mode.fetchInto(OverviewOccurrence.class);
        }
        return step.fetchInto(OverviewOccurrence.class);
    }

    public List<UserGroupRoleDto> getUserGroupRole(UserGroupRoleRole mode, long groupId) {
        isAllowed(groupId);
        var conditionStep = dslContext.select(USER_GROUP_ROLE.ID, USER.USERNAME, USER.EMAIL)
                .from(USER_GROUP_ROLE.join(USER).onKey())
                .where(USER_GROUP_ROLE.ROLE.eq(mode));
        if (mode == UserGroupRoleRole.ADMIN) {
            if (!customUserDetailsService.isAdmin()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        } else {
            conditionStep = conditionStep.and(USER_GROUP_ROLE.USER_GROUP_ID.eq(groupId));
        }
        return conditionStep.fetchInto(UserGroupRoleDto.class);
    }

    public boolean postUserGroupRole(String email, UserGroupRoleRole mode, long groupId) {
        isAllowed(groupId);
        if (mode == UserGroupRoleRole.ADMIN) {
            if (!customUserDetailsService.isAdmin()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        var opt = dslContext.selectFrom(USER).where(USER.USERNAME.eq(email)).fetchOptional()
                .or(() -> dslContext.selectFrom(USER).where(USER.EMAIL.eq(email)).fetchOptional());
        opt.ifPresent(user -> dslContext.newRecord(USER_GROUP_ROLE, (new UserGroupRole(null, mode, user.getId(), groupId))).store());
        return opt.isPresent();
    }

    public void deleteUserGroupRole(long id) {
        var groupId = dslContext.selectFrom(USER_GROUP_ROLE).where(USER_GROUP_ROLE.ID.eq(id)).fetchOne(USER_GROUP_ROLE.USER_GROUP_ID);
        isAllowed(groupId);
        dslContext.delete(USER_GROUP_ROLE).where(USER_GROUP_ROLE.ID.eq(id)).execute();
    }

    private void isAllowed(long groupId) {
        if (!customUserDetailsService.isAllowedOnProject(groupId, true))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    public TextAudio getTextAudio(long groupId, Long textAudioId) {
        isAllowed(groupId);
        return dslContext.selectFrom(TEXT_AUDIO).where(TEXT_AUDIO.ID.eq(textAudioId)).fetchOneInto(TextAudio.class);
    }

    public byte[] getTextAudioAudio(long groupId, Long textAudioId) throws IOException {
        isAllowed(groupId);
        var textAudio = dslContext.selectFrom(TEXT_AUDIO.join(SOURCE).onKey()).where(TEXT_AUDIO.ID.eq(textAudioId)).fetchOne(SOURCE.RAW_AUDIO_PATH);
        return Files.readAllBytes(speechCollectionAppConfig.getBasePath().resolve(textAudio));
    }

    public void putDescription(long groupId, String description) {
        isAllowed(groupId);
        dslContext.update(USER_GROUP).set(USER_GROUP.DESCRIPTION, description).execute();
    }

    public List<Domain> getDomain() {
        return dslContext.selectFrom(DOMAIN).fetchInto(Domain.class);
    }
}
