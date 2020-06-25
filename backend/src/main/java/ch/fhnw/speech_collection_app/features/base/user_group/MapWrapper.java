package ch.fhnw.speech_collection_app.features.base.user_group;

import java.util.Objects;

import ch.fhnw.speech_collection_app.features.base.user_group.CantonClass.CantonEnum;

public class MapWrapper {
    CantonEnum canton;
    Long points;

    public MapWrapper() {
    }

    public MapWrapper(CantonEnum canton, Long points) {
        this.canton = canton;
        this.points = points;
    }

    public CantonEnum getCanton() {
        return this.canton;
    }

    public void setCanton(CantonEnum canton) {
        this.canton = canton;
    }

    public Long getPoints() {
        return this.points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public MapWrapper canton(CantonEnum canton) {
        this.canton = canton;
        return this;
    }

    public MapWrapper points(Long points) {
        this.points = points;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MapWrapper)) {
            return false;
        }
        MapWrapper mapWrapper = (MapWrapper) o;
        return Objects.equals(canton, mapWrapper.canton) && Objects.equals(points, mapWrapper.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(canton, points);
    }

    @Override
    public String toString() {
        return "{" + " canton='" + getCanton() + "'" + ", points='" + getPoints() + "'" + "}";
    }

}
