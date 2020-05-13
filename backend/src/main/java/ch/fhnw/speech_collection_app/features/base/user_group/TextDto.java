package ch.fhnw.speech_collection_app.features.base.user_group;

public class TextDto {
  public final Long id;
  public final Boolean isPrivate;
  public final String text;

  public TextDto(Long id, Boolean isPrivate, String text) {
    this.id = id;
    this.isPrivate = isPrivate;
    this.text = text;
  }

  public Long getId() { return id; }

  public String getText() { return text; }
}
