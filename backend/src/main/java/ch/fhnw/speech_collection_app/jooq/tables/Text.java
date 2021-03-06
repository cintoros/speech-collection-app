/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.speech_collection_app.jooq.tables;


import ch.fhnw.speech_collection_app.jooq.Indexes;
import ch.fhnw.speech_collection_app.jooq.Keys;
import ch.fhnw.speech_collection_app.jooq.SpeechCollectionApp;
import ch.fhnw.speech_collection_app.jooq.tables.records.TextRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row5;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Text extends TableImpl<TextRecord> {

    private static final long serialVersionUID = 306317956;

    public static final Text TEXT = new Text();

    @Override
    public Class<TextRecord> getRecordType() {
        return TextRecord.class;
    }

    public final TableField<TextRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    public final TableField<TextRecord, Long> DIALECT_ID = createField(DSL.name("dialect_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    public final TableField<TextRecord, Long> DATA_ELEMENT_ID = createField(DSL.name("data_element_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    public final TableField<TextRecord, Boolean> IS_SENTENCE_ERROR = createField(DSL.name("is_sentence_error"), org.jooq.impl.SQLDataType.BOOLEAN.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BOOLEAN)), this, "");

    public final TableField<TextRecord, String> TEXT_ = createField(DSL.name("text"), org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    public Text() {
        this(DSL.name("text"), null);
    }

    public Text(String alias) {
        this(DSL.name(alias), TEXT);
    }

    public Text(Name alias) {
        this(alias, TEXT);
    }

    private Text(Name alias, Table<TextRecord> aliased) {
        this(alias, aliased, null);
    }

    private Text(Name alias, Table<TextRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Text(Table<O> child, ForeignKey<O, TextRecord> key) {
        super(child, key, TEXT);
    }

    @Override
    public Schema getSchema() {
        return SpeechCollectionApp.SPEECH_COLLECTION_APP;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.TEXT_DATA_ELEMENT_ID, Indexes.TEXT_DIALECT_ID);
    }

    @Override
    public Identity<TextRecord, Long> getIdentity() {
        return Keys.IDENTITY_TEXT;
    }

    @Override
    public UniqueKey<TextRecord> getPrimaryKey() {
        return Keys.KEY_TEXT_PRIMARY;
    }

    @Override
    public List<UniqueKey<TextRecord>> getKeys() {
        return Arrays.<UniqueKey<TextRecord>>asList(Keys.KEY_TEXT_PRIMARY);
    }

    @Override
    public List<ForeignKey<TextRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TextRecord, ?>>asList(Keys.TEXT_IBFK_1, Keys.TEXT_IBFK_2);
    }

    public Dialect dialect() {
        return new Dialect(this, Keys.TEXT_IBFK_1);
    }

    public DataElement dataElement() {
        return new DataElement(this, Keys.TEXT_IBFK_2);
    }

    @Override
    public Text as(String alias) {
        return new Text(DSL.name(alias), this);
    }

    @Override
    public Text as(Name alias) {
        return new Text(alias, this);
    }

    @Override
    public Text rename(String name) {
        return new Text(DSL.name(name), null);
    }

    @Override
    public Text rename(Name name) {
        return new Text(name, null);
    }

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row5<Long, Long, Long, Boolean, String> fieldsRow() {
        return (Row5) super.fieldsRow();
    }
}
