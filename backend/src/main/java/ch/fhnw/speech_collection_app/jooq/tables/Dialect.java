/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.speech_collection_app.jooq.tables;


import ch.fhnw.speech_collection_app.jooq.Indexes;
import ch.fhnw.speech_collection_app.jooq.Keys;
import ch.fhnw.speech_collection_app.jooq.SpeechCollectionApp;
import ch.fhnw.speech_collection_app.jooq.tables.records.DialectRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row4;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Dialect extends TableImpl<DialectRecord> {

    private static final long serialVersionUID = 1846088294;

    public static final Dialect DIALECT = new Dialect();

    @Override
    public Class<DialectRecord> getRecordType() {
        return DialectRecord.class;
    }

    public final TableField<DialectRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    public final TableField<DialectRecord, String> COUNTY_ID = createField(DSL.name("county_id"), org.jooq.impl.SQLDataType.VARCHAR(100).defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    public final TableField<DialectRecord, String> COUNTY_NAME = createField(DSL.name("county_name"), org.jooq.impl.SQLDataType.CLOB.defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.CLOB)), this, "");

    public final TableField<DialectRecord, Long> LANGUAGE_ID = createField(DSL.name("language_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    public Dialect() {
        this(DSL.name("dialect"), null);
    }

    public Dialect(String alias) {
        this(DSL.name(alias), DIALECT);
    }

    public Dialect(Name alias) {
        this(alias, DIALECT);
    }

    private Dialect(Name alias, Table<DialectRecord> aliased) {
        this(alias, aliased, null);
    }

    private Dialect(Name alias, Table<DialectRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Dialect(Table<O> child, ForeignKey<O, DialectRecord> key) {
        super(child, key, DIALECT);
    }

    @Override
    public Schema getSchema() {
        return SpeechCollectionApp.SPEECH_COLLECTION_APP;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.DIALECT_LANGUAGE_ID);
    }

    @Override
    public Identity<DialectRecord, Long> getIdentity() {
        return Keys.IDENTITY_DIALECT;
    }

    @Override
    public UniqueKey<DialectRecord> getPrimaryKey() {
        return Keys.KEY_DIALECT_PRIMARY;
    }

    @Override
    public List<UniqueKey<DialectRecord>> getKeys() {
        return Arrays.<UniqueKey<DialectRecord>>asList(Keys.KEY_DIALECT_PRIMARY);
    }

    @Override
    public List<ForeignKey<DialectRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<DialectRecord, ?>>asList(Keys.DIALECT_IBFK_1);
    }

    public Language language() {
        return new Language(this, Keys.DIALECT_IBFK_1);
    }

    @Override
    public Dialect as(String alias) {
        return new Dialect(DSL.name(alias), this);
    }

    @Override
    public Dialect as(Name alias) {
        return new Dialect(alias, this);
    }

    @Override
    public Dialect rename(String name) {
        return new Dialect(DSL.name(name), null);
    }

    @Override
    public Dialect rename(Name name) {
        return new Dialect(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Long, String, String, Long> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
