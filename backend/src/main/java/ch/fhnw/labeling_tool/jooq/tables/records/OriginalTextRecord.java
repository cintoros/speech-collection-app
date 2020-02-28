/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.labeling_tool.jooq.tables.records;


import ch.fhnw.labeling_tool.jooq.tables.OriginalText;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class OriginalTextRecord extends UpdatableRecordImpl<OriginalTextRecord> implements Record5<Long, Long, Long, Long, Timestamp> {

    private static final long serialVersionUID = -479379311;

    public void setId(Long value) {
        set(0, value);
    }

    public Long getId() {
        return (Long) get(0);
    }

    public void setUserGroupId(Long value) {
        set(1, value);
    }

    @NotNull
    public Long getUserGroupId() {
        return (Long) get(1);
    }

    public void setDomainId(Long value) {
        set(2, value);
    }

    @NotNull
    public Long getDomainId() {
        return (Long) get(2);
    }

    public void setUserId(Long value) {
        set(3, value);
    }

    public Long getUserId() {
        return (Long) get(3);
    }

    public void setTime(Timestamp value) {
        set(4, value);
    }

    public Timestamp getTime() {
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
    public Row5<Long, Long, Long, Long, Timestamp> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<Long, Long, Long, Long, Timestamp> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return OriginalText.ORIGINAL_TEXT.ID;
    }

    @Override
    public Field<Long> field2() {
        return OriginalText.ORIGINAL_TEXT.USER_GROUP_ID;
    }

    @Override
    public Field<Long> field3() {
        return OriginalText.ORIGINAL_TEXT.DOMAIN_ID;
    }

    @Override
    public Field<Long> field4() {
        return OriginalText.ORIGINAL_TEXT.USER_ID;
    }

    @Override
    public Field<Timestamp> field5() {
        return OriginalText.ORIGINAL_TEXT.TIME;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public Long component2() {
        return getUserGroupId();
    }

    @Override
    public Long component3() {
        return getDomainId();
    }

    @Override
    public Long component4() {
        return getUserId();
    }

    @Override
    public Timestamp component5() {
        return getTime();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public Long value2() {
        return getUserGroupId();
    }

    @Override
    public Long value3() {
        return getDomainId();
    }

    @Override
    public Long value4() {
        return getUserId();
    }

    @Override
    public Timestamp value5() {
        return getTime();
    }

    @Override
    public OriginalTextRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public OriginalTextRecord value2(Long value) {
        setUserGroupId(value);
        return this;
    }

    @Override
    public OriginalTextRecord value3(Long value) {
        setDomainId(value);
        return this;
    }

    @Override
    public OriginalTextRecord value4(Long value) {
        setUserId(value);
        return this;
    }

    @Override
    public OriginalTextRecord value5(Timestamp value) {
        setTime(value);
        return this;
    }

    @Override
    public OriginalTextRecord values(Long value1, Long value2, Long value3, Long value4, Timestamp value5) {
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

    public OriginalTextRecord() {
        super(OriginalText.ORIGINAL_TEXT);
    }

    public OriginalTextRecord(Long id, Long userGroupId, Long domainId, Long userId, Timestamp time) {
        super(OriginalText.ORIGINAL_TEXT);

        set(0, id);
        set(1, userGroupId);
        set(2, domainId);
        set(3, userId);
        set(4, time);
    }
}
