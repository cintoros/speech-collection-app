package ch.fhnw.speech_collection_app.features.base.admin;

public class TextAudioDto {
    public final Long id;
    public final Double audioStart, audioEnd;

    public TextAudioDto(Long id, Double audioStart, Double audioEnd) {
        this.id = id;
        this.audioStart = audioStart;
        this.audioEnd = audioEnd;
    }
}
