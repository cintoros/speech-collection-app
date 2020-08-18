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
    if (this.achievementWrapper) {
      this.level = this.achievementWrapper.level;
    }
  }
}
