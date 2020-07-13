package ch.fhnw.speech_collection_app.features.base.user_group;

import java.util.Objects;

public class ReturnWrapper {
    private DataElementDto dataElementDto;
    private TextDto textDto;
    private AudioDto audioDto;
    private ImageDto imageDto;
    private ElementType elementType;
    private AchievementWrapper achievementWrapper;

    public ReturnWrapper() {
    }

    public ReturnWrapper(DataElementDto dataElementDto, TextDto textDto, AudioDto audioDto, ImageDto imageDto,
                         ElementType elementType, AchievementWrapper achievementWrapper) {
        this.dataElementDto = dataElementDto;
        this.textDto = textDto;
        this.audioDto = audioDto;
        this.imageDto = imageDto;
        this.elementType = elementType;
        this.achievementWrapper = achievementWrapper;
    }

    public static ElementType stringToElementType(final String type) {
        if (type.equals("\"TEXT\""))
            return ElementType.TEXT;
        if (type.equals("\"AUDIO\""))
            return ElementType.AUDIO;
        if (type.equals("\"IMAGE\""))
            return ElementType.IMAGE;
        if (type.equals("\"TEXT_OR_IMAGE\""))
            return ElementType.TEXT_OR_IMAGE;
        return null;
    }

    public DataElementDto getDataElementDto() {
        return this.dataElementDto;
    }

    public void setDataElementDto(DataElementDto dataElementDto) {
        this.dataElementDto = dataElementDto;
    }

    public TextDto getTextDto() {
        return this.textDto;
    }

    public void setTextDto(TextDto textDto) {
        this.textDto = textDto;
    }

    public AudioDto getAudioDto() {
        return this.audioDto;
    }

    public void setAudioDto(AudioDto audioDto) {
        this.audioDto = audioDto;
    }

    public ImageDto getImageDto() {
        return this.imageDto;
    }

    public void setImageDto(ImageDto imageDto) {
        this.imageDto = imageDto;
    }

    public ElementType getElementType() {
        return this.elementType;
    }

    public void setElementType(ElementType elementType) {
        this.elementType = elementType;
    }

    public AchievementWrapper getAchievementWrapper() {
        return this.achievementWrapper;
    }

    public void setAchievementWrapper(AchievementWrapper achievementWrapper) {
        this.achievementWrapper = achievementWrapper;
    }

    public ReturnWrapper dataElementDto(DataElementDto dataElementDto) {
        this.dataElementDto = dataElementDto;
        return this;
    }

    public ReturnWrapper textDto(TextDto textDto) {
        this.textDto = textDto;
        return this;
    }

    public ReturnWrapper audioDto(AudioDto audioDto) {
        this.audioDto = audioDto;
        return this;
    }

    public ReturnWrapper imageDto(ImageDto imageDto) {
        this.imageDto = imageDto;
        return this;
    }

    public ReturnWrapper elementType(ElementType elementType) {
        this.elementType = elementType;
        return this;
    }

    public ReturnWrapper achievementWrapper(AchievementWrapper achievementWrapper) {
        this.achievementWrapper = achievementWrapper;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ReturnWrapper)) {
            return false;
        }
        ReturnWrapper returnWrapper = (ReturnWrapper) o;
        return Objects.equals(dataElementDto, returnWrapper.dataElementDto)
                && Objects.equals(textDto, returnWrapper.textDto) && Objects.equals(audioDto, returnWrapper.audioDto)
                && Objects.equals(imageDto, returnWrapper.imageDto)
                && Objects.equals(elementType, returnWrapper.elementType)
                && Objects.equals(achievementWrapper, returnWrapper.achievementWrapper);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataElementDto, textDto, audioDto, imageDto, elementType, achievementWrapper);
    }

    @Override
    public String toString() {
        return "{" + " dataElementDto='" + getDataElementDto() + "'" + ", textDto='" + getTextDto() + "'"
                + ", audioDto='" + getAudioDto() + "'" + ", imageDto='" + getImageDto() + "'" + ", elementType='"
                + getElementType() + "'" + ", achievementWrapper='" + getAchievementWrapper() + "'" + "}";
    }

    public enum ElementType {
        TEXT, AUDIO, IMAGE, TEXT_OR_IMAGE
    }
}
