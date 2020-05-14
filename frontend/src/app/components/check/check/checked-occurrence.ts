import {OccurrenceMode} from './check.component';

export enum CheckedOccurrenceLabel {SKIPPED = 'SKIPPED', CORRECT = 'CORRECT', WRONG = 'WRONG', PRIVATE = 'PRIVATE', SENTENCE_ERROR = 'SENTENCE_ERROR'}

export class CheckedOccurrence {
  id: number;
  label: CheckedOccurrenceLabel;

  constructor(id: number, label: CheckedOccurrenceLabel) {
    this.id = id;
    this.label = label;
  }
}

export interface Occurrence {
  mode: OccurrenceMode;
  id: number;
  dataElementId_2: number;
  dataElementId_1: number;
  text: string;
  imageDataElementId?: number;
}
