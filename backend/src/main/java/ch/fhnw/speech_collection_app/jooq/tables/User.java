/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.speech_collection_app.jooq.tables;


import ch.fhnw.speech_collection_app.jooq.Indexes;
import ch.fhnw.speech_collection_app.jooq.Keys;
import ch.fhnw.speech_collection_app.jooq.SpeechCollectionApp;
import ch.fhnw.speech_collection_app.jooq.enums.UserAge;
import ch.fhnw.speech_collection_app.jooq.enums.UserLicence;
import ch.fhnw.speech_collection_app.jooq.enums.UserSex;
import ch.fhnw.speech_collection_app.jooq.tables.records.UserRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row14;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class User extends TableImpl<UserRecord> {

    private static final long serialVersionUID = 1038244234;

    public static final User USER = new User();

    @Override
    public Class<UserRecord> getRecordType() {
        return UserRecord.class;
    }

    public final TableField<UserRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    public final TableField<UserRecord, String> FIRST_NAME = createField(DSL.name("first_name"), org.jooq.impl.SQLDataType.VARCHAR(100).defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    public final TableField<UserRecord, String> LAST_NAME = createField(DSL.name("last_name"), org.jooq.impl.SQLDataType.VARCHAR(100).defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    public final TableField<UserRecord, String> EMAIL = createField(DSL.name("email"), org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "");

    public final TableField<UserRecord, String> USERNAME = createField(DSL.name("username"), org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "");

    public final TableField<UserRecord, String> PASSWORD = createField(DSL.name("password"), org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "");

    public final TableField<UserRecord, UserSex> SEX = createField(DSL.name("sex"), org.jooq.impl.SQLDataType.VARCHAR(4).defaultValue(org.jooq.impl.DSL.inline("'NONE'", org.jooq.impl.SQLDataType.VARCHAR)).asEnumDataType(ch.fhnw.speech_collection_app.jooq.enums.UserSex.class), this, "");

    public final TableField<UserRecord, UserLicence> LICENCE = createField(DSL.name("licence"), org.jooq.impl.SQLDataType.VARCHAR(8).defaultValue(org.jooq.impl.DSL.inline("'PUBLIC'", org.jooq.impl.SQLDataType.VARCHAR)).asEnumDataType(ch.fhnw.speech_collection_app.jooq.enums.UserLicence.class), this, "");

    public final TableField<UserRecord, UserAge> AGE = createField(DSL.name("age"), org.jooq.impl.SQLDataType.VARCHAR(4).defaultValue(org.jooq.impl.DSL.inline("'NONE'", org.jooq.impl.SQLDataType.VARCHAR)).asEnumDataType(ch.fhnw.speech_collection_app.jooq.enums.UserAge.class), this, "");

    public final TableField<UserRecord, Boolean> ENABLED = createField(DSL.name("enabled"), org.jooq.impl.SQLDataType.BOOLEAN.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.BOOLEAN)), this, "");

    public final TableField<UserRecord, Long> DIALECT_ID = createField(DSL.name("dialect_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    public final TableField<UserRecord, Boolean> NOT_CH = createField(DSL.name("not_CH"), org.jooq.impl.SQLDataType.BOOLEAN.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BOOLEAN)), this, "");

    public final TableField<UserRecord, String> ZIP_CODE = createField(DSL.name("zip_code"), org.jooq.impl.SQLDataType.VARCHAR(45).defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    public final TableField<UserRecord, Timestamp> LAST_ONLINE = createField(DSL.name("last_online"), org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("current_timestamp()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    public User() {
        this(DSL.name("user"), null);
    }

    public User(String alias) {
        this(DSL.name(alias), USER);
    }

    public User(Name alias) {
        this(alias, USER);
    }

    private User(Name alias, Table<UserRecord> aliased) {
        this(alias, aliased, null);
    }

    private User(Name alias, Table<UserRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> User(Table<O> child, ForeignKey<O, UserRecord> key) {
        super(child, key, USER);
    }

    @Override
    public Schema getSchema() {
        return SpeechCollectionApp.SPEECH_COLLECTION_APP;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.USER_DIALECT_ID, Indexes.USER_EMAIL, Indexes.USER_PRIMARY, Indexes.USER_USERNAME);
    }

    @Override
    public Identity<UserRecord, Long> getIdentity() {
        return Keys.IDENTITY_USER;
    }

    @Override
    public UniqueKey<UserRecord> getPrimaryKey() {
        return Keys.KEY_USER_PRIMARY;
    }

    @Override
    public List<UniqueKey<UserRecord>> getKeys() {
        return Arrays.<UniqueKey<UserRecord>>asList(Keys.KEY_USER_PRIMARY, Keys.KEY_USER_EMAIL, Keys.KEY_USER_USERNAME);
    }

    @Override
    public List<ForeignKey<UserRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<UserRecord, ?>>asList(Keys.USER_IBFK_1);
    }

    public Dialect dialect() {
        return new Dialect(this, Keys.USER_IBFK_1);
    }

    @Override
    public User as(String alias) {
        return new User(DSL.name(alias), this);
    }

    @Override
    public User as(Name alias) {
        return new User(alias, this);
    }

    @Override
    public User rename(String name) {
        return new User(DSL.name(name), null);
    }

    @Override
    public User rename(Name name) {
        return new User(name, null);
    }

    // -------------------------------------------------------------------------
    // Row14 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row14<Long, String, String, String, String, String, UserSex, UserLicence, UserAge, Boolean, Long, Boolean, String, Timestamp> fieldsRow() {
        return (Row14) super.fieldsRow();
    }
}
