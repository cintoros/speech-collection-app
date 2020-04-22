package ch.fhnw.speech_collection_app.features.base.admin;

import ch.fhnw.speech_collection_app.config.SpeechCollectionAppConfig;
import ch.fhnw.speech_collection_app.features.base.user.CustomUserDetailsService;
import ch.fhnw.speech_collection_app.features.base.user_group.OccurrenceMode;
import ch.fhnw.speech_collection_app.features.base.user_group.OverviewOccurrence;
import ch.fhnw.speech_collection_app.jooq.enums.UserGroupRoleRole;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.DataTuple;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.Domain;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.UserGroupRole;
import org.apache.tika.io.IOUtils;
import org.jooq.DSLContext;
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

    public void putTextAudio(long groupId, DataTuple textAudio) {
        isAllowed(groupId);
        dslContext.delete(CHECKED_DATA_TUPLE).where(CHECKED_DATA_TUPLE.DATA_TUPLE_ID.eq(textAudio.getId())).execute();
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

    //TODO maybe also add a overview for the elements
    public List<OverviewOccurrence> getOverviewOccurrence(long groupId) {
        isAllowed(groupId);
        //TODO implement pagination for increased performance
        //TODO not sure how we want to handle the text as some are image -> text etc.
        //TODO maybe add a drop down to filter per type
        return dslContext.select(DATA_TUPLE.ID, DATA_TUPLE.CORRECT, DATA_TUPLE.WRONG, DSL.inline("TODO add text"),
                DSL.inline(OccurrenceMode.RECORDING.name()).as("mode"))
                .from(DATA_TUPLE.join(DATA_ELEMENT).onKey(DATA_TUPLE.DATA_ELEMENT_ID_1))
                .where(DATA_ELEMENT.USER_GROUP_ID.eq(groupId).and(DATA_TUPLE.CORRECT.plus(DATA_TUPLE.WRONG).ge(0L)))
                .fetchInto(OverviewOccurrence.class);
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

    public TextAudioDto getTextAudio(long groupId, Long textAudioId) {
        isAllowed(groupId);
        //TODO implement dto that contains both the text and audio information
        return dslContext.selectFrom(AUDIO).where(AUDIO.ID.eq(textAudioId)).fetchOneInto(TextAudioDto.class);
    }

    public byte[] getTextAudioAudio(long groupId, Long textAudioId) throws IOException {
        isAllowed(groupId);
        var textAudio = dslContext.selectFrom(AUDIO.join(DATA_ELEMENT).onKey().join(SOURCE).onKey())
                .where(AUDIO.ID.eq(textAudioId))
                .fetchOne(SOURCE.PATH_TO_RAW_FILE);
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
