package ch.fhnw.speech_collection_app.features.base.user_group;

public class Occurrence {
    public final Long id, dataElementId_1, dataElementId_2;
    public final String text;
    public final OccurrenceMode mode;

    public Occurrence(Long id, Long dataElementId_1, Long dataElementId_2, String text, OccurrenceMode mode) {
        this.id = id;
        this.dataElementId_1 = dataElementId_1;
        this.dataElementId_2 = dataElementId_2;
        this.text = text;
        this.mode = mode;
    }
}
