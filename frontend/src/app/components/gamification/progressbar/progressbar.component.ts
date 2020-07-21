import { Component, Input, OnInit } from '@angular/core';
import { AchievementWrapper } from 'src/app/models/achievement-wrapper';

@Component({
  selector: 'app-progressbar',
  templateUrl: './progressbar.component.html',
  styleUrls: ['./progressbar.component.scss']
})
export class ProgressbarComponent implements OnInit {
  @Input() achievementWrapper: AchievementWrapper;

  constructor() {
  }

  ngOnInit(): void {
  }

  getProgressColor(achievementWrapper: AchievementWrapper): string {
    const lvl = this.getLevel(achievementWrapper);
    switch (lvl) {
      case 0:
        return 'bronze';
      case 1:
        return 'silver';
      case 2:
        return 'gold';
      case 3:
      case 4:
        return 'green';
    }
  }

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

  getProgressPercent(achievementWrapper: AchievementWrapper): number {
    const lvl = this.getLevel(achievementWrapper);
    if (lvl === 4) {
      return 100;
    }
    const points = achievementWrapper.userAchievements.points;
    if (lvl === 3) {
      return (points / achievementWrapper.achievements.pointsLvl4) * 100;
    }
    if (lvl === 2) {
      return (points / achievementWrapper.achievements.pointsLvl3) * 100;
    }
    if (lvl === 1) {
      return (points / achievementWrapper.achievements.pointsLvl2) * 100;
    }
    if (lvl === 0) {
      return (points / achievementWrapper.achievements.pointsLvl1) * 100;
    }
    return 0;
  }

  getRemainingPoints(achievementWrapper: AchievementWrapper): string {
    const lvl = this.getLevel(achievementWrapper);
    const points = achievementWrapper.userAchievements.points.toString();
    if (lvl === 4) {
      return points;
    }

    if (lvl === 3) {
      return points + '/' +
          achievementWrapper.achievements.pointsLvl4.toString();
    }
    if (lvl === 2) {
      return points + '/' +
          achievementWrapper.achievements.pointsLvl3.toString();
    }
    if (lvl === 1) {
      return points + '/' +
          achievementWrapper.achievements.pointsLvl2.toString();
    }
    if (lvl === 0) {
      return points + '/' +
          achievementWrapper.achievements.pointsLvl1.toString();
    }
    return '0';
  }
}
