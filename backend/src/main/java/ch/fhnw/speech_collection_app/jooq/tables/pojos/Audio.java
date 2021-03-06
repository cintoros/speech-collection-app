/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.speech_collection_app.jooq.tables.pojos;


import ch.fhnw.speech_collection_app.jooq.enums.AudioNoiseLevel;
import ch.fhnw.speech_collection_app.jooq.enums.AudioQuality;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Audio implements Serializable {

    private static final long serialVersionUID = -1248698259;

    private Long            id;
    private Long            dialectId;
    private Long            dataElementId;
    private String          path;
    private AudioQuality    quality;
    private AudioNoiseLevel noiseLevel;
    private String          browserVersion;
    private Double          audioStart;
    private Double          audioEnd;
    private Double          duration;

    public Audio() {}

    public Audio(Audio value) {
        this.id = value.id;
        this.dialectId = value.dialectId;
        this.dataElementId = value.dataElementId;
        this.path = value.path;
        this.quality = value.quality;
        this.noiseLevel = value.noiseLevel;
        this.browserVersion = value.browserVersion;
        this.audioStart = value.audioStart;
        this.audioEnd = value.audioEnd;
        this.duration = value.duration;
    }

    public Audio(
        Long            id,
        Long            dialectId,
        Long            dataElementId,
        String          path,
        AudioQuality    quality,
        AudioNoiseLevel noiseLevel,
        String          browserVersion,
        Double          audioStart,
        Double          audioEnd,
        Double          duration
    ) {
        this.id = id;
        this.dialectId = dialectId;
        this.dataElementId = dataElementId;
        this.path = path;
        this.quality = quality;
        this.noiseLevel = noiseLevel;
        this.browserVersion = browserVersion;
        this.audioStart = audioStart;
        this.audioEnd = audioEnd;
        this.duration = duration;
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

    @NotNull
    @Size(max = 65535)
    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public AudioQuality getQuality() {
        return this.quality;
    }

    public void setQuality(AudioQuality quality) {
        this.quality = quality;
    }

    public AudioNoiseLevel getNoiseLevel() {
        return this.noiseLevel;
    }

    public void setNoiseLevel(AudioNoiseLevel noiseLevel) {
        this.noiseLevel = noiseLevel;
    }

    @Size(max = 65535)
    public String getBrowserVersion() {
        return this.browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public Double getAudioStart() {
        return this.audioStart;
    }

    public void setAudioStart(Double audioStart) {
        this.audioStart = audioStart;
    }

    public Double getAudioEnd() {
        return this.audioEnd;
    }

    public void setAudioEnd(Double audioEnd) {
        this.audioEnd = audioEnd;
    }

    public Double getDuration() {
        return this.duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Audio (");

        sb.append(id);
        sb.append(", ").append(dialectId);
        sb.append(", ").append(dataElementId);
        sb.append(", ").append(path);
        sb.append(", ").append(quality);
        sb.append(", ").append(noiseLevel);
        sb.append(", ").append(browserVersion);
        sb.append(", ").append(audioStart);
        sb.append(", ").append(audioEnd);
        sb.append(", ").append(duration);

        sb.append(")");
        return sb.toString();
    }
}
