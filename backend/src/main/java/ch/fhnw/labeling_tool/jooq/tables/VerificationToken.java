/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.labeling_tool.jooq.tables;


import ch.fhnw.labeling_tool.jooq.Indexes;
import ch.fhnw.labeling_tool.jooq.Keys;
import ch.fhnw.labeling_tool.jooq.LabelingTool;
import ch.fhnw.labeling_tool.jooq.enums.VerificationTokenTokenType;
import ch.fhnw.labeling_tool.jooq.tables.records.VerificationTokenRecord;

import java.sql.Timestamp;
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
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class VerificationToken extends TableImpl<VerificationTokenRecord> {

    private static final long serialVersionUID = -1269835745;

    public static final VerificationToken VERIFICATION_TOKEN = new VerificationToken();

    @Override
    public Class<VerificationTokenRecord> getRecordType() {
        return VerificationTokenRecord.class;
    }

    public final TableField<VerificationTokenRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    public final TableField<VerificationTokenRecord, String> TOKEN = createField(DSL.name("token"), org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    public final TableField<VerificationTokenRecord, VerificationTokenTokenType> TOKEN_TYPE = createField(DSL.name("token_type"), org.jooq.impl.SQLDataType.VARCHAR(8).nullable(false).asEnumDataType(ch.fhnw.labeling_tool.jooq.enums.VerificationTokenTokenType.class), this, "");

    public final TableField<VerificationTokenRecord, Timestamp> CREATED_TIME = createField(DSL.name("created_time"), org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("current_timestamp()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    public final TableField<VerificationTokenRecord, Long> USER_ID = createField(DSL.name("user_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    public VerificationToken() {
        this(DSL.name("verification_token"), null);
    }

    public VerificationToken(String alias) {
        this(DSL.name(alias), VERIFICATION_TOKEN);
    }

    public VerificationToken(Name alias) {
        this(alias, VERIFICATION_TOKEN);
    }

    private VerificationToken(Name alias, Table<VerificationTokenRecord> aliased) {
        this(alias, aliased, null);
    }

    private VerificationToken(Name alias, Table<VerificationTokenRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> VerificationToken(Table<O> child, ForeignKey<O, VerificationTokenRecord> key) {
        super(child, key, VERIFICATION_TOKEN);
    }

    @Override
    public Schema getSchema() {
        return LabelingTool.LABELING_TOOL;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.VERIFICATION_TOKEN_PRIMARY, Indexes.VERIFICATION_TOKEN_USER_ID);
    }

    @Override
    public Identity<VerificationTokenRecord, Long> getIdentity() {
        return Keys.IDENTITY_VERIFICATION_TOKEN;
    }

    @Override
    public UniqueKey<VerificationTokenRecord> getPrimaryKey() {
        return Keys.KEY_VERIFICATION_TOKEN_PRIMARY;
    }

    @Override
    public List<UniqueKey<VerificationTokenRecord>> getKeys() {
        return Arrays.<UniqueKey<VerificationTokenRecord>>asList(Keys.KEY_VERIFICATION_TOKEN_PRIMARY);
    }

    @Override
    public List<ForeignKey<VerificationTokenRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<VerificationTokenRecord, ?>>asList(Keys.VERIFICATION_TOKEN_IBFK_1);
    }

    public User user() {
        return new User(this, Keys.VERIFICATION_TOKEN_IBFK_1);
    }

    @Override
    public VerificationToken as(String alias) {
        return new VerificationToken(DSL.name(alias), this);
    }

    @Override
    public VerificationToken as(Name alias) {
        return new VerificationToken(alias, this);
    }

    @Override
    public VerificationToken rename(String name) {
        return new VerificationToken(DSL.name(name), null);
    }

    @Override
    public VerificationToken rename(Name name) {
        return new VerificationToken(name, null);
    }

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row5<Long, String, VerificationTokenTokenType, Timestamp, Long> fieldsRow() {
        return (Row5) super.fieldsRow();
    }
}