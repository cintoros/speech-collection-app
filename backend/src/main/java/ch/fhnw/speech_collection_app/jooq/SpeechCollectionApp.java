/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.speech_collection_app.jooq;


import ch.fhnw.speech_collection_app.jooq.tables.Achievements;
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
import ch.fhnw.speech_collection_app.jooq.tables.UserAchievements;
import ch.fhnw.speech_collection_app.jooq.tables.UserGroup;
import ch.fhnw.speech_collection_app.jooq.tables.UserGroupRole;
import ch.fhnw.speech_collection_app.jooq.tables.VerificationToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SpeechCollectionApp extends SchemaImpl {

    private static final long serialVersionUID = -1297787489;

    public static final SpeechCollectionApp SPEECH_COLLECTION_APP = new SpeechCollectionApp();

    public final Achievements ACHIEVEMENTS = ch.fhnw.speech_collection_app.jooq.tables.Achievements.ACHIEVEMENTS;

    public final Audio AUDIO = ch.fhnw.speech_collection_app.jooq.tables.Audio.AUDIO;

    public final CheckedDataElement CHECKED_DATA_ELEMENT = ch.fhnw.speech_collection_app.jooq.tables.CheckedDataElement.CHECKED_DATA_ELEMENT;

    public final CheckedDataTuple CHECKED_DATA_TUPLE = ch.fhnw.speech_collection_app.jooq.tables.CheckedDataTuple.CHECKED_DATA_TUPLE;

    public final DataElement DATA_ELEMENT = ch.fhnw.speech_collection_app.jooq.tables.DataElement.DATA_ELEMENT;

    public final DataTuple DATA_TUPLE = ch.fhnw.speech_collection_app.jooq.tables.DataTuple.DATA_TUPLE;

    public final Dialect DIALECT = ch.fhnw.speech_collection_app.jooq.tables.Dialect.DIALECT;

    public final Domain DOMAIN = ch.fhnw.speech_collection_app.jooq.tables.Domain.DOMAIN;

    public final FlywaySchemaHistory FLYWAY_SCHEMA_HISTORY = ch.fhnw.speech_collection_app.jooq.tables.FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY;

    public final Image IMAGE = ch.fhnw.speech_collection_app.jooq.tables.Image.IMAGE;

    public final Language LANGUAGE = ch.fhnw.speech_collection_app.jooq.tables.Language.LANGUAGE;

    public final Source SOURCE = ch.fhnw.speech_collection_app.jooq.tables.Source.SOURCE;

    public final Text TEXT = ch.fhnw.speech_collection_app.jooq.tables.Text.TEXT;

    public final User USER = ch.fhnw.speech_collection_app.jooq.tables.User.USER;

    public final UserAchievements USER_ACHIEVEMENTS = ch.fhnw.speech_collection_app.jooq.tables.UserAchievements.USER_ACHIEVEMENTS;

    public final UserGroup USER_GROUP = ch.fhnw.speech_collection_app.jooq.tables.UserGroup.USER_GROUP;

    public final UserGroupRole USER_GROUP_ROLE = ch.fhnw.speech_collection_app.jooq.tables.UserGroupRole.USER_GROUP_ROLE;

    public final VerificationToken VERIFICATION_TOKEN = ch.fhnw.speech_collection_app.jooq.tables.VerificationToken.VERIFICATION_TOKEN;

    private SpeechCollectionApp() {
        super("speech_collection_app", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            Achievements.ACHIEVEMENTS,
            Audio.AUDIO,
            CheckedDataElement.CHECKED_DATA_ELEMENT,
            CheckedDataTuple.CHECKED_DATA_TUPLE,
            DataElement.DATA_ELEMENT,
            DataTuple.DATA_TUPLE,
            Dialect.DIALECT,
            Domain.DOMAIN,
            FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY,
            Image.IMAGE,
            Language.LANGUAGE,
            Source.SOURCE,
            Text.TEXT,
            User.USER,
            UserAchievements.USER_ACHIEVEMENTS,
            UserGroup.USER_GROUP,
            UserGroupRole.USER_GROUP_ROLE,
            VerificationToken.VERIFICATION_TOKEN);
    }
}
