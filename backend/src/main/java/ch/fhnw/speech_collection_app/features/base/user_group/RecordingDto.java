package ch.fhnw.speech_collection_app.features.base.user_group;

import ch.fhnw.speech_collection_app.jooq.enums.AudioNoiseLevel;
import ch.fhnw.speech_collection_app.jooq.enums.AudioQuality;

public class RecordingDto {
    public final AudioQuality audioQuality;
    public final AudioNoiseLevel audioNoiseLevel;
    public final String browserVersion;

    public RecordingDto(AudioQuality audioQuality, AudioNoiseLevel audioNoiseLevel, String browserVersion) {
        this.audioQuality = audioQuality;
        this.audioNoiseLevel = audioNoiseLevel;
        this.browserVersion = browserVersion;
    }
}
