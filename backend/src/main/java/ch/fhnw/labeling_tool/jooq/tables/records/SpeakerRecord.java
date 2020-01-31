/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.labeling_tool.jooq.tables.records;


import ch.fhnw.labeling_tool.jooq.tables.Speaker;

import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SpeakerRecord extends UpdatableRecordImpl<SpeakerRecord> implements Record4<Long, String, String, String> {

    private static final long serialVersionUID = 2078361944;

    public void setId(Long value) {
        set(0, value);
    }

    public Long getId() {
        return (Long) get(0);
    }

    public void setSpeakerId(String value) {
        set(1, value);
    }

    @Size(max = 45)
    public String getSpeakerId() {
        return (String) get(1);
    }

    public void setLanguageUsed(String value) {
        set(2, value);
    }

    @Size(max = 45)
    public String getLanguageUsed() {
        return (String) get(2);
    }

    public void setDialect(String value) {
        set(3, value);
    }

    @Size(max = 45)
    public String getDialect() {
        return (String) get(3);
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
    public Row4<Long, String, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<Long, String, String, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return Speaker.SPEAKER.ID;
    }

    @Override
    public Field<String> field2() {
        return Speaker.SPEAKER.SPEAKER_ID;
    }

    @Override
    public Field<String> field3() {
        return Speaker.SPEAKER.LANGUAGE_USED;
    }

    @Override
    public Field<String> field4() {
        return Speaker.SPEAKER.DIALECT;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getSpeakerId();
    }

    @Override
    public String component3() {
        return getLanguageUsed();
    }

    @Override
    public String component4() {
        return getDialect();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getSpeakerId();
    }

    @Override
    public String value3() {
        return getLanguageUsed();
    }

    @Override
    public String value4() {
        return getDialect();
    }

    @Override
    public SpeakerRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public SpeakerRecord value2(String value) {
        setSpeakerId(value);
        return this;
    }

    @Override
    public SpeakerRecord value3(String value) {
        setLanguageUsed(value);
        return this;
    }

    @Override
    public SpeakerRecord value4(String value) {
        setDialect(value);
        return this;
    }

    @Override
    public SpeakerRecord values(Long value1, String value2, String value3, String value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    public SpeakerRecord() {
        super(Speaker.SPEAKER);
    }

    public SpeakerRecord(Long id, String speakerId, String languageUsed, String dialect) {
        super(Speaker.SPEAKER);

        set(0, id);
        set(1, speakerId);
        set(2, languageUsed);
        set(3, dialect);
    }
}
