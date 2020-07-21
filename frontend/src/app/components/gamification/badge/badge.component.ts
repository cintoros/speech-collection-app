import { Component, Input } from '@angular/core';
import { AchievementWrapper } from 'src/app/models/achievement-wrapper';

@Component({
  selector: 'app-badge',
  templateUrl: './badge.component.html',
  styleUrls: ['./badge.component.scss']
})
export class BadgeComponent {
  @Input() achievementWrapper: AchievementWrapper;

  getLevel(achievementWrapper: AchievementWrapper): number {
    const points = achievementWrapper.userAchievements.points;
    const lvl1 = achievementWrapper.achievements.pointsLvl1;
    const lvl2 = achievementWrapper.achievements.pointsLvl2;
    const lvl3 = achievementWrapper.achievements.pointsLvl3;
    const lvl4 = achievementWrapper.achievements.pointsLvl4;

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


  getDescription(achievementWrapper: AchievementWrapper): Text {
    const lvl = this.getLevel(achievementWrapper);
    switch (lvl) {
      case 0:
      case 1:
        return achievementWrapper.achievements.descriptionLvl1;
      case 2:
        return achievementWrapper.achievements.descriptionLvl2;
      case 3:
        return achievementWrapper.achievements.descriptionLvl3;
      case 4:
      default:
        return achievementWrapper.achievements.descriptionLvl4;
    }
  }

  getPoints(achievementWrapper: AchievementWrapper): string {
    return achievementWrapper.userAchievements.points.toString();
  }


  getNextLevelPoints(achievementWrapper: AchievementWrapper): string {
    const lvl = this.getLevel(achievementWrapper);
    if (lvl === 4) {
      return achievementWrapper.achievements.pointsLvl4.toString();
    }
    if (lvl === 3) {
      return achievementWrapper.achievements.pointsLvl4.toString();
    }
    if (lvl === 2) {
      return achievementWrapper.achievements.pointsLvl3.toString();
    }
    if (lvl === 1) {
      return achievementWrapper.achievements.pointsLvl2.toString();
    }
    if (lvl === 0) {
      return achievementWrapper.achievements.pointsLvl1.toString();
    }
    return '0';
  }


  getRemainingDays(achievementWrapper: AchievementWrapper): number {
    const s = new Date(achievementWrapper.achievements.endTime);
    return this.millisecondsToDays(s.valueOf() - Date.now().valueOf());
  }

  millisecondsToDays(ms: number): number {
    return Math.ceil(ms / 1000 / 60 / 60 / 24);
  }
}
