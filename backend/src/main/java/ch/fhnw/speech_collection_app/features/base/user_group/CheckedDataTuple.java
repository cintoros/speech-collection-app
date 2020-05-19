package ch.fhnw.speech_collection_app.features.base.user_group;

import java.util.Objects;

import ch.fhnw.speech_collection_app.jooq.enums.CheckedDataTupleType;

public class CheckedDataTuple {
    private Long id;
    private Long user_id;
    private Long data_tuple_id;
    private CheckedDataTupleType type;

    public CheckedDataTuple() {
    }

    public CheckedDataTuple(Long id, Long user_id, Long data_tuple_id, CheckedDataTupleType type) {
        this.id = id;
        this.user_id = user_id;
        this.data_tuple_id = data_tuple_id;
        this.type = type;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return this.user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getData_tuple_id() {
        return this.data_tuple_id;
    }

    public void setData_tuple_id(Long data_tuple_id) {
        this.data_tuple_id = data_tuple_id;
    }

    public CheckedDataTupleType getType() {
        return this.type;
    }

    public void setType(CheckedDataTupleType type) {
        this.type = type;
    }

    public CheckedDataTuple id(Long id) {
        this.id = id;
        return this;
    }

    public CheckedDataTuple user_id(Long user_id) {
        this.user_id = user_id;
        return this;
    }

    public CheckedDataTuple data_tuple_id(Long data_tuple_id) {
        this.data_tuple_id = data_tuple_id;
        return this;
    }

    public CheckedDataTuple type(CheckedDataTupleType type) {
        this.type = type;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CheckedDataTuple)) {
            return false;
        }
        CheckedDataTuple checkedDataTuple = (CheckedDataTuple) o;
        return Objects.equals(id, checkedDataTuple.id) && Objects.equals(user_id, checkedDataTuple.user_id)
                && Objects.equals(data_tuple_id, checkedDataTuple.data_tuple_id)
                && Objects.equals(type, checkedDataTuple.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_id, data_tuple_id, type);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", user_id='" + getUser_id() + "'" + ", data_tuple_id='"
                + getData_tuple_id() + "'" + ", type='" + getType() + "'" + "}";
    }

}
