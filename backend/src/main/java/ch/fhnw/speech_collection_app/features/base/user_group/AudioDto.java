package ch.fhnw.speech_collection_app.features.base.user_group;

import ch.fhnw.speech_collection_app.jooq.enums.AudioNoiseLevel;
import ch.fhnw.speech_collection_app.jooq.enums.AudioQuality;

import java.util.Objects;

public class AudioDto {
    private Long id;
    private Long dialectId;
    private Long dataElementId;
    private String path;
    private AudioQuality quality;
    private AudioNoiseLevel noiseLevel;
    private String browserVersion;
    private Float audioStart;
    private Float audioEnd;

    public AudioDto() {
    }

    public AudioDto(Long id, Long dialectId, Long dataElementId, String path,
                    AudioQuality quality, AudioNoiseLevel noiseLevel,
                    String browserVersion, Float audioStart, Float audioEnd) {
        this.id = id;
        this.dialectId = dialectId;
        this.dataElementId = dataElementId;
        this.path = path;
        this.quality = quality;
        this.noiseLevel = noiseLevel;
        this.browserVersion = browserVersion;
        this.audioStart = audioStart;
        this.audioEnd = audioEnd;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDialectId() {
        return this.dialectId;
    }

    public void setDialectId(Long dialectId) {
        this.dialectId = dialectId;
    }

    public Long getDataElementId() {
        return this.dataElementId;
    }

    public void setDataElementId(Long dataElementId) {
        this.dataElementId = dataElementId;
    }

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

    public String getBrowserVersion() {
        return this.browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public Float getAudioStart() {
        return this.audioStart;
    }

    public void setAudioStart(Float audioStart) {
        this.audioStart = audioStart;
    }

    public Float getAudioEnd() {
        return this.audioEnd;
    }

    public void setAudioEnd(Float audioEnd) {
        this.audioEnd = audioEnd;
    }

    public AudioDto id(Long id) {
        this.id = id;
        return this;
    }

    public AudioDto dialectId(Long dialectId) {
        this.dialectId = dialectId;
        return this;
    }

    public AudioDto dataElementId(Long dataElementId) {
        this.dataElementId = dataElementId;
        return this;
    }

    public AudioDto path(String path) {
        this.path = path;
        return this;
    }

    public AudioDto quality(AudioQuality quality) {
        this.quality = quality;
        return this;
    }

    public AudioDto noiseLevel(AudioNoiseLevel noiseLevel) {
        this.noiseLevel = noiseLevel;
        return this;
    }

    public AudioDto browserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
        return this;
    }

    public AudioDto audioStart(Float audioStart) {
        this.audioStart = audioStart;
        return this;
    }

    public AudioDto audioEnd(Float audioEnd) {
        this.audioEnd = audioEnd;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AudioDto)) {
            return false;
        }
        AudioDto audioDto = (AudioDto) o;
        return Objects.equals(id, audioDto.id) &&
                Objects.equals(dialectId, audioDto.dialectId) &&
                Objects.equals(dataElementId, audioDto.dataElementId) &&
                Objects.equals(path, audioDto.path) &&
                Objects.equals(quality, audioDto.quality) &&
                Objects.equals(noiseLevel, audioDto.noiseLevel) &&
                Objects.equals(browserVersion, audioDto.browserVersion) &&
                Objects.equals(audioStart, audioDto.audioStart) &&
                Objects.equals(audioEnd, audioDto.audioEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dialectId, dataElementId, path, quality, noiseLevel,
                browserVersion, audioStart, audioEnd);
    }

    @Override
    public String toString() {
        return "{"
                + " id='" + getId() + "'"
                + ", dialectId='" + getDialectId() + "'"
                + ", dataElementId='" + getDataElementId() + "'"
                + ", path='" + getPath() + "'"
                + ", quality='" + getQuality() + "'"
                + ", noiseLevel='" + getNoiseLevel() + "'"
                + ", browserVersion='" + getBrowserVersion() + "'"
                + ", audioStart='" + getAudioStart() + "'"
                + ", audioEnd='" + getAudioEnd() + "'"
                + "}";
    }
}
