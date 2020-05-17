package ch.fhnw.speech_collection_app.features.base.user_group;

import java.util.*;

public class DataElementDto {
  private Long id;
  private Long sourceId;
  private Long userId;
  private Long userGroupId;
  private Date createdTime;
  private Boolean finished;
  private Boolean isPrivate;
  private Long skipped;

  public DataElementDto() {}

  public DataElementDto(Long id, Long sourceId, Long userId, Long userGroupId,
                        Date createdTime, Boolean finished, Boolean isPrivate,
                        Long skipped) {
    this.id = id;
    this.sourceId = sourceId;
    this.userId = userId;
    this.userGroupId = userGroupId;
    this.createdTime = createdTime;
    this.finished = finished;
    this.isPrivate = isPrivate;
    this.skipped = skipped;
  }

  public Long getId() { return this.id; }

  public void setId(Long id) { this.id = id; }

  public Long getSourceId() { return this.sourceId; }

  public void setSourceId(Long sourceId) { this.sourceId = sourceId; }

  public Long getUserId() { return this.userId; }

  public void setUserId(Long userId) { this.userId = userId; }

  public Long getUserGroupId() { return this.userGroupId; }

  public void setUserGroupId(Long userGroupId) {
    this.userGroupId = userGroupId;
  }

  public Date getCreatedTime() { return this.createdTime; }

  public void setCreatedTime(Date createdTime) {
    this.createdTime = createdTime;
  }

  public Boolean isFinished() { return this.finished; }

  public Boolean getFinished() { return this.finished; }

  public void setFinished(Boolean finished) { this.finished = finished; }

  public Boolean isIsPrivate() { return this.isPrivate; }

  public Boolean getIsPrivate() { return this.isPrivate; }

  public void setIsPrivate(Boolean isPrivate) { this.isPrivate = isPrivate; }

  public Long getSkipped() { return this.skipped; }

  public void setSkipped(Long skipped) { this.skipped = skipped; }

  public DataElementDto id(Long id) {
    this.id = id;
    return this;
  }

  public DataElementDto sourceId(Long sourceId) {
    this.sourceId = sourceId;
    return this;
  }

  public DataElementDto userId(Long userId) {
    this.userId = userId;
    return this;
  }

  public DataElementDto userGroupId(Long userGroupId) {
    this.userGroupId = userGroupId;
    return this;
  }

  public DataElementDto createdTime(Date createdTime) {
    this.createdTime = createdTime;
    return this;
  }

  public DataElementDto finished(Boolean finished) {
    this.finished = finished;
    return this;
  }

  public DataElementDto isPrivate(Boolean isPrivate) {
    this.isPrivate = isPrivate;
    return this;
  }

  public DataElementDto skipped(Long skipped) {
    this.skipped = skipped;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof DataElementDto)) {
      return false;
    }
    DataElementDto dataElementDto = (DataElementDto)o;
    return Objects.equals(id, dataElementDto.id) &&
        Objects.equals(sourceId, dataElementDto.sourceId) &&
        Objects.equals(userId, dataElementDto.userId) &&
        Objects.equals(userGroupId, dataElementDto.userGroupId) &&
        Objects.equals(createdTime, dataElementDto.createdTime) &&
        Objects.equals(finished, dataElementDto.finished) &&
        Objects.equals(isPrivate, dataElementDto.isPrivate) &&
        Objects.equals(skipped, dataElementDto.skipped);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, sourceId, userId, userGroupId, createdTime,
                        finished, isPrivate, skipped);
  }

  @Override
  public String toString() {
    return "{"
        + " id='" + getId() + "'"
        + ", sourceId='" + getSourceId() + "'"
        + ", userId='" + getUserId() + "'"
        + ", userGroupId='" + getUserGroupId() + "'"
        + ", createdTime='" + getCreatedTime() + "'"
        + ", finished='" + isFinished() + "'"
        + ", isPrivate='" + isIsPrivate() + "'"
        + ", skipped='" + getSkipped() + "'"
        + "}";
  }
}
