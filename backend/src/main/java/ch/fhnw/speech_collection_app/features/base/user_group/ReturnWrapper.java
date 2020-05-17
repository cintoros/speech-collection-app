package ch.fhnw.speech_collection_app.features.base.user_group;

import java.util.*;

public class ReturnWrapper {
    private DataElementDto dataElementDto;
    private TextDto textDto;
    private RecordingDto recordingDto;
    private ImageDto imageDto;
    private ElementType elementType;

    public ReturnWrapper() {
    }

    public ReturnWrapper(final DataElementDto dataElementDto, final TextDto textDto, final RecordingDto recordingDto,
            final ImageDto imageDto, final ElementType elementType) {
        this.dataElementDto = dataElementDto;
        this.textDto = textDto;
        this.recordingDto = recordingDto;
        this.imageDto = imageDto;
        this.elementType = elementType;
    }

    public DataElementDto getDataElementDto() {
        return this.dataElementDto;
    }

    public void setDataElementDto(final DataElementDto dataElementDto) {
        this.dataElementDto = dataElementDto;
    }

    public TextDto getTextDto() {
        return this.textDto;
    }

    public void setTextDto(final TextDto textDto) {
        this.textDto = textDto;
    }

    public RecordingDto getRecordingDto() {
        return this.recordingDto;
    }

    public void setRecordingDto(final RecordingDto recordingDto) {
        this.recordingDto = recordingDto;
    }

    public ImageDto getImageDto() {
        return this.imageDto;
    }

    public void setImageDto(final ImageDto imageDto) {
        this.imageDto = imageDto;
    }

    public ElementType getElementType() {
        return this.elementType;
    }

    public void setElementType(final ElementType elementType) {
        this.elementType = elementType;
    }

    public ReturnWrapper dataElementDto(final DataElementDto dataElementDto) {
        this.dataElementDto = dataElementDto;
        return this;
    }

    public ReturnWrapper textDto(final TextDto textDto) {
        this.textDto = textDto;
        return this;
    }

    public ReturnWrapper recordingDto(final RecordingDto recordingDto) {
        this.recordingDto = recordingDto;
        return this;
    }

    public ReturnWrapper imageDto(final ImageDto imageDto) {
        this.imageDto = imageDto;
        return this;
    }

    public ReturnWrapper elementType(final ElementType elementType) {
        this.elementType = elementType;
        return this;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ReturnWrapper)) {
            return false;
        }
        final ReturnWrapper returnWrapper = (ReturnWrapper) o;
        return Objects.equals(dataElementDto, returnWrapper.dataElementDto)
                && Objects.equals(textDto, returnWrapper.textDto)
                && Objects.equals(recordingDto, returnWrapper.recordingDto)
                && Objects.equals(imageDto, returnWrapper.imageDto)
                && Objects.equals(elementType, returnWrapper.elementType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataElementDto, textDto, recordingDto, imageDto, elementType);
    }

    @Override
    public String toString() {
        return "{" + " dataElementDto='" + getDataElementDto() + "'" + ", textDto='" + getTextDto() + "'"
                + ", recordingDto='" + getRecordingDto() + "'" + ", imageDto='" + getImageDto() + "'"
                + ", elementType='" + getElementType() + "'" + "}";
    }

    public static ElementType stringToElementType(final String type) {
        if (type.equals("\"TEXT\""))
            return ElementType.TEXT;
        if (type.equals("\"AUDIO\""))
            return ElementType.AUDIO;
        if (type.equals("\"IMAGE\""))
            return ElementType.IMAGE;
        return null;
    }

    public enum ElementType {
        TEXT, AUDIO, IMAGE
    }

}
