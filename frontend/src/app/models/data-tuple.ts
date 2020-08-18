export interface DataTuple {
  id: number;
  dataElementId_1: number;
  dataElementId_2: number;
  type: DataTupleType;
  finished: number;
  correct: number;
  wrong: number;
  skipped: number;
}

export enum DataTupleType {
  TEXT_TEXT = 'TEXT_TEXT',
  AUDIO_AUDIO = 'AUDIO_AUDIO',
  TEXT_AUDIO = 'TEXT_AUDIO',
  AUDIO_TEXT = 'AUDIO_TEXT',
  IMAGE_AUDIO = 'IMAGE_AUDIO',
  IMAGE_TEXT = 'IMAGE_TEXT',
  RECORDING = 'RECORDING',
}
