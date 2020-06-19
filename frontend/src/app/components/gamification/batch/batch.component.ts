import {Component, Input, OnInit} from '@angular/core';
import {AchievementWrapper} from 'src/app/models/achievement-wrapper';

@Component({
  selector: 'app-batch',
  templateUrl: './batch.component.html',
  styleUrls: ['./batch.component.scss']
})
export class BatchComponent implements OnInit {
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



  getDescription(achievementWrapper: AchievementWrapper): Text {
    var lvl = this.getLevel(achievementWrapper);
    switch (lvl) {
      case 0:
      case 1:
        return achievementWrapper.achievementDto.description_lvl1;
      case 2:
        return achievementWrapper.achievementDto.description_lvl2;
      case 3:
        return achievementWrapper.achievementDto.description_lvl3;
      case 4:
      default:
        return achievementWrapper.achievementDto.description_lvl4;
    }
  }

  getPoints(achievementWrapper: AchievementWrapper): String {
    return achievementWrapper.userAchievementDto.points.toString();
  }



  getNextLevelPoints(achievementWrapper: AchievementWrapper): String {
    var lvl = this.getLevel(achievementWrapper);
    if (lvl == 4)
      return achievementWrapper.achievementDto.points_lvl4.toString();
    if (lvl == 3)
      return achievementWrapper.achievementDto.points_lvl4.toString();
    if (lvl == 2)
      return achievementWrapper.achievementDto.points_lvl3.toString();
    if (lvl == 1)
      return achievementWrapper.achievementDto.points_lvl2.toString();
    if (lvl == 0)
      return achievementWrapper.achievementDto.points_lvl1.toString();
    return '0';
  }


  getRemainingDays(achievementWrapper: AchievementWrapper): number {
    var s = new Date(achievementWrapper.achievementDto.end_time);
    console.log(s.valueOf() - Date.now().valueOf());
    return this.millisecondsToDays(s.valueOf() - Date.now().valueOf());
  }

  millisecondsToDays(ms: number): number {
    return Math.ceil(ms / 1000 / 60 / 60 / 24);
  }
}
