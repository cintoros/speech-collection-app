/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.speech_collection_app.jooq.tables;


import ch.fhnw.speech_collection_app.jooq.Keys;
import ch.fhnw.speech_collection_app.jooq.SpeechCollectionApp;
import ch.fhnw.speech_collection_app.jooq.tables.records.LanguageRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row3;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Language extends TableImpl<LanguageRecord> {

    private static final long serialVersionUID = -21170569;

    public static final Language LANGUAGE = new Language();

    @Override
    public Class<LanguageRecord> getRecordType() {
        return LanguageRecord.class;
    }

    public final TableField<LanguageRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    public final TableField<LanguageRecord, String> LANGUAGE_ID = createField(DSL.name("language_id"), org.jooq.impl.SQLDataType.VARCHAR(100).defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    public final TableField<LanguageRecord, String> LANGUAGE_NAME = createField(DSL.name("language_name"), org.jooq.impl.SQLDataType.CLOB.defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.CLOB)), this, "");

    public Language() {
        this(DSL.name("language"), null);
    }

    public Language(String alias) {
        this(DSL.name(alias), LANGUAGE);
    }

    public Language(Name alias) {
        this(alias, LANGUAGE);
    }

    private Language(Name alias, Table<LanguageRecord> aliased) {
        this(alias, aliased, null);
    }

    private Language(Name alias, Table<LanguageRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Language(Table<O> child, ForeignKey<O, LanguageRecord> key) {
        super(child, key, LANGUAGE);
    }

    @Override
    public Schema getSchema() {
        return SpeechCollectionApp.SPEECH_COLLECTION_APP;
    }

    @Override
    public Identity<LanguageRecord, Long> getIdentity() {
        return Keys.IDENTITY_LANGUAGE;
    }

    @Override
    public UniqueKey<LanguageRecord> getPrimaryKey() {
        return Keys.KEY_LANGUAGE_PRIMARY;
    }

    @Override
    public List<UniqueKey<LanguageRecord>> getKeys() {
        return Arrays.<UniqueKey<LanguageRecord>>asList(Keys.KEY_LANGUAGE_PRIMARY);
    }

    @Override
    public Language as(String alias) {
        return new Language(DSL.name(alias), this);
    }

    @Override
    public Language as(Name alias) {
        return new Language(alias, this);
    }

    @Override
    public Language rename(String name) {
        return new Language(DSL.name(name), null);
    }

    @Override
    public Language rename(Name name) {
        return new Language(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<Long, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
