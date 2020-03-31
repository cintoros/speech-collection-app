/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.labeling_tool.jooq.tables.pojos;


import ch.fhnw.labeling_tool.jooq.enums.RecordingLabel;
import ch.fhnw.labeling_tool.jooq.enums.RecordingNoiseLevel;
import ch.fhnw.labeling_tool.jooq.enums.RecordingQuality;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.validation.constraints.NotNull;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Recording implements Serializable {

    private static final long serialVersionUID = -647683933;

    private Long                id;
    private Long                excerptId;
    private Long                userId;
    private Timestamp           time;
    private RecordingLabel      label;
    private Long                wrong;
    private Long                correct;
    private Timestamp           deleted;
    private RecordingQuality    quality;
    private RecordingNoiseLevel noiseLevel;

    public Recording() {}

    public Recording(Recording value) {
        this.id = value.id;
        this.excerptId = value.excerptId;
        this.userId = value.userId;
        this.time = value.time;
        this.label = value.label;
        this.wrong = value.wrong;
        this.correct = value.correct;
        this.deleted = value.deleted;
        this.quality = value.quality;
        this.noiseLevel = value.noiseLevel;
    }

    public Recording(
        Long                id,
        Long                excerptId,
        Long                userId,
        Timestamp           time,
        RecordingLabel      label,
        Long                wrong,
        Long                correct,
        Timestamp           deleted,
        RecordingQuality    quality,
        RecordingNoiseLevel noiseLevel
    ) {
        this.id = id;
        this.excerptId = excerptId;
        this.userId = userId;
        this.time = time;
        this.label = label;
        this.wrong = wrong;
        this.correct = correct;
        this.deleted = deleted;
        this.quality = quality;
        this.noiseLevel = noiseLevel;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public Long getExcerptId() {
        return this.excerptId;
    }

    public void setExcerptId(Long excerptId) {
        this.excerptId = excerptId;
    }

    @NotNull
    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Timestamp getTime() {
        return this.time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public RecordingLabel getLabel() {
        return this.label;
    }

    public void setLabel(RecordingLabel label) {
        this.label = label;
    }

    public Long getWrong() {
        return this.wrong;
    }

    public void setWrong(Long wrong) {
        this.wrong = wrong;
    }

    public Long getCorrect() {
        return this.correct;
    }

    public void setCorrect(Long correct) {
        this.correct = correct;
    }

    public Timestamp getDeleted() {
        return this.deleted;
    }

    public void setDeleted(Timestamp deleted) {
        this.deleted = deleted;
    }

    public RecordingQuality getQuality() {
        return this.quality;
    }

    public void setQuality(RecordingQuality quality) {
        this.quality = quality;
    }

    public RecordingNoiseLevel getNoiseLevel() {
        return this.noiseLevel;
    }

    public void setNoiseLevel(RecordingNoiseLevel noiseLevel) {
        this.noiseLevel = noiseLevel;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Recording (");

        sb.append(id);
        sb.append(", ").append(excerptId);
        sb.append(", ").append(userId);
        sb.append(", ").append(time);
        sb.append(", ").append(label);
        sb.append(", ").append(wrong);
        sb.append(", ").append(correct);
        sb.append(", ").append(deleted);
        sb.append(", ").append(quality);
        sb.append(", ").append(noiseLevel);

        sb.append(")");
        return sb.toString();
    }
}
