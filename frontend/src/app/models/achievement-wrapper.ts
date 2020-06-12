import {AchievementDto} from './achievement-dto';
import {UserAchievementDto} from './user-achievement-dto';

export class AchievementWrapper {
  achievementDto: AchievementDto;
  userAchievementDto: UserAchievementDto;


  constructor(
      $achievementDto: AchievementDto,
      $userAchievementDto: UserAchievementDto) {
    this.achievementDto = $achievementDto;
    this.userAchievementDto = $userAchievementDto;
  }
}
