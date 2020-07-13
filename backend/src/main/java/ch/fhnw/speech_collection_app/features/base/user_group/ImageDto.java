package ch.fhnw.speech_collection_app.features.base.user_group;

import java.util.Objects;

public class ImageDto {
    private Long id;
    private Long dataElementId;
    private String path;
    private String licence;

    public ImageDto() {
    }

    public ImageDto(Long id, Long dataElementId, String path, String licence) {
        this.id = id;
        this.dataElementId = dataElementId;
        this.path = path;
        this.licence = licence;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDataElementId() {
        return this.dataElementId;
    }

    public void setDataElementId(Long dataElementId) {
        this.dataElementId = dataElementId;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLicence() {
        return this.licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public ImageDto id(Long id) {
        this.id = id;
        return this;
    }

    public ImageDto dataElementId(Long dataElementId) {
        this.dataElementId = dataElementId;
        return this;
    }

    public ImageDto path(String path) {
        this.path = path;
        return this;
    }

    public ImageDto licence(String licence) {
        this.licence = licence;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ImageDto)) {
            return false;
        }
        ImageDto imageDto = (ImageDto) o;
        return Objects.equals(id, imageDto.id) &&
                Objects.equals(dataElementId, imageDto.dataElementId) &&
                Objects.equals(path, imageDto.path) &&
                Objects.equals(licence, imageDto.licence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataElementId, path, licence);
    }

    @Override
    public String toString() {
        return "{"
                + " id='" + getId() + "'"
                + ", dataElementId='" + getDataElementId() + "'"
                + ", path='" + getPath() + "'"
                + ", licence='" + getLicence() + "'"
                + "}";
    }
}
