import { Component, Input } from '@angular/core';
import { AchievementWrapper } from 'src/app/models/achievement-wrapper';

@Component({
  selector: 'app-badge',
  templateUrl: './badge.component.html',
  styleUrls: ['./badge.component.scss']
})
export class BadgeComponent {
  @Input() achievementWrapper: AchievementWrapper;

  // TODO this could be calculated in the backend... -> it already is ;)
  getLevel(): number {
    const points = this.achievementWrapper.userAchievements.points;
    const lvl1 = this.achievementWrapper.achievements.pointsLvl1;
    const lvl2 = this.achievementWrapper.achievements.pointsLvl2;
    const lvl3 = this.achievementWrapper.achievements.pointsLvl3;
    const lvl4 = this.achievementWrapper.achievements.pointsLvl4;

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

  getRemainingDays() {
    const s = new Date(this.achievementWrapper.achievements.endTime);
    return this.millisecondsToDays(s.valueOf() - Date.now().valueOf());
  }

  millisecondsToDays = (ms: number) => Math.ceil(ms / 1000 / 60 / 60 / 24);
  getLastLevelPoints = () => this.getLevelPoints(this.getLevel());
  getNextLevelPoints = () => this.getLevelPoints(this.getLevel() + 1);
  getType = () => this.achievementWrapper.achievements.batchName;
  getPoints = () => this.achievementWrapper.userAchievements.points;

  private getLevelPoints(level: number) {
    const achievement = this.achievementWrapper.achievements;
    const points = [0, achievement.pointsLvl1, achievement.pointsLvl2, achievement.pointsLvl3, achievement.pointsLvl4];
    return points[level];
  }
}
