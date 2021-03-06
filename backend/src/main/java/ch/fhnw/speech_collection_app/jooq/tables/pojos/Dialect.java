/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.speech_collection_app.jooq.tables.pojos;


import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Dialect implements Serializable {

    private static final long serialVersionUID = -1505336437;

    private Long   id;
    private String countyId;
    private String countyName;
    private Long   languageId;

    public Dialect() {}

    public Dialect(Dialect value) {
        this.id = value.id;
        this.countyId = value.countyId;
        this.countyName = value.countyName;
        this.languageId = value.languageId;
    }

    public Dialect(
        Long   id,
        String countyId,
        String countyName,
        Long   languageId
    ) {
        this.id = id;
        this.countyId = countyId;
        this.countyName = countyName;
        this.languageId = languageId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Size(max = 100)
    public String getCountyId() {
        return this.countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
    }

    @Size(max = 255)
    public String getCountyName() {
        return this.countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    @NotNull
    public Long getLanguageId() {
        return this.languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Dialect (");

        sb.append(id);
        sb.append(", ").append(countyId);
        sb.append(", ").append(countyName);
        sb.append(", ").append(languageId);

        sb.append(")");
        return sb.toString();
    }
}
