/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.speech_collection_app.jooq.tables.pojos;


import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Text implements Serializable {

    private static final long serialVersionUID = 1266994450;

    private Long    id;
    private Long    dialectId;
    private Long    dataElementId;
    private Boolean isSentenceError;
    private String  text;

    public Text() {}

    public Text(Text value) {
        this.id = value.id;
        this.dialectId = value.dialectId;
        this.dataElementId = value.dataElementId;
        this.isSentenceError = value.isSentenceError;
        this.text = value.text;
    }

    public Text(
        Long    id,
        Long    dialectId,
        Long    dataElementId,
        Boolean isSentenceError,
        String  text
    ) {
        this.id = id;
        this.dialectId = dialectId;
        this.dataElementId = dataElementId;
        this.isSentenceError = isSentenceError;
        this.text = text;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public Long getDialectId() {
        return this.dialectId;
    }

    public void setDialectId(Long dialectId) {
        this.dialectId = dialectId;
    }

    @NotNull
    public Long getDataElementId() {
        return this.dataElementId;
    }

    public void setDataElementId(Long dataElementId) {
        this.dataElementId = dataElementId;
    }

    public Boolean getIsSentenceError() {
        return this.isSentenceError;
    }

    public void setIsSentenceError(Boolean isSentenceError) {
        this.isSentenceError = isSentenceError;
    }

    @NotNull
    @Size(max = 65535)
    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Text (");

        sb.append(id);
        sb.append(", ").append(dialectId);
        sb.append(", ").append(dataElementId);
        sb.append(", ").append(isSentenceError);
        sb.append(", ").append(text);

        sb.append(")");
        return sb.toString();
    }
}