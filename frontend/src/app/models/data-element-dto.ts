export class DataElementDto {
  id: number;
  sourceId: number;
  userId: number;
  userGroupId: number;
  createdTime: Date;
  finished: boolean;
  isPrivate: boolean;
  skipped: number;

  constructor(
    $id: number,
    $sourceId: number,
    $userId: number,
    $userGroupId: number,
    $createdTime: Date,
    $finished: boolean,
    $isPrivate: boolean,
    $skipped: number
  ) {
    this.id = $id;
    this.sourceId = $sourceId;
    this.userId = $userId;
    this.userGroupId = $userGroupId;
    this.createdTime = $createdTime;
    this.finished = $finished;
    this.isPrivate = $isPrivate;
    this.skipped = $skipped;
  }
}
