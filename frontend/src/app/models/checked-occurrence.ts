export enum CheckedOccurrenceLabel {SKIPPED = 'SKIPPED', CORRECT = 'CORRECT', WRONG = 'WRONG', PRIVATE = 'PRIVATE', SENTENCE_ERROR = 'SENTENCE_ERROR'}

export class CheckedOccurrence {
  id: number;
  label: CheckedOccurrenceLabel;

  constructor(id: number, label: CheckedOccurrenceLabel) {
    this.id = id;
    this.label = label;
  }
}
