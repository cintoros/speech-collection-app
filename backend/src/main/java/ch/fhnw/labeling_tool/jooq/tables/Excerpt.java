/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.labeling_tool.jooq.tables;


import ch.fhnw.labeling_tool.jooq.Indexes;
import ch.fhnw.labeling_tool.jooq.Keys;
import ch.fhnw.labeling_tool.jooq.LabelingTool;
import ch.fhnw.labeling_tool.jooq.tables.records.ExcerptRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row6;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Excerpt extends TableImpl<ExcerptRecord> {

    private static final long serialVersionUID = -1103626046;

    public static final Excerpt EXCERPT = new Excerpt();

    @Override
    public Class<ExcerptRecord> getRecordType() {
        return ExcerptRecord.class;
    }

    public final TableField<ExcerptRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    public final TableField<ExcerptRecord, Long> ORIGINAL_TEXT_ID = createField(DSL.name("original_text_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    public final TableField<ExcerptRecord, String> EXCERPT_ = createField(DSL.name("excerpt"), org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    public final TableField<ExcerptRecord, Integer> IS_SKIPPED = createField(DSL.name("is_skipped"), org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    public final TableField<ExcerptRecord, Boolean> IS_PRIVATE = createField(DSL.name("is_private"), org.jooq.impl.SQLDataType.BOOLEAN.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BOOLEAN)), this, "");

    public final TableField<ExcerptRecord, Boolean> IS_SENTENCE_ERROR = createField(DSL.name("is_sentence_error"), org.jooq.impl.SQLDataType.BOOLEAN.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BOOLEAN)), this, "");

    public Excerpt() {
        this(DSL.name("excerpt"), null);
    }

    public Excerpt(String alias) {
        this(DSL.name(alias), EXCERPT);
    }

    public Excerpt(Name alias) {
        this(alias, EXCERPT);
    }

    private Excerpt(Name alias, Table<ExcerptRecord> aliased) {
        this(alias, aliased, null);
    }

    private Excerpt(Name alias, Table<ExcerptRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Excerpt(Table<O> child, ForeignKey<O, ExcerptRecord> key) {
        super(child, key, EXCERPT);
    }

    @Override
    public Schema getSchema() {
        return LabelingTool.LABELING_TOOL;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.EXCERPT_ORIGINAL_TEXT_ID, Indexes.EXCERPT_PRIMARY);
    }

    @Override
    public Identity<ExcerptRecord, Long> getIdentity() {
        return Keys.IDENTITY_EXCERPT;
    }

    @Override
    public UniqueKey<ExcerptRecord> getPrimaryKey() {
        return Keys.KEY_EXCERPT_PRIMARY;
    }

    @Override
    public List<UniqueKey<ExcerptRecord>> getKeys() {
        return Arrays.<UniqueKey<ExcerptRecord>>asList(Keys.KEY_EXCERPT_PRIMARY);
    }

    @Override
    public List<ForeignKey<ExcerptRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ExcerptRecord, ?>>asList(Keys.EXCERPT_IBFK_1);
    }

    public OriginalText originalText() {
        return new OriginalText(this, Keys.EXCERPT_IBFK_1);
    }

    @Override
    public Excerpt as(String alias) {
        return new Excerpt(DSL.name(alias), this);
    }

    @Override
    public Excerpt as(Name alias) {
        return new Excerpt(alias, this);
    }

    @Override
    public Excerpt rename(String name) {
        return new Excerpt(DSL.name(name), null);
    }

    @Override
    public Excerpt rename(Name name) {
        return new Excerpt(name, null);
    }

    // -------------------------------------------------------------------------
    // Row6 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row6<Long, Long, String, Integer, Boolean, Boolean> fieldsRow() {
        return (Row6) super.fieldsRow();
    }
}
