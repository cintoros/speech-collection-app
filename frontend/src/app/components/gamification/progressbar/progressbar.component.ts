import { Component, Input } from '@angular/core';
import { AchievementWrapper } from 'src/app/models/achievement-wrapper';
import { FeaturesService } from '../../../services/features.service';

@Component({
  selector: 'app-progressbar',
  templateUrl: './progressbar.component.html',
  styleUrls: ['./progressbar.component.scss']
})
export class ProgressbarComponent {
  @Input() achievementWrapper: AchievementWrapper;
  private pointPerLevel = [0, 10, 20, 50, 100];
  private colors = ['bronze', 'silver', 'gold', 'green', 'green'];

  constructor(private featuresService: FeaturesService) {
    featuresService.getFeatureFlags().subscribe(value => this.pointPerLevel = value.gamification.pointPerLevel);
  }

  getProgressPercent(): number {
    if (this.achievementWrapper.level === 4) {
      return 100;
    }
    const points = this.achievementWrapper.userAchievements.points;
    return (points / this.pointPerLevel[this.achievementWrapper.level + 1]) * 100;
  }

  getRemainingPoints(): string {
    const points = this.achievementWrapper.userAchievements.points;
    if (this.achievementWrapper.level === 4) {
      return `${points}`;
    }
    return `${points}/${this.pointPerLevel[this.achievementWrapper.level + 1]}`;
  }

  getProgressColor = () => this.colors[this.achievementWrapper.level];
}
