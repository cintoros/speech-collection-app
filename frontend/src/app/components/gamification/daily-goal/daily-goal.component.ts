import { Component, Input } from '@angular/core';
import { AchievementWrapper } from 'src/app/models/achievement-wrapper';

@Component({
  selector: 'app-daily-goal',
  templateUrl: './daily-goal.component.html',
  styleUrls: ['./daily-goal.component.scss']
})
export class DailyGoalComponent {
  @Input() achievementWrapper: AchievementWrapper;

  getLevel(achievementWrapper: AchievementWrapper): number {
    const points = achievementWrapper.userAchievementDto.points;
    const lvl1 = achievementWrapper.achievementDto.points_lvl1;
    const lvl2 = achievementWrapper.achievementDto.points_lvl2;
    const lvl3 = achievementWrapper.achievementDto.points_lvl3;
    const lvl4 = achievementWrapper.achievementDto.points_lvl4;

    if (points - lvl4 >= 0) {
      return 4;
    }
    if (points - lvl3 >= 0) {
      return 3;
    }
    if (points - lvl2 >= 0) {
      return 2;
    }
    if (points - lvl1 >= 0) {
      return 1;
    }
    return 0;
  }

  getColorofNextLevel(achievementWrapper: AchievementWrapper): string {
    const lvl = this.getLevel(achievementWrapper);
    switch (lvl) {
      case 0:
        return 'Bronze';
      case 1:
        return 'Silber';
      case 2:
        return 'Gold';
      case 3:
        return 'Platin';
      case 4:
      default:
        return '???';
    }
  }
}
