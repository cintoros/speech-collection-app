import { ElementType } from "./element-type";

export class TupleDto {
  dataElementId1: number;
  dataElementId2: number;
  type: TupleType;
  finished: number;
  correct: number;
  wrong: number;
  skipped: number;
  type1: ElementType;
  type2: ElementType;

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

    if ($type == TupleType.AUDIO_AUDIO || $type == TupleType.AUDIO_TEXT) this.type1 = ElementType.AUDIO;
    else if ($type == TupleType.TEXT_AUDIO || $type == TupleType.TEXT_TEXT) this.type1 = ElementType.TEXT;
    else if ($type == TupleType.IMAGE_AUDIO) this.type1 = ElementType.IMAGE;

    if ($type == TupleType.AUDIO_AUDIO || $type == TupleType.TEXT_AUDIO || TupleType.IMAGE_AUDIO) this.type2 = ElementType.AUDIO;
    else if ($type == TupleType.AUDIO_TEXT || $type == TupleType.TEXT_TEXT) this.type2 = ElementType.TEXT;
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
