/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.speech_collection_app.jooq.tables;


import ch.fhnw.speech_collection_app.jooq.Indexes;
import ch.fhnw.speech_collection_app.jooq.Keys;
import ch.fhnw.speech_collection_app.jooq.SpeechCollectionApp;
import ch.fhnw.speech_collection_app.jooq.enums.CheckedDataTupleType;
import ch.fhnw.speech_collection_app.jooq.tables.records.CheckedDataTupleRecord;

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
public class CheckedDataTuple extends TableImpl<CheckedDataTupleRecord> {

    private static final long serialVersionUID = -1559909895;

    public static final CheckedDataTuple CHECKED_DATA_TUPLE = new CheckedDataTuple();

    @Override
    public Class<CheckedDataTupleRecord> getRecordType() {
        return CheckedDataTupleRecord.class;
    }

    public final TableField<CheckedDataTupleRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    public final TableField<CheckedDataTupleRecord, Long> USER_ID = createField(DSL.name("user_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    public final TableField<CheckedDataTupleRecord, Long> DATA_TUPLE_ID = createField(DSL.name("data_tuple_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    public final TableField<CheckedDataTupleRecord, CheckedDataTupleType> TYPE = createField(DSL.name("type"), org.jooq.impl.SQLDataType.VARCHAR(7).nullable(false).asEnumDataType(ch.fhnw.speech_collection_app.jooq.enums.CheckedDataTupleType.class), this, "");

    public final TableField<CheckedDataTupleRecord, Timestamp> CREATED_TIME = createField(DSL.name("created_time"), org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("current_timestamp()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    public CheckedDataTuple() {
        this(DSL.name("checked_data_tuple"), null);
    }

    public CheckedDataTuple(String alias) {
        this(DSL.name(alias), CHECKED_DATA_TUPLE);
    }

    public CheckedDataTuple(Name alias) {
        this(alias, CHECKED_DATA_TUPLE);
    }

    private CheckedDataTuple(Name alias, Table<CheckedDataTupleRecord> aliased) {
        this(alias, aliased, null);
    }

    private CheckedDataTuple(Name alias, Table<CheckedDataTupleRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("this table is used to save the label of a data_tuple by a user so we can revert it in case a user produces nonsense"));
    }

    public <O extends Record> CheckedDataTuple(Table<O> child, ForeignKey<O, CheckedDataTupleRecord> key) {
        super(child, key, CHECKED_DATA_TUPLE);
    }

    @Override
    public Schema getSchema() {
        return SpeechCollectionApp.SPEECH_COLLECTION_APP;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.CHECKED_DATA_TUPLE_DATA_TUPLE_ID, Indexes.CHECKED_DATA_TUPLE_PRIMARY, Indexes.CHECKED_DATA_TUPLE_USER_ID);
    }

    @Override
    public Identity<CheckedDataTupleRecord, Long> getIdentity() {
        return Keys.IDENTITY_CHECKED_DATA_TUPLE;
    }

    @Override
    public UniqueKey<CheckedDataTupleRecord> getPrimaryKey() {
        return Keys.KEY_CHECKED_DATA_TUPLE_PRIMARY;
    }

    @Override
    public List<UniqueKey<CheckedDataTupleRecord>> getKeys() {
        return Arrays.<UniqueKey<CheckedDataTupleRecord>>asList(Keys.KEY_CHECKED_DATA_TUPLE_PRIMARY);
    }

    @Override
    public List<ForeignKey<CheckedDataTupleRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<CheckedDataTupleRecord, ?>>asList(Keys.CHECKED_DATA_TUPLE_IBFK_1, Keys.CHECKED_DATA_TUPLE_IBFK_2);
    }

    public User user() {
        return new User(this, Keys.CHECKED_DATA_TUPLE_IBFK_1);
    }

    public DataTuple dataTuple() {
        return new DataTuple(this, Keys.CHECKED_DATA_TUPLE_IBFK_2);
    }

    @Override
    public CheckedDataTuple as(String alias) {
        return new CheckedDataTuple(DSL.name(alias), this);
    }

    @Override
    public CheckedDataTuple as(Name alias) {
        return new CheckedDataTuple(alias, this);
    }

    @Override
    public CheckedDataTuple rename(String name) {
        return new CheckedDataTuple(DSL.name(name), null);
    }

    @Override
    public CheckedDataTuple rename(Name name) {
        return new CheckedDataTuple(name, null);
    }

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row5<Long, Long, Long, CheckedDataTupleType, Timestamp> fieldsRow() {
        return (Row5) super.fieldsRow();
    }
}
