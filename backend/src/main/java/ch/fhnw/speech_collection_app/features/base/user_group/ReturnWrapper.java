package ch.fhnw.speech_collection_app.features.base.user_group;

import ch.fhnw.speech_collection_app.jooq.tables.pojos.Audio;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.DataElement;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.Image;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.Text;

//FIXME this is not even the same as the typescript one.
//TODO not sure what is even used of all this stuff...
public class ReturnWrapper {
    public final DataElement dataElement;
    public final Text text;
    public final Audio audio;
    public final Image image;
    public final ElementType elementType;
    public final AchievementWrapper achievementWrapper;

    public ReturnWrapper(DataElement dataElement, Text text, Audio audio, Image image, ElementType elementType, AchievementWrapper achievementWrapper) {
        this.dataElement = dataElement;
        this.text = text;
        this.audio = audio;
        this.image = image;
        this.elementType = elementType;
        this.achievementWrapper = achievementWrapper;
    }


}
