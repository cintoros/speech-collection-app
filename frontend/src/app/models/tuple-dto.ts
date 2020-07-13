export class TupleDto {
  id: number;
  data_element_id_1: number;
  data_element_id_2: number;
  type: TupleType;
  finished: number;
  correct: number;
  wrong: number;
  skipped: number;

  constructor(
      $dataElementId1: number, $dataElementId2: number, $type: TupleType,
      $finished: number, $correct: number, $wrong: number, $skipped: number) {
    this.data_element_id_1 = $dataElementId1;
    this.data_element_id_1 = $dataElementId2;
    this.type = $type;
    this.finished = $finished;
    this.correct = $correct;
    this.wrong = $wrong;
    this.skipped = $skipped;
  }
}

export enum TupleType {
  TEXT_TEXT = 'TEXT_TEXT',
  AUDIO_AUDIO = 'AUDIO_AUDIO',
  TEXT_AUDIO = 'TEXT_AUDIO',
  AUDIO_TEXT = 'AUDIO_TEXT',
  IMAGE_AUDIO = 'IMAGE_AUDIO',
  IMAGE_TEXT = 'IMAGE_TEXT',

}
