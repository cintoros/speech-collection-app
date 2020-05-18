package ch.fhnw.speech_collection_app.features.base.user_group;

import java.util.Objects;

import ch.fhnw.speech_collection_app.jooq.enums.DataTupleType;

public class TupleDto {
    private Long id;
    private Long data_element_id_1;
    private Long data_element_id_2;
    private DataTupleType type;
    private Boolean finished;
    private Long correct;
    private Long wrong;
    private Long skipped;

    public TupleDto() {
    }

    public TupleDto(Long id, Long data_element_id_1, Long data_element_id_2, DataTupleType type, Boolean finished,
            Long correct, Long wrong, Long skipped) {
        this.id = id;
        this.data_element_id_1 = data_element_id_1;
        this.data_element_id_2 = data_element_id_2;
        this.type = type;
        this.finished = finished;
        this.correct = correct;
        this.wrong = wrong;
        this.skipped = skipped;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getData_element_id_1() {
        return this.data_element_id_1;
    }

    public void setData_element_id_1(Long data_element_id_1) {
        this.data_element_id_1 = data_element_id_1;
    }

    public Long getData_element_id_2() {
        return this.data_element_id_2;
    }

    public void setData_element_id_2(Long data_element_id_2) {
        this.data_element_id_2 = data_element_id_2;
    }

    public DataTupleType getType() {
        return this.type;
    }

    public void setType(DataTupleType type) {
        this.type = type;
    }

    public Boolean isFinished() {
        return this.finished;
    }

    public Boolean getFinished() {
        return this.finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Long getCorrect() {
        return this.correct;
    }

    public void setCorrect(Long correct) {
        this.correct = correct;
    }

    public Long getWrong() {
        return this.wrong;
    }

    public void setWrong(Long wrong) {
        this.wrong = wrong;
    }

    public Long getSkipped() {
        return this.skipped;
    }

    public void setSkipped(Long skipped) {
        this.skipped = skipped;
    }

    public TupleDto id(Long id) {
        this.id = id;
        return this;
    }

    public TupleDto data_element_id_1(Long data_element_id_1) {
        this.data_element_id_1 = data_element_id_1;
        return this;
    }

    public TupleDto data_element_id_2(Long data_element_id_2) {
        this.data_element_id_2 = data_element_id_2;
        return this;
    }

    public TupleDto type(DataTupleType type) {
        this.type = type;
        return this;
    }

    public TupleDto finished(Boolean finished) {
        this.finished = finished;
        return this;
    }

    public TupleDto correct(Long correct) {
        this.correct = correct;
        return this;
    }

    public TupleDto wrong(Long wrong) {
        this.wrong = wrong;
        return this;
    }

    public TupleDto skipped(Long skipped) {
        this.skipped = skipped;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TupleDto)) {
            return false;
        }
        TupleDto tupleDto = (TupleDto) o;
        return Objects.equals(id, tupleDto.id) && Objects.equals(data_element_id_1, tupleDto.data_element_id_1)
                && Objects.equals(data_element_id_2, tupleDto.data_element_id_2) && Objects.equals(type, tupleDto.type)
                && Objects.equals(finished, tupleDto.finished) && Objects.equals(correct, tupleDto.correct)
                && Objects.equals(wrong, tupleDto.wrong) && Objects.equals(skipped, tupleDto.skipped);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, data_element_id_1, data_element_id_2, type, finished, correct, wrong, skipped);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", data_element_id_1='" + getData_element_id_1() + "'"
                + ", data_element_id_2='" + getData_element_id_2() + "'" + ", type='" + getType() + "'" + ", finished='"
                + isFinished() + "'" + ", correct='" + getCorrect() + "'" + ", wrong='" + getWrong() + "'"
                + ", skipped='" + getSkipped() + "'" + "}";
    }

    public static DataTupleType stringToDataTupleType(final String type) {
        if (type.equals("\"AUDIO_AUDIO\""))
            return DataTupleType.AUDIO_AUDIO;
        if (type.equals("\"AUDIO_TEXT\""))
            return DataTupleType.AUDIO_TEXT;
        if (type.equals("\"IMAGE_AUDIO\""))
            return DataTupleType.IMAGE_AUDIO;
        if (type.equals("\"IMAGE_TEXT\""))
            return DataTupleType.IMAGE_TEXT;
        if (type.equals("\"TEXT_AUDIO\""))
            return DataTupleType.TEXT_AUDIO;
        if (type.equals("\"TEXT_TEXT\""))
            return DataTupleType.TEXT_TEXT;
        return null;
    }
}
