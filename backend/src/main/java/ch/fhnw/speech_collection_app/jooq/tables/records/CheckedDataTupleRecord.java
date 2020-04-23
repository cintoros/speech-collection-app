/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.speech_collection_app.jooq.tables.records;


import ch.fhnw.speech_collection_app.jooq.enums.CheckedDataTupleType;
import ch.fhnw.speech_collection_app.jooq.tables.CheckedDataTuple;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CheckedDataTupleRecord extends UpdatableRecordImpl<CheckedDataTupleRecord> implements Record5<Long, Long, Long, CheckedDataTupleType, Timestamp> {

    private static final long serialVersionUID = -467944828;

    public void setId(Long value) {
        set(0, value);
    }

    public Long getId() {
        return (Long) get(0);
    }

    public void setUserId(Long value) {
        set(1, value);
    }

    @NotNull
    public Long getUserId() {
        return (Long) get(1);
    }

    public void setDataTupleId(Long value) {
        set(2, value);
    }

    @NotNull
    public Long getDataTupleId() {
        return (Long) get(2);
    }

    public void setType(CheckedDataTupleType value) {
        set(3, value);
    }

    @NotNull
    public CheckedDataTupleType getType() {
        return (CheckedDataTupleType) get(3);
    }

    public void setCreatedTime(Timestamp value) {
        set(4, value);
    }

    public Timestamp getCreatedTime() {
        return (Timestamp) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row5<Long, Long, Long, CheckedDataTupleType, Timestamp> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<Long, Long, Long, CheckedDataTupleType, Timestamp> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return CheckedDataTuple.CHECKED_DATA_TUPLE.ID;
    }

    @Override
    public Field<Long> field2() {
        return CheckedDataTuple.CHECKED_DATA_TUPLE.USER_ID;
    }

    @Override
    public Field<Long> field3() {
        return CheckedDataTuple.CHECKED_DATA_TUPLE.DATA_TUPLE_ID;
    }

    @Override
    public Field<CheckedDataTupleType> field4() {
        return CheckedDataTuple.CHECKED_DATA_TUPLE.TYPE;
    }

    @Override
    public Field<Timestamp> field5() {
        return CheckedDataTuple.CHECKED_DATA_TUPLE.CREATED_TIME;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public Long component2() {
        return getUserId();
    }

    @Override
    public Long component3() {
        return getDataTupleId();
    }

    @Override
    public CheckedDataTupleType component4() {
        return getType();
    }

    @Override
    public Timestamp component5() {
        return getCreatedTime();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public Long value2() {
        return getUserId();
    }

    @Override
    public Long value3() {
        return getDataTupleId();
    }

    @Override
    public CheckedDataTupleType value4() {
        return getType();
    }

    @Override
    public Timestamp value5() {
        return getCreatedTime();
    }

    @Override
    public CheckedDataTupleRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public CheckedDataTupleRecord value2(Long value) {
        setUserId(value);
        return this;
    }

    @Override
    public CheckedDataTupleRecord value3(Long value) {
        setDataTupleId(value);
        return this;
    }

    @Override
    public CheckedDataTupleRecord value4(CheckedDataTupleType value) {
        setType(value);
        return this;
    }

    @Override
    public CheckedDataTupleRecord value5(Timestamp value) {
        setCreatedTime(value);
        return this;
    }

    @Override
    public CheckedDataTupleRecord values(Long value1, Long value2, Long value3, CheckedDataTupleType value4, Timestamp value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    public CheckedDataTupleRecord() {
        super(CheckedDataTuple.CHECKED_DATA_TUPLE);
    }

    public CheckedDataTupleRecord(Long id, Long userId, Long dataTupleId, CheckedDataTupleType type, Timestamp createdTime) {
        super(CheckedDataTuple.CHECKED_DATA_TUPLE);

        set(0, id);
        set(1, userId);
        set(2, dataTupleId);
        set(3, type);
        set(4, createdTime);
    }
}