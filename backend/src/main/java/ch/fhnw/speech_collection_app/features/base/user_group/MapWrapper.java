package ch.fhnw.speech_collection_app.features.base.user_group;

import ch.fhnw.speech_collection_app.features.base.user_group.CantonClass.CantonEnum;

public class MapWrapper {
    public final CantonEnum canton;
    public final Long points;

    public MapWrapper(CantonEnum canton, Long points) {
        this.canton = canton;
        this.points = points;
    }
}
