package ch.fhnw.speech_collection_app.features.base.user_group;

public class OverviewOccurrence {
    public final Long id, correct, wrong;
    public final String text;
    public final OccurrenceMode mode;

    public OverviewOccurrence(Long id, Long correct, Long wrong, String text, OccurrenceMode mode) {
        this.id = id;
        this.correct = correct;
        this.wrong = wrong;
        this.text = text;
        this.mode = mode;
    }
}