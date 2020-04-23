package ch.fhnw.speech_collection_app.features.base.user_group;

import ch.fhnw.speech_collection_app.jooq.enums.AudioNoiseLevel;
import ch.fhnw.speech_collection_app.jooq.enums.AudioQuality;

//TODO maybe add a separate endpoint class etc.
public class RecordingDto {
    private Long excerptId;
    private AudioQuality audioQuality;
    private AudioNoiseLevel audioNoiseLevel;

    public Long getExcerptId() {
        return excerptId;
    }

    public void setExcerptId(Long excerptId) {
        this.excerptId = excerptId;
    }

    public AudioQuality getAudioQuality() {
        return audioQuality;
    }

    public void setAudioQuality(AudioQuality audioQuality) {
        this.audioQuality = audioQuality;
    }

    public AudioNoiseLevel getAudioNoiseLevel() {
        return audioNoiseLevel;
    }

    public void setAudioNoiseLevel(AudioNoiseLevel audioNoiseLevel) {
        this.audioNoiseLevel = audioNoiseLevel;
    }
}