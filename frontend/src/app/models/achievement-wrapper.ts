import {AchievementDto} from './achievement-dto';
import {UserAchievementDto} from './user-achievement-dto';

export class AchievementWrapper {
  achievementDto: AchievementDto;
  userAchievementDto: UserAchievementDto;
  percentOfUsers: number;

  constructor(
      $achievementDto: AchievementDto, $userAchievementDto: UserAchievementDto,
      $percentOfUsers: number) {
    this.achievementDto = $achievementDto;
    this.userAchievementDto = $userAchievementDto;
    this.percentOfUsers = $percentOfUsers;
  }
}
