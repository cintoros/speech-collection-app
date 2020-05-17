package ch.fhnw.speech_collection_app.features.base.user_group;

import java.util.*;

public class TextDto {
  private Long id;
  private Long dialectId;
  private Long dataElementId;
  private Boolean isSentenceError;
  private String text;

  public TextDto() {}

  public TextDto(Long id, Long dialectId, Long dataElementId,
                 Boolean isSentenceError, String text) {
    this.id = id;
    this.dialectId = dialectId;
    this.dataElementId = dataElementId;
    this.isSentenceError = isSentenceError;
    this.text = text;
  }

  public Long getId() { return this.id; }

  public void setId(Long id) { this.id = id; }

  public Long getDialectId() { return this.dialectId; }

  public void setDialectId(Long dialectId) { this.dialectId = dialectId; }

  public Long getDataElementId() { return this.dataElementId; }

  public void setDataElementId(Long dataElementId) {
    this.dataElementId = dataElementId;
  }

  public Boolean isIsSentenceError() { return this.isSentenceError; }

  public Boolean getIsSentenceError() { return this.isSentenceError; }

  public void setIsSentenceError(Boolean isSentenceError) {
    this.isSentenceError = isSentenceError;
  }

  public String getText() { return this.text; }

  public void setText(String text) { this.text = text; }

  public TextDto id(Long id) {
    this.id = id;
    return this;
  }

  public TextDto dialectId(Long dialectId) {
    this.dialectId = dialectId;
    return this;
  }

  public TextDto dataElementId(Long dataElementId) {
    this.dataElementId = dataElementId;
    return this;
  }

  public TextDto isSentenceError(Boolean isSentenceError) {
    this.isSentenceError = isSentenceError;
    return this;
  }

  public TextDto text(String text) {
    this.text = text;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof TextDto)) {
      return false;
    }
    TextDto textDto = (TextDto)o;
    return Objects.equals(id, textDto.id) &&
        Objects.equals(dialectId, textDto.dialectId) &&
        Objects.equals(dataElementId, textDto.dataElementId) &&
        Objects.equals(isSentenceError, textDto.isSentenceError) &&
        Objects.equals(text, textDto.text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, dialectId, dataElementId, isSentenceError, text);
  }

  @Override
  public String toString() {
    return "{"
        + " id='" + getId() + "'"
        + ", dialectId='" + getDialectId() + "'"
        + ", dataElementId='" + getDataElementId() + "'"
        + ", isSentenceError='" + isIsSentenceError() + "'"
        + ", text='" + getText() + "'"
        + "}";
  }
}
