/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.speech_collection_app.jooq.tables.records;


import ch.fhnw.speech_collection_app.jooq.tables.Image;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ImageRecord extends UpdatableRecordImpl<ImageRecord> implements Record4<Long, Long, String, String> {

    private static final long serialVersionUID = 1908873709;

    public void setId(Long value) {
        set(0, value);
    }

    public Long getId() {
        return (Long) get(0);
    }

    public void setDataElementId(Long value) {
        set(1, value);
    }

    @NotNull
    public Long getDataElementId() {
        return (Long) get(1);
    }

    public void setPath(String value) {
        set(2, value);
    }

    @NotNull
    @Size(max = 65535)
    public String getPath() {
        return (String) get(2);
    }

    public void setLicence(String value) {
        set(3, value);
    }

    @Size(max = 65535)
    public String getLicence() {
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
    public Row4<Long, Long, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<Long, Long, String, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return Image.IMAGE.ID;
    }

    @Override
    public Field<Long> field2() {
        return Image.IMAGE.DATA_ELEMENT_ID;
    }

    @Override
    public Field<String> field3() {
        return Image.IMAGE.PATH;
    }

    @Override
    public Field<String> field4() {
        return Image.IMAGE.LICENCE;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public Long component2() {
        return getDataElementId();
    }

    @Override
    public String component3() {
        return getPath();
    }

    @Override
    public String component4() {
        return getLicence();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public Long value2() {
        return getDataElementId();
    }

    @Override
    public String value3() {
        return getPath();
    }

    @Override
    public String value4() {
        return getLicence();
    }

    @Override
    public ImageRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public ImageRecord value2(Long value) {
        setDataElementId(value);
        return this;
    }

    @Override
    public ImageRecord value3(String value) {
        setPath(value);
        return this;
    }

    @Override
    public ImageRecord value4(String value) {
        setLicence(value);
        return this;
    }

    @Override
    public ImageRecord values(Long value1, Long value2, String value3, String value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    public ImageRecord() {
        super(Image.IMAGE);
    }

    public ImageRecord(Long id, Long dataElementId, String path, String licence) {
        super(Image.IMAGE);

        set(0, id);
        set(1, dataElementId);
        set(2, path);
        set(3, licence);
    }
}
