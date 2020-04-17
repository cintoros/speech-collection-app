package ch.fhnw.speech_collection_app.features.base.user_group;

public class CheckedOccurrence {
    public final Long id;
    public final Long userId;
    public final CheckedOccurrenceLabel label;
    public final OccurrenceMode mode;

    public CheckedOccurrence(Long id, Long userId, CheckedOccurrenceLabel label, OccurrenceMode mode) {
        this.id = id;
        this.userId = userId;
        this.label = label;
        this.mode = mode;
    }
}