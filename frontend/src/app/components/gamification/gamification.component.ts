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

  activeAchievementsWrapper: AchievementWrapper[];
  nonActiveAchievementsWrapper: AchievementWrapper[];
  levels: number[];

  constructor(
      private httpClient: HttpClient,
      private userGroupService: UserGroupService) {
    this.groupId = this.userGroupService.userGroupId;
  }

  ngOnInit(): void {
    this.getActiveAchievements();
    this.getNonActiveAchievements();
  }


  private getActiveAchievements(): void {
    this.httpClient
        .get<AchievementWrapper[]>(
            `${environment.url}user_group/${this.groupId}/achievements/active`)
        .subscribe((value) => {
          this.activeAchievementsWrapper = value;
        });
  }

  private getNonActiveAchievements(): void {
    this.httpClient
        .get<AchievementWrapper[]>(`${environment.url}user_group/${
            this.groupId}/achievements/nonactive`)
        .subscribe((value) => {
          this.nonActiveAchievementsWrapper = value;
        });
  }
}
