export class TupleDto {
  private dataElementId1: number;
  private dataElementId2: number;
  private type: TupleType;
  private finished: number;
  private correct: number;
  private wrong: number;
  private skipped: number;

  constructor(
    $dataElementId1: number,
    $dataElementId2: number,
    $type: TupleType,
    $finished: number,
    $correct: number,
    $wrong: number,
    $skipped: number
  ) {
    this.dataElementId1 = $dataElementId1;
    this.dataElementId2 = $dataElementId2;
    this.type = $type;
    this.finished = $finished;
    this.correct = $correct;
    this.wrong = $wrong;
    this.skipped = $skipped;
  }
}

export enum TupleType {
  TEXT_TEXT = "TEXT_TEXT",
  AUDIO_AUDIO = "AUDIO_AUDIO",
  TEXT_AUDIO = "TEXT_AUDIO",
  AUDIO_TEXT = "AUDIO_TEXT",
  IMAGE_AUDIO = "IMAGE_AUDIO",
  RECORDING = "RECORDING",
}
