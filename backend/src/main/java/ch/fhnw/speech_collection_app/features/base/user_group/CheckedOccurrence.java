package ch.fhnw.speech_collection_app.features.base.user_group;

public class CheckedOccurrence {
    public final Long id;
    public final CheckedOccurrenceLabel label;

    public CheckedOccurrence(Long id, CheckedOccurrenceLabel label) {
        this.id = id;
        this.label = label;
    }
}
