/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.speech_collection_app.jooq;


import ch.fhnw.speech_collection_app.jooq.tables.Audio;
import ch.fhnw.speech_collection_app.jooq.tables.CheckedDataElement;
import ch.fhnw.speech_collection_app.jooq.tables.CheckedDataTuple;
import ch.fhnw.speech_collection_app.jooq.tables.DataElement;
import ch.fhnw.speech_collection_app.jooq.tables.DataTuple;
import ch.fhnw.speech_collection_app.jooq.tables.Dialect;
import ch.fhnw.speech_collection_app.jooq.tables.Domain;
import ch.fhnw.speech_collection_app.jooq.tables.FlywaySchemaHistory;
import ch.fhnw.speech_collection_app.jooq.tables.Image;
import ch.fhnw.speech_collection_app.jooq.tables.Language;
import ch.fhnw.speech_collection_app.jooq.tables.Source;
import ch.fhnw.speech_collection_app.jooq.tables.Text;
import ch.fhnw.speech_collection_app.jooq.tables.User;
import ch.fhnw.speech_collection_app.jooq.tables.UserGroup;
import ch.fhnw.speech_collection_app.jooq.tables.UserGroupRole;
import ch.fhnw.speech_collection_app.jooq.tables.VerificationToken;
import ch.fhnw.speech_collection_app.jooq.tables.records.AudioRecord;
import ch.fhnw.speech_collection_app.jooq.tables.records.CheckedDataElementRecord;
import ch.fhnw.speech_collection_app.jooq.tables.records.CheckedDataTupleRecord;
import ch.fhnw.speech_collection_app.jooq.tables.records.DataElementRecord;
import ch.fhnw.speech_collection_app.jooq.tables.records.DataTupleRecord;
import ch.fhnw.speech_collection_app.jooq.tables.records.DialectRecord;
import ch.fhnw.speech_collection_app.jooq.tables.records.DomainRecord;
import ch.fhnw.speech_collection_app.jooq.tables.records.FlywaySchemaHistoryRecord;
import ch.fhnw.speech_collection_app.jooq.tables.records.ImageRecord;
import ch.fhnw.speech_collection_app.jooq.tables.records.LanguageRecord;
import ch.fhnw.speech_collection_app.jooq.tables.records.SourceRecord;
import ch.fhnw.speech_collection_app.jooq.tables.records.TextRecord;
import ch.fhnw.speech_collection_app.jooq.tables.records.UserGroupRecord;
import ch.fhnw.speech_collection_app.jooq.tables.records.UserGroupRoleRecord;
import ch.fhnw.speech_collection_app.jooq.tables.records.UserRecord;
import ch.fhnw.speech_collection_app.jooq.tables.records.VerificationTokenRecord;

