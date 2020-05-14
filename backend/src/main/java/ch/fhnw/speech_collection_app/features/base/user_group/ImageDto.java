package ch.fhnw.speech_collection_app.features.base.user_group;

public class ImageDto {
    public final Long id;
    public final Boolean isPrivate;

    public ImageDto(Long id, Boolean isPrivate) {
        this.id = id;
        this.isPrivate = isPrivate;
    }
}
