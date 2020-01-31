/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.labeling_tool.jooq.tables.records;


import ch.fhnw.labeling_tool.jooq.tables.UserAndTextAudio;

import java.sql.Timestamp;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserAndTextAudioRecord extends UpdatableRecordImpl<UserAndTextAudioRecord> implements Record4<Long, Long, Integer, Timestamp> {

    private static final long serialVersionUID = -1821916051;

    public void setId(Long value) {
        set(0, value);
    }

    public Long getId() {
        return (Long) get(0);
    }

    public void setUserId(Long value) {
        set(1, value);
    }

    public Long getUserId() {
        return (Long) get(1);
    }

    public void setTextAudioId(Integer value) {
        set(2, value);
    }

    public Integer getTextAudioId() {
        return (Integer) get(2);
    }

    public void setTime(Timestamp value) {
        set(3, value);
    }

    public Timestamp getTime() {
        return (Timestamp) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<Long, Long, Integer, Timestamp> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<Long, Long, Integer, Timestamp> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return UserAndTextAudio.USER_AND_TEXT_AUDIO.ID;
    }

    @Override
    public Field<Long> field2() {
        return UserAndTextAudio.USER_AND_TEXT_AUDIO.USER_ID;
    }

    @Override
    public Field<Integer> field3() {
        return UserAndTextAudio.USER_AND_TEXT_AUDIO.TEXT_AUDIO_ID;
    }

    @Override
    public Field<Timestamp> field4() {
        return UserAndTextAudio.USER_AND_TEXT_AUDIO.TIME;
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
    public Integer component3() {
        return getTextAudioId();
    }

    @Override
    public Timestamp component4() {
        return getTime();
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
    public Integer value3() {
        return getTextAudioId();
    }

    @Override
    public Timestamp value4() {
        return getTime();
    }

    @Override
    public UserAndTextAudioRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public UserAndTextAudioRecord value2(Long value) {
        setUserId(value);
        return this;
    }

    @Override
    public UserAndTextAudioRecord value3(Integer value) {
        setTextAudioId(value);
        return this;
    }

    @Override
    public UserAndTextAudioRecord value4(Timestamp value) {
        setTime(value);
        return this;
    }

    @Override
    public UserAndTextAudioRecord values(Long value1, Long value2, Integer value3, Timestamp value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    public UserAndTextAudioRecord() {
        super(UserAndTextAudio.USER_AND_TEXT_AUDIO);
    }

    public UserAndTextAudioRecord(Long id, Long userId, Integer textAudioId, Timestamp time) {
        super(UserAndTextAudio.USER_AND_TEXT_AUDIO);

        set(0, id);
        set(1, userId);
        set(2, textAudioId);
        set(3, time);
    }
}
