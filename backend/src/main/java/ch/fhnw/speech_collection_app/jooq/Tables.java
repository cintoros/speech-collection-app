/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.speech_collection_app.jooq;


import ch.fhnw.speech_collection_app.jooq.tables.CheckedRecording;
import ch.fhnw.speech_collection_app.jooq.tables.CheckedTextAudio;
import ch.fhnw.speech_collection_app.jooq.tables.Dialect;
import ch.fhnw.speech_collection_app.jooq.tables.Domain;
import ch.fhnw.speech_collection_app.jooq.tables.Excerpt;
import ch.fhnw.speech_collection_app.jooq.tables.FlywaySchemaHistory;
import ch.fhnw.speech_collection_app.jooq.tables.Language;
import ch.fhnw.speech_collection_app.jooq.tables.OriginalText;
import ch.fhnw.speech_collection_app.jooq.tables.Recording;
import ch.fhnw.speech_collection_app.jooq.tables.Source;
import ch.fhnw.speech_collection_app.jooq.tables.Speaker;
import ch.fhnw.speech_collection_app.jooq.tables.TextAudio;
import ch.fhnw.speech_collection_app.jooq.tables.User;
import ch.fhnw.speech_collection_app.jooq.tables.UserGroup;
import ch.fhnw.speech_collection_app.jooq.tables.UserGroupRole;
import ch.fhnw.speech_collection_app.jooq.tables.VerificationToken;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    public static final CheckedRecording CHECKED_RECORDING = CheckedRecording.CHECKED_RECORDING;

    public static final CheckedTextAudio CHECKED_TEXT_AUDIO = CheckedTextAudio.CHECKED_TEXT_AUDIO;

    public static final Dialect DIALECT = Dialect.DIALECT;

    public static final Domain DOMAIN = Domain.DOMAIN;

    public static final Excerpt EXCERPT = Excerpt.EXCERPT;

    public static final FlywaySchemaHistory FLYWAY_SCHEMA_HISTORY = FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY;

    public static final Language LANGUAGE = Language.LANGUAGE;

    public static final OriginalText ORIGINAL_TEXT = OriginalText.ORIGINAL_TEXT;

    public static final Recording RECORDING = Recording.RECORDING;

    public static final Source SOURCE = Source.SOURCE;

    public static final Speaker SPEAKER = Speaker.SPEAKER;

    public static final TextAudio TEXT_AUDIO = TextAudio.TEXT_AUDIO;

    public static final User USER = User.USER;

    public static final UserGroup USER_GROUP = UserGroup.USER_GROUP;

    public static final UserGroupRole USER_GROUP_ROLE = UserGroupRole.USER_GROUP_ROLE;

    public static final VerificationToken VERIFICATION_TOKEN = VerificationToken.VERIFICATION_TOKEN;
}