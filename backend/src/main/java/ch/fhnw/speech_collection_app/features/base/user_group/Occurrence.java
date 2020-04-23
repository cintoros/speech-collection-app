package ch.fhnw.speech_collection_app.features.base.user_group;

public class Occurrence {
    public final Long id, dataElementId2;
    public final String text;
    public final OccurrenceMode mode;

    public Occurrence(Long id, Long dataElementId2, String text, OccurrenceMode mode) {
        this.id = id;
        this.dataElementId2 = dataElementId2;
        this.text = text;
        this.mode = mode;
    }
}
