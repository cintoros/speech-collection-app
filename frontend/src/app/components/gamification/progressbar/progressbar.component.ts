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

  getProgressColor(achievementWrapper: AchievementWrapper): String {
    var lvl = this.getLevel(achievementWrapper);
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
    var points = achievementWrapper.userAchievementDto.points;
    var lvl1 = achievementWrapper.achievementDto.points_lvl1;
    var lvl2 = achievementWrapper.achievementDto.points_lvl2;
    var lvl3 = achievementWrapper.achievementDto.points_lvl3;
    var lvl4 = achievementWrapper.achievementDto.points_lvl4;

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
    var lvl = this.getLevel(achievementWrapper);
    if (lvl == 4) {
      return 100;
    }
    var points = achievementWrapper.userAchievementDto.points;
    if (lvl == 3) {
      return (points / achievementWrapper.achievementDto.points_lvl4) * 100;
    }
    if (lvl == 2) {
      return (points / achievementWrapper.achievementDto.points_lvl3) * 100;
    }
    if (lvl == 1) {
      return (points / achievementWrapper.achievementDto.points_lvl2) * 100;
    }
    if (lvl == 0) {
      return (points / achievementWrapper.achievementDto.points_lvl1) * 100;
    }
    return 0;
  }

  getRemainingPoints(achievementWrapper: AchievementWrapper): String {
    var lvl = this.getLevel(achievementWrapper);
    var points = achievementWrapper.userAchievementDto.points.toString();
    if (lvl == 4) {
      return points;
    }

    if (lvl == 3) {
      return points + '/' +
          achievementWrapper.achievementDto.points_lvl4.toString();
    }
    if (lvl == 2) {
      return points + '/' +
          achievementWrapper.achievementDto.points_lvl3.toString();
    }
    if (lvl == 1) {
      return points + '/' +
          achievementWrapper.achievementDto.points_lvl2.toString();
    }
    if (lvl == 0) {
      return points + '/' +
          achievementWrapper.achievementDto.points_lvl1.toString();
    }
    return '0';
  }
}
