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

  private getAchievements(): void {
    this.httpClient
        .get<AchievementWrapper[]>(
            `${environment.url}user_group/${this.groupId}/achievements`)
        .subscribe((value) => {
          this.achievementsWrapper = value;
        });
  }
}
