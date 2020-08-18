import { OccurrenceMode } from '../../../models/occurrence-mode';

export interface OverviewOccurrence {
  text: string;
  correct: number;
  wrong: number;
  id: number;
  mode: OccurrenceMode;
  dataElementId_2: number;
}