import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<AudioRecord, Long> IDENTITY_AUDIO = Identities0.IDENTITY_AUDIO;
    public static final Identity<CheckedDataElementRecord, Long> IDENTITY_CHECKED_DATA_ELEMENT = Identities0.IDENTITY_CHECKED_DATA_ELEMENT;
    public static final Identity<CheckedDataTupleRecord, Long> IDENTITY_CHECKED_DATA_TUPLE = Identities0.IDENTITY_CHECKED_DATA_TUPLE;
    public static final Identity<DataElementRecord, Long> IDENTITY_DATA_ELEMENT = Identities0.IDENTITY_DATA_ELEMENT;
    public static final Identity<DataTupleRecord, Long> IDENTITY_DATA_TUPLE = Identities0.IDENTITY_DATA_TUPLE;
    public static final Identity<DialectRecord, Long> IDENTITY_DIALECT = Identities0.IDENTITY_DIALECT;
    public static final Identity<DomainRecord, Long> IDENTITY_DOMAIN = Identities0.IDENTITY_DOMAIN;
    public static final Identity<ImageRecord, Long> IDENTITY_IMAGE = Identities0.IDENTITY_IMAGE;
    public static final Identity<LanguageRecord, Long> IDENTITY_LANGUAGE = Identities0.IDENTITY_LANGUAGE;
    public static final Identity<SourceRecord, Long> IDENTITY_SOURCE = Identities0.IDENTITY_SOURCE;
    public static final Identity<TextRecord, Long> IDENTITY_TEXT = Identities0.IDENTITY_TEXT;
    public static final Identity<UserRecord, Long> IDENTITY_USER = Identities0.IDENTITY_USER;
    public static final Identity<UserGroupRecord, Long> IDENTITY_USER_GROUP = Identities0.IDENTITY_USER_GROUP;
    public static final Identity<UserGroupRoleRecord, Long> IDENTITY_USER_GROUP_ROLE = Identities0.IDENTITY_USER_GROUP_ROLE;
    public static final Identity<VerificationTokenRecord, Long> IDENTITY_VERIFICATION_TOKEN = Identities0.IDENTITY_VERIFICATION_TOKEN;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<AudioRecord> KEY_AUDIO_PRIMARY = UniqueKeys0.KEY_AUDIO_PRIMARY;
    public static final UniqueKey<CheckedDataElementRecord> KEY_CHECKED_DATA_ELEMENT_PRIMARY = UniqueKeys0.KEY_CHECKED_DATA_ELEMENT_PRIMARY;
    public static final UniqueKey<CheckedDataTupleRecord> KEY_CHECKED_DATA_TUPLE_PRIMARY = UniqueKeys0.KEY_CHECKED_DATA_TUPLE_PRIMARY;
    public static final UniqueKey<DataElementRecord> KEY_DATA_ELEMENT_PRIMARY = UniqueKeys0.KEY_DATA_ELEMENT_PRIMARY;
    public static final UniqueKey<DataTupleRecord> KEY_DATA_TUPLE_PRIMARY = UniqueKeys0.KEY_DATA_TUPLE_PRIMARY;
    public static final UniqueKey<DialectRecord> KEY_DIALECT_PRIMARY = UniqueKeys0.KEY_DIALECT_PRIMARY;
    public static final UniqueKey<DomainRecord> KEY_DOMAIN_PRIMARY = UniqueKeys0.KEY_DOMAIN_PRIMARY;
    public static final UniqueKey<FlywaySchemaHistoryRecord> KEY_FLYWAY_SCHEMA_HISTORY_PRIMARY = UniqueKeys0.KEY_FLYWAY_SCHEMA_HISTORY_PRIMARY;
    public static final UniqueKey<ImageRecord> KEY_IMAGE_PRIMARY = UniqueKeys0.KEY_IMAGE_PRIMARY;
    public static final UniqueKey<LanguageRecord> KEY_LANGUAGE_PRIMARY = UniqueKeys0.KEY_LANGUAGE_PRIMARY;
    public static final UniqueKey<SourceRecord> KEY_SOURCE_PRIMARY = UniqueKeys0.KEY_SOURCE_PRIMARY;
    public static final UniqueKey<TextRecord> KEY_TEXT_PRIMARY = UniqueKeys0.KEY_TEXT_PRIMARY;
    public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = UniqueKeys0.KEY_USER_PRIMARY;
    public static final UniqueKey<UserRecord> KEY_USER_EMAIL = UniqueKeys0.KEY_USER_EMAIL;
    public static final UniqueKey<UserRecord> KEY_USER_USERNAME = UniqueKeys0.KEY_USER_USERNAME;
    public static final UniqueKey<UserGroupRecord> KEY_USER_GROUP_PRIMARY = UniqueKeys0.KEY_USER_GROUP_PRIMARY;
    public static final UniqueKey<UserGroupRoleRecord> KEY_USER_GROUP_ROLE_PRIMARY = UniqueKeys0.KEY_USER_GROUP_ROLE_PRIMARY;
    public static final UniqueKey<VerificationTokenRecord> KEY_VERIFICATION_TOKEN_PRIMARY = UniqueKeys0.KEY_VERIFICATION_TOKEN_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<AudioRecord, DialectRecord> AUDIO_IBFK_1 = ForeignKeys0.AUDIO_IBFK_1;
    public static final ForeignKey<AudioRecord, DataElementRecord> AUDIO_IBFK_2 = ForeignKeys0.AUDIO_IBFK_2;
    public static final ForeignKey<CheckedDataElementRecord, UserRecord> CHECKED_DATA_ELEMENT_IBFK_1 = ForeignKeys0.CHECKED_DATA_ELEMENT_IBFK_1;
    public static final ForeignKey<CheckedDataElementRecord, DataElementRecord> CHECKED_DATA_ELEMENT_IBFK_2 = ForeignKeys0.CHECKED_DATA_ELEMENT_IBFK_2;
    public static final ForeignKey<CheckedDataTupleRecord, UserRecord> CHECKED_DATA_TUPLE_IBFK_1 = ForeignKeys0.CHECKED_DATA_TUPLE_IBFK_1;
    public static final ForeignKey<CheckedDataTupleRecord, DataTupleRecord> CHECKED_DATA_TUPLE_IBFK_2 = ForeignKeys0.CHECKED_DATA_TUPLE_IBFK_2;
    public static final ForeignKey<DataElementRecord, SourceRecord> DATA_ELEMENT_IBFK_1 = ForeignKeys0.DATA_ELEMENT_IBFK_1;
    public static final ForeignKey<DataElementRecord, UserRecord> DATA_ELEMENT_IBFK_2 = ForeignKeys0.DATA_ELEMENT_IBFK_2;
    public static final ForeignKey<DataElementRecord, UserGroupRecord> DATA_ELEMENT_IBFK_3 = ForeignKeys0.DATA_ELEMENT_IBFK_3;
    public static final ForeignKey<DataTupleRecord, DataElementRecord> DATA_TUPLE_IBFK_1 = ForeignKeys0.DATA_TUPLE_IBFK_1;
    public static final ForeignKey<DataTupleRecord, DataElementRecord> DATA_TUPLE_IBFK_2 = ForeignKeys0.DATA_TUPLE_IBFK_2;
    public static final ForeignKey<DialectRecord, LanguageRecord> DIALECT_IBFK_1 = ForeignKeys0.DIALECT_IBFK_1;
    public static final ForeignKey<ImageRecord, DataElementRecord> IMAGE_IBFK_1 = ForeignKeys0.IMAGE_IBFK_1;
    public static final ForeignKey<SourceRecord, UserRecord> SOURCE_IBFK_1 = ForeignKeys0.SOURCE_IBFK_1;
    public static final ForeignKey<SourceRecord, DialectRecord> SOURCE_IBFK_2 = ForeignKeys0.SOURCE_IBFK_2;
    public static final ForeignKey<SourceRecord, DomainRecord> SOURCE_IBFK_3 = ForeignKeys0.SOURCE_IBFK_3;
    public static final ForeignKey<SourceRecord, UserGroupRecord> SOURCE_IBFK_4 = ForeignKeys0.SOURCE_IBFK_4;
    public static final ForeignKey<TextRecord, DialectRecord> TEXT_IBFK_1 = ForeignKeys0.TEXT_IBFK_1;
    public static final ForeignKey<TextRecord, DataElementRecord> TEXT_IBFK_2 = ForeignKeys0.TEXT_IBFK_2;
    public static final ForeignKey<UserRecord, DialectRecord> USER_IBFK_1 = ForeignKeys0.USER_IBFK_1;
    public static final ForeignKey<UserGroupRoleRecord, UserRecord> USER_GROUP_ROLE_IBFK_2 = ForeignKeys0.USER_GROUP_ROLE_IBFK_2;
    public static final ForeignKey<UserGroupRoleRecord, UserGroupRecord> USER_GROUP_ROLE_IBFK_1 = ForeignKeys0.USER_GROUP_ROLE_IBFK_1;
    public static final ForeignKey<VerificationTokenRecord, UserRecord> VERIFICATION_TOKEN_IBFK_1 = ForeignKeys0.VERIFICATION_TOKEN_IBFK_1;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<AudioRecord, Long> IDENTITY_AUDIO = Internal.createIdentity(Audio.AUDIO, Audio.AUDIO.ID);
        public static Identity<CheckedDataElementRecord, Long> IDENTITY_CHECKED_DATA_ELEMENT = Internal.createIdentity(CheckedDataElement.CHECKED_DATA_ELEMENT, CheckedDataElement.CHECKED_DATA_ELEMENT.ID);
        public static Identity<CheckedDataTupleRecord, Long> IDENTITY_CHECKED_DATA_TUPLE = Internal.createIdentity(CheckedDataTuple.CHECKED_DATA_TUPLE, CheckedDataTuple.CHECKED_DATA_TUPLE.ID);
        public static Identity<DataElementRecord, Long> IDENTITY_DATA_ELEMENT = Internal.createIdentity(DataElement.DATA_ELEMENT, DataElement.DATA_ELEMENT.ID);
        public static Identity<DataTupleRecord, Long> IDENTITY_DATA_TUPLE = Internal.createIdentity(DataTuple.DATA_TUPLE, DataTuple.DATA_TUPLE.ID);
        public static Identity<DialectRecord, Long> IDENTITY_DIALECT = Internal.createIdentity(Dialect.DIALECT, Dialect.DIALECT.ID);
        public static Identity<DomainRecord, Long> IDENTITY_DOMAIN = Internal.createIdentity(Domain.DOMAIN, Domain.DOMAIN.ID);
        public static Identity<ImageRecord, Long> IDENTITY_IMAGE = Internal.createIdentity(Image.IMAGE, Image.IMAGE.ID);
        public static Identity<LanguageRecord, Long> IDENTITY_LANGUAGE = Internal.createIdentity(Language.LANGUAGE, Language.LANGUAGE.ID);
        public static Identity<SourceRecord, Long> IDENTITY_SOURCE = Internal.createIdentity(Source.SOURCE, Source.SOURCE.ID);
        public static Identity<TextRecord, Long> IDENTITY_TEXT = Internal.createIdentity(Text.TEXT, Text.TEXT.ID);
        public static Identity<UserRecord, Long> IDENTITY_USER = Internal.createIdentity(User.USER, User.USER.ID);
        public static Identity<UserGroupRecord, Long> IDENTITY_USER_GROUP = Internal.createIdentity(UserGroup.USER_GROUP, UserGroup.USER_GROUP.ID);
        public static Identity<UserGroupRoleRecord, Long> IDENTITY_USER_GROUP_ROLE = Internal.createIdentity(UserGroupRole.USER_GROUP_ROLE, UserGroupRole.USER_GROUP_ROLE.ID);
        public static Identity<VerificationTokenRecord, Long> IDENTITY_VERIFICATION_TOKEN = Internal.createIdentity(VerificationToken.VERIFICATION_TOKEN, VerificationToken.VERIFICATION_TOKEN.ID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<AudioRecord> KEY_AUDIO_PRIMARY = Internal.createUniqueKey(Audio.AUDIO, "KEY_audio_PRIMARY", new TableField[] { Audio.AUDIO.ID }, true);
        public static final UniqueKey<CheckedDataElementRecord> KEY_CHECKED_DATA_ELEMENT_PRIMARY = Internal.createUniqueKey(CheckedDataElement.CHECKED_DATA_ELEMENT, "KEY_checked_data_element_PRIMARY", new TableField[] { CheckedDataElement.CHECKED_DATA_ELEMENT.ID }, true);
        public static final UniqueKey<CheckedDataTupleRecord> KEY_CHECKED_DATA_TUPLE_PRIMARY = Internal.createUniqueKey(CheckedDataTuple.CHECKED_DATA_TUPLE, "KEY_checked_data_tuple_PRIMARY", new TableField[] { CheckedDataTuple.CHECKED_DATA_TUPLE.ID }, true);
        public static final UniqueKey<DataElementRecord> KEY_DATA_ELEMENT_PRIMARY = Internal.createUniqueKey(DataElement.DATA_ELEMENT, "KEY_data_element_PRIMARY", new TableField[] { DataElement.DATA_ELEMENT.ID }, true);
        public static final UniqueKey<DataTupleRecord> KEY_DATA_TUPLE_PRIMARY = Internal.createUniqueKey(DataTuple.DATA_TUPLE, "KEY_data_tuple_PRIMARY", new TableField[] { DataTuple.DATA_TUPLE.ID }, true);
        public static final UniqueKey<DialectRecord> KEY_DIALECT_PRIMARY = Internal.createUniqueKey(Dialect.DIALECT, "KEY_dialect_PRIMARY", new TableField[] { Dialect.DIALECT.ID }, true);
        public static final UniqueKey<DomainRecord> KEY_DOMAIN_PRIMARY = Internal.createUniqueKey(Domain.DOMAIN, "KEY_domain_PRIMARY", new TableField[] { Domain.DOMAIN.ID }, true);
        public static final UniqueKey<FlywaySchemaHistoryRecord> KEY_FLYWAY_SCHEMA_HISTORY_PRIMARY = Internal.createUniqueKey(FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY, "KEY_flyway_schema_history_PRIMARY", new TableField[] { FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.INSTALLED_RANK }, true);
        public static final UniqueKey<ImageRecord> KEY_IMAGE_PRIMARY = Internal.createUniqueKey(Image.IMAGE, "KEY_image_PRIMARY", new TableField[] { Image.IMAGE.ID }, true);
        public static final UniqueKey<LanguageRecord> KEY_LANGUAGE_PRIMARY = Internal.createUniqueKey(Language.LANGUAGE, "KEY_language_PRIMARY", new TableField[] { Language.LANGUAGE.ID }, true);
        public static final UniqueKey<SourceRecord> KEY_SOURCE_PRIMARY = Internal.createUniqueKey(Source.SOURCE, "KEY_source_PRIMARY", new TableField[] { Source.SOURCE.ID }, true);
        public static final UniqueKey<TextRecord> KEY_TEXT_PRIMARY = Internal.createUniqueKey(Text.TEXT, "KEY_text_PRIMARY", new TableField[] { Text.TEXT.ID }, true);
        public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = Internal.createUniqueKey(User.USER, "KEY_user_PRIMARY", new TableField[] { User.USER.ID }, true);
        public static final UniqueKey<UserRecord> KEY_USER_EMAIL = Internal.createUniqueKey(User.USER, "KEY_user_email", new TableField[] { User.USER.EMAIL }, true);
        public static final UniqueKey<UserRecord> KEY_USER_USERNAME = Internal.createUniqueKey(User.USER, "KEY_user_username", new TableField[] { User.USER.USERNAME }, true);
        public static final UniqueKey<UserGroupRecord> KEY_USER_GROUP_PRIMARY = Internal.createUniqueKey(UserGroup.USER_GROUP, "KEY_user_group_PRIMARY", new TableField[] { UserGroup.USER_GROUP.ID }, true);
        public static final UniqueKey<UserGroupRoleRecord> KEY_USER_GROUP_ROLE_PRIMARY = Internal.createUniqueKey(UserGroupRole.USER_GROUP_ROLE, "KEY_user_group_role_PRIMARY", new TableField[] { UserGroupRole.USER_GROUP_ROLE.ID }, true);
        public static final UniqueKey<VerificationTokenRecord> KEY_VERIFICATION_TOKEN_PRIMARY = Internal.createUniqueKey(VerificationToken.VERIFICATION_TOKEN, "KEY_verification_token_PRIMARY", new TableField[] { VerificationToken.VERIFICATION_TOKEN.ID }, true);
    }

    private static class ForeignKeys0 {
        public static final ForeignKey<AudioRecord, DialectRecord> AUDIO_IBFK_1 = Internal.createForeignKey(Keys.KEY_DIALECT_PRIMARY, Audio.AUDIO, "audio_ibfk_1", new TableField[] { Audio.AUDIO.DIALECT_ID }, true);
        public static final ForeignKey<AudioRecord, DataElementRecord> AUDIO_IBFK_2 = Internal.createForeignKey(Keys.KEY_DATA_ELEMENT_PRIMARY, Audio.AUDIO, "audio_ibfk_2", new TableField[] { Audio.AUDIO.DATA_ELEMENT_ID }, true);
        public static final ForeignKey<CheckedDataElementRecord, UserRecord> CHECKED_DATA_ELEMENT_IBFK_1 = Internal.createForeignKey(Keys.KEY_USER_PRIMARY, CheckedDataElement.CHECKED_DATA_ELEMENT, "checked_data_element_ibfk_1", new TableField[] { CheckedDataElement.CHECKED_DATA_ELEMENT.USER_ID }, true);
        public static final ForeignKey<CheckedDataElementRecord, DataElementRecord> CHECKED_DATA_ELEMENT_IBFK_2 = Internal.createForeignKey(Keys.KEY_DATA_ELEMENT_PRIMARY, CheckedDataElement.CHECKED_DATA_ELEMENT, "checked_data_element_ibfk_2", new TableField[] { CheckedDataElement.CHECKED_DATA_ELEMENT.DATA_ELEMENT_ID }, true);
        public static final ForeignKey<CheckedDataTupleRecord, UserRecord> CHECKED_DATA_TUPLE_IBFK_1 = Internal.createForeignKey(Keys.KEY_USER_PRIMARY, CheckedDataTuple.CHECKED_DATA_TUPLE, "checked_data_tuple_ibfk_1", new TableField[] { CheckedDataTuple.CHECKED_DATA_TUPLE.USER_ID }, true);
        public static final ForeignKey<CheckedDataTupleRecord, DataTupleRecord> CHECKED_DATA_TUPLE_IBFK_2 = Internal.createForeignKey(Keys.KEY_DATA_TUPLE_PRIMARY, CheckedDataTuple.CHECKED_DATA_TUPLE, "checked_data_tuple_ibfk_2", new TableField[] { CheckedDataTuple.CHECKED_DATA_TUPLE.DATA_TUPLE_ID }, true);
        public static final ForeignKey<DataElementRecord, SourceRecord> DATA_ELEMENT_IBFK_1 = Internal.createForeignKey(Keys.KEY_SOURCE_PRIMARY, DataElement.DATA_ELEMENT, "data_element_ibfk_1", new TableField[] { DataElement.DATA_ELEMENT.SOURCE_ID }, true);
        public static final ForeignKey<DataElementRecord, UserRecord> DATA_ELEMENT_IBFK_2 = Internal.createForeignKey(Keys.KEY_USER_PRIMARY, DataElement.DATA_ELEMENT, "data_element_ibfk_2", new TableField[] { DataElement.DATA_ELEMENT.USER_ID }, true);
        public static final ForeignKey<DataElementRecord, UserGroupRecord> DATA_ELEMENT_IBFK_3 = Internal.createForeignKey(Keys.KEY_USER_GROUP_PRIMARY, DataElement.DATA_ELEMENT, "data_element_ibfk_3", new TableField[] { DataElement.DATA_ELEMENT.USER_GROUP_ID }, true);
        public static final ForeignKey<DataTupleRecord, DataElementRecord> DATA_TUPLE_IBFK_1 = Internal.createForeignKey(Keys.KEY_DATA_ELEMENT_PRIMARY, DataTuple.DATA_TUPLE, "data_tuple_ibfk_1", new TableField[] { DataTuple.DATA_TUPLE.DATA_ELEMENT_ID_1 }, true);
        public static final ForeignKey<DataTupleRecord, DataElementRecord> DATA_TUPLE_IBFK_2 = Internal.createForeignKey(Keys.KEY_DATA_ELEMENT_PRIMARY, DataTuple.DATA_TUPLE, "data_tuple_ibfk_2", new TableField[] { DataTuple.DATA_TUPLE.DATA_ELEMENT_ID_2 }, true);
        public static final ForeignKey<DialectRecord, LanguageRecord> DIALECT_IBFK_1 = Internal.createForeignKey(Keys.KEY_LANGUAGE_PRIMARY, Dialect.DIALECT, "dialect_ibfk_1", new TableField[] { Dialect.DIALECT.LANGUAGE_ID }, true);
        public static final ForeignKey<ImageRecord, DataElementRecord> IMAGE_IBFK_1 = Internal.createForeignKey(Keys.KEY_DATA_ELEMENT_PRIMARY, Image.IMAGE, "image_ibfk_1", new TableField[] { Image.IMAGE.DATA_ELEMENT_ID }, true);
        public static final ForeignKey<SourceRecord, UserRecord> SOURCE_IBFK_1 = Internal.createForeignKey(Keys.KEY_USER_PRIMARY, Source.SOURCE, "source_ibfk_1", new TableField[] { Source.SOURCE.USER_ID }, true);
        public static final ForeignKey<SourceRecord, DialectRecord> SOURCE_IBFK_2 = Internal.createForeignKey(Keys.KEY_DIALECT_PRIMARY, Source.SOURCE, "source_ibfk_2", new TableField[] { Source.SOURCE.DIALECT_ID }, true);
        public static final ForeignKey<SourceRecord, DomainRecord> SOURCE_IBFK_3 = Internal.createForeignKey(Keys.KEY_DOMAIN_PRIMARY, Source.SOURCE, "source_ibfk_3", new TableField[] { Source.SOURCE.DOMAIN_ID }, true);
        public static final ForeignKey<SourceRecord, UserGroupRecord> SOURCE_IBFK_4 = Internal.createForeignKey(Keys.KEY_USER_GROUP_PRIMARY, Source.SOURCE, "source_ibfk_4", new TableField[] { Source.SOURCE.USER_GROUP_ID }, true);
        public static final ForeignKey<TextRecord, DialectRecord> TEXT_IBFK_1 = Internal.createForeignKey(Keys.KEY_DIALECT_PRIMARY, Text.TEXT, "text_ibfk_1", new TableField[] { Text.TEXT.DIALECT_ID }, true);
        public static final ForeignKey<TextRecord, DataElementRecord> TEXT_IBFK_2 = Internal.createForeignKey(Keys.KEY_DATA_ELEMENT_PRIMARY, Text.TEXT, "text_ibfk_2", new TableField[] { Text.TEXT.DATA_ELEMENT_ID }, true);
        public static final ForeignKey<UserRecord, DialectRecord> USER_IBFK_1 = Internal.createForeignKey(Keys.KEY_DIALECT_PRIMARY, User.USER, "user_ibfk_1", new TableField[] { User.USER.DIALECT_ID }, true);
        public static final ForeignKey<UserGroupRoleRecord, UserRecord> USER_GROUP_ROLE_IBFK_2 = Internal.createForeignKey(Keys.KEY_USER_PRIMARY, UserGroupRole.USER_GROUP_ROLE, "user_group_role_ibfk_2", new TableField[] { UserGroupRole.USER_GROUP_ROLE.USER_ID }, true);
        public static final ForeignKey<UserGroupRoleRecord, UserGroupRecord> USER_GROUP_ROLE_IBFK_1 = Internal.createForeignKey(Keys.KEY_USER_GROUP_PRIMARY, UserGroupRole.USER_GROUP_ROLE, "user_group_role_ibfk_1", new TableField[] { UserGroupRole.USER_GROUP_ROLE.USER_GROUP_ID }, true);
        public static final ForeignKey<VerificationTokenRecord, UserRecord> VERIFICATION_TOKEN_IBFK_1 = Internal.createForeignKey(Keys.KEY_USER_PRIMARY, VerificationToken.VERIFICATION_TOKEN, "verification_token_ibfk_1", new TableField[] { VerificationToken.VERIFICATION_TOKEN.USER_ID }, true);
    }
}
