import {Component, Input, OnInit} from '@angular/core';
import {AchievementWrapper} from 'src/app/models/achievement-wrapper';

@Component({
  selector: 'app-daily-goal',
  templateUrl: './daily-goal.component.html',
  styleUrls: ['./daily-goal.component.scss']
})
export class DailyGoalComponent implements OnInit {
  @Input() achievementWrapper: AchievementWrapper;

  constructor() {}

  ngOnInit(): void {}

  getLevel(achievementWrapper: AchievementWrapper): number {
    var points = achievementWrapper.userAchievementDto.points;
    var lvl1 = achievementWrapper.achievementDto.points_lvl1;
    var lvl2 = achievementWrapper.achievementDto.points_lvl2;
    var lvl3 = achievementWrapper.achievementDto.points_lvl3;
    var lvl4 = achievementWrapper.achievementDto.points_lvl4;

    if (points - lvl4 >= 0) return 4;
    if (points - lvl3 >= 0) return 3;
    if (points - lvl2 >= 0) return 2;
    if (points - lvl1 >= 0) return 1;
    return 0;
  }

  getColorofNextLevel(achievementWrapper: AchievementWrapper): String {
    var lvl = this.getLevel(achievementWrapper);
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
