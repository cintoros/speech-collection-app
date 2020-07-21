import { Component, Input, OnChanges } from '@angular/core';
import { AchievementWrapper } from 'src/app/models/achievement-wrapper';

@Component({
  selector: 'app-daily-goal',
  templateUrl: './daily-goal.component.html',
  styleUrls: ['./daily-goal.component.scss']
})
export class DailyGoalComponent implements OnChanges {
  @Input() achievementWrapper: AchievementWrapper;
  level = 0;

  ngOnChanges(): void {
    // TODO simplify?
    const points = this.achievementWrapper.userAchievementDto.points;
    const lvl1 = this.achievementWrapper.achievementDto.points_lvl1;
    const lvl2 = this.achievementWrapper.achievementDto.points_lvl2;
    const lvl3 = this.achievementWrapper.achievementDto.points_lvl3;
    const lvl4 = this.achievementWrapper.achievementDto.points_lvl4;

    if (points - lvl4 >= 0) {
      this.level = 4;
    } else if (points - lvl3 >= 0) {
      this.level = 3;
    } else if (points - lvl2 >= 0) {
      this.level = 2;
    } else if (points - lvl1 >= 0) {
      this.level = 1;
    } else {
      this.level = 0;
    }
  }
}
