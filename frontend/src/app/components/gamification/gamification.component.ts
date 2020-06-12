import {HttpClient} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {AchievementWrapper} from 'src/app/models/achievement-wrapper';
import {UserGroupService} from 'src/app/services/user-group.service';
import {environment} from 'src/environments/environment';

@Component({
  selector: 'app-gamification',
  templateUrl: './gamification.component.html',
  styleUrls: ['./gamification.component.scss']
})
export class GamificationComponent implements OnInit {
  private groupId = 1;

  achievementsWrapper: AchievementWrapper[];
  levels: number[];

  constructor(
      private httpClient: HttpClient,
      private userGroupService: UserGroupService) {
    this.groupId = this.userGroupService.userGroupId;
  }

  ngOnInit(): void {
    this.getAchievements()
  }

  private getLevel(achievementWrapper: AchievementWrapper): number {
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

  private getProgressColor(achievementWrapper: AchievementWrapper): String {
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

  private getProgressPercent(achievementWrapper: AchievementWrapper): number {
    var lvl = this.getLevel(achievementWrapper);
    if (lvl == 4) return 100;
    var points = achievementWrapper.userAchievementDto.points;
    if (lvl == 3)
      return (points / achievementWrapper.achievementDto.points_lvl4) * 100;
    if (lvl == 2)
      return (points / achievementWrapper.achievementDto.points_lvl3) * 100;
    if (lvl == 1)
      return (points / achievementWrapper.achievementDto.points_lvl2) * 100;
    if (lvl == 0)
      return (points / achievementWrapper.achievementDto.points_lvl1) * 100;
    return 0;
  }

  private getDescription(achievementWrapper: AchievementWrapper): Text {
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


  private getRemainingDays(achievementWrapper: AchievementWrapper): number {
    var s = new Date(achievementWrapper.achievementDto.end_time);
    console.log(s.valueOf() - Date.now().valueOf());
    return this.millisecondsToDays(s.valueOf() - Date.now().valueOf());
  }

  private millisecondsToDays(ms: number): number {
    return Math.ceil(ms / 1000 / 60 / 60 / 24);
  }

  private getAchievements(): void {
    this.httpClient
        .get<AchievementWrapper[]>(
            `${environment.url}user_group/${this.groupId}/achievements`)
        .subscribe((value) => {
          this.achievementsWrapper = value;
        });
  }
}
