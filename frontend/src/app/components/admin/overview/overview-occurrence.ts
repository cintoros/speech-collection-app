import {OccurrenceMode} from '../../check/check/check.component';

export interface OverviewOccurrence {
  text: string;
  correct: number;
  wrong: number;
  id: number;
  mode: OccurrenceMode;
  dataElementId_2: number;
}
