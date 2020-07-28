import { Component, Input, OnChanges } from '@angular/core';
import { AchievementWrapper } from 'src/app/models/achievement-wrapper';
import { FeaturesService } from '../../../services/features.service';

@Component({
  selector: 'app-badge',
  templateUrl: './badge.component.html',
  styleUrls: ['./badge.component.scss']
})
export class BadgeComponent implements OnChanges {
  @Input() achievementWrapper: AchievementWrapper;
  types = ['audio-month', 'write-month', 'check-month'];
  glyphs = ['microphone-alt', 'edit', 'check-square'];
  indexOf = 0;
  private pointPerLevel = [0, 10, 20, 50, 100];

  constructor(private featuresService: FeaturesService) {
    featuresService.getFeatureFlags().subscribe(value => this.pointPerLevel = value.gamification.pointPerLevel);
  }

  ngOnChanges(): void {
    this.indexOf = this.types.indexOf(this.achievementWrapper.achievements.batchName);
  }

  getRemainingDays() {
    const s = new Date(this.achievementWrapper.achievements.endTime);
    return this.millisecondsToDays(s.valueOf() - Date.now().valueOf());
  }

  getLevel = (): number => this.achievementWrapper.level;
  millisecondsToDays = (ms: number) => Math.ceil(ms / 1000 / 60 / 60 / 24);
  getLastLevelPoints = () => this.getLevelPoints(this.getLevel());
  getNextLevelPoints = () => this.getLevelPoints(this.getLevel() + 1);
  getPoints = () => this.achievementWrapper.userAchievements.points;
  getGlyph = () => this.glyphs[this.indexOf];
  getLevelPoints = (level: number) => this.pointPerLevel[level];
}
