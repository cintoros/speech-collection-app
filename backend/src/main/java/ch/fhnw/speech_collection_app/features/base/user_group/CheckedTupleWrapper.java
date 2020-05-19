package ch.fhnw.speech_collection_app.features.base.user_group;

import java.util.Objects;

public class CheckedTupleWrapper {
    private TupleDto tuple;
    private CheckedDataTuple checkedDataTuple;

    public CheckedTupleWrapper() {
    }

    public CheckedTupleWrapper(TupleDto tuple, CheckedDataTuple checkedDataTuple) {
        this.tuple = tuple;
        this.checkedDataTuple = checkedDataTuple;
    }

    public TupleDto getTuple() {
        return this.tuple;
    }

    public void setTuple(TupleDto tuple) {
        this.tuple = tuple;
    }

    public CheckedDataTuple getCheckedDataTuple() {
        return this.checkedDataTuple;
    }

    public void setCheckedDataTuple(CheckedDataTuple checkedDataTuple) {
        this.checkedDataTuple = checkedDataTuple;
    }

    public CheckedTupleWrapper tuple(TupleDto tuple) {
        this.tuple = tuple;
        return this;
    }

    public CheckedTupleWrapper checkedDataTuple(CheckedDataTuple checkedDataTuple) {
        this.checkedDataTuple = checkedDataTuple;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CheckedTupleWrapper)) {
            return false;
        }
        CheckedTupleWrapper checkedTupleWrapper = (CheckedTupleWrapper) o;
        return Objects.equals(tuple, checkedTupleWrapper.tuple)
                && Objects.equals(checkedDataTuple, checkedTupleWrapper.checkedDataTuple);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tuple, checkedDataTuple);
    }

    @Override
    public String toString() {
        return "{" + " tuple='" + getTuple() + "'" + ", checkedDataTuple='" + getCheckedDataTuple() + "'" + "}";
    }

}
