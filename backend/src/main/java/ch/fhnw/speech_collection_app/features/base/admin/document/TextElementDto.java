package ch.fhnw.speech_collection_app.features.base.admin.document;

public class TextElementDto {
    public final Long id, sourceId, skipped;
    public final Boolean isPrivate, isSentenceError;
    public final String text;

    public TextElementDto(Long id, Long sourceId, Long skipped, Boolean isPrivate, Boolean isSentenceError, String text) {
        this.id = id;
        this.sourceId = sourceId;
        this.skipped = skipped;
        this.isPrivate = isPrivate;
        this.isSentenceError = isSentenceError;
        this.text = text;
    }
}
