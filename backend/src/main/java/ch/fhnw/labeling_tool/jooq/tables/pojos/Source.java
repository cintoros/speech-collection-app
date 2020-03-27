/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.labeling_tool.jooq.tables.pojos;


import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Source implements Serializable {

    private static final long serialVersionUID = 777999557;

    private Long   id;
    private String description;
    private String name;
    private String rawAudioPath;
    private String rawFilePath;
    private String licence;

    public Source() {}

    public Source(Source value) {
        this.id = value.id;
        this.description = value.description;
        this.name = value.name;
        this.rawAudioPath = value.rawAudioPath;
        this.rawFilePath = value.rawFilePath;
        this.licence = value.licence;
    }

    public Source(
        Long   id,
        String description,
        String name,
        String rawAudioPath,
        String rawFilePath,
        String licence
    ) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.rawAudioPath = rawAudioPath;
        this.rawFilePath = rawFilePath;
        this.licence = licence;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    @Size(max = 65535)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull
    @Size(max = 45)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Size(max = 255)
    public String getRawAudioPath() {
        return this.rawAudioPath;
    }

    public void setRawAudioPath(String rawAudioPath) {
        this.rawAudioPath = rawAudioPath;
    }

    @NotNull
    @Size(max = 255)
    public String getRawFilePath() {
        return this.rawFilePath;
    }

    public void setRawFilePath(String rawFilePath) {
        this.rawFilePath = rawFilePath;
    }

    @Size(max = 65535)
    public String getLicence() {
        return this.licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Source (");

        sb.append(id);
        sb.append(", ").append(description);
        sb.append(", ").append(name);
        sb.append(", ").append(rawAudioPath);
        sb.append(", ").append(rawFilePath);
        sb.append(", ").append(licence);

        sb.append(")");
        return sb.toString();
    }
}
