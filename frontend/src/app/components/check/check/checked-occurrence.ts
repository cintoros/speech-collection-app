import {OccurrenceMode} from './check.component';

export enum CheckedOccurrenceLabel {SKIPPED = 'SKIPPED', CORRECT = 'CORRECT', WRONG = 'WRONG', PRIVATE = 'PRIVATE'}

export class CheckedOccurrence {
  id: number;
  label: CheckedOccurrenceLabel;


  constructor(id: number, userId: number, label: CheckedOccurrenceLabel, mode: OccurrenceMode) {
    this.id = id;
    this.label = label;
  }
}

export interface Occurrence {
  mode: OccurrenceMode;
  id: number;
  dataElementId2: number;
  text: string;
}
