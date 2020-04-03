package ch.fhnw.speech_collection_app.features.base.user_group;

public class Occurrence {
    public final Long id;
    public final String text;
    public final OccurrenceMode mode;

    public Occurrence(Long id, String text, OccurrenceMode mode) {
        this.id = id;
        this.text = text;
        this.mode = mode;
    }
}
