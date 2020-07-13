import { AchievementWrapper } from './achievement-wrapper';
import { TupleDto } from './tuple-dto';

export class CheckWrapper {
  tupleDto: TupleDto;
  achievementWrapper: AchievementWrapper;

  constructor($tupleDto: TupleDto, $achievementWrapper: AchievementWrapper) {
    this.tupleDto = $tupleDto;
    this.achievementWrapper = $achievementWrapper;
  }
}
