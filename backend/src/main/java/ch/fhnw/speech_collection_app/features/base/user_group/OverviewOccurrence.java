package ch.fhnw.speech_collection_app.features.base.user_group;

public class OverviewOccurrence {
    public final Long id, correct, wrong;
    public final String text;
    public final OccurrenceMode mode;
    public final Long dataElementId_2;

    public OverviewOccurrence(Long id, Long correct, Long wrong, String text, OccurrenceMode mode, Long dataElementId_2) {
        this.id = id;
        this.correct = correct;
        this.wrong = wrong;
        this.text = text;
        this.mode = mode;
        this.dataElementId_2 = dataElementId_2;
    }
}
