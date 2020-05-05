/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.speech_collection_app.jooq.tables.pojos;


import ch.fhnw.speech_collection_app.jooq.enums.CheckedRecordingLabel;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.validation.constraints.NotNull;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CheckedRecording implements Serializable {

    private static final long serialVersionUID = -2104978160;

    private Long                  id;
    private Long                  recordingId;
    private Long                  userId;
    private CheckedRecordingLabel label;
    private Timestamp             time;

    public CheckedRecording() {}

    public CheckedRecording(CheckedRecording value) {
        this.id = value.id;
        this.recordingId = value.recordingId;
        this.userId = value.userId;
        this.label = value.label;
        this.time = value.time;
    }

    public CheckedRecording(
        Long                  id,
        Long                  recordingId,
        Long                  userId,
        CheckedRecordingLabel label,
        Timestamp             time
    ) {
        this.id = id;
        this.recordingId = recordingId;
        this.userId = userId;
        this.label = label;
        this.time = time;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public Long getRecordingId() {
        return this.recordingId;
    }

    public void setRecordingId(Long recordingId) {
        this.recordingId = recordingId;
    }

    @NotNull
    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public CheckedRecordingLabel getLabel() {
        return this.label;
    }

    public void setLabel(CheckedRecordingLabel label) {
        this.label = label;
    }

    public Timestamp getTime() {
        return this.time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("CheckedRecording (");

        sb.append(id);
        sb.append(", ").append(recordingId);
        sb.append(", ").append(userId);
        sb.append(", ").append(label);
        sb.append(", ").append(time);

        sb.append(")");
        return sb.toString();
    }
}