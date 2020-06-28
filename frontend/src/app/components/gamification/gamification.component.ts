import {HttpClient} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {AchievementWrapper} from 'src/app/models/achievement-wrapper';
import {CustomUserDetails} from 'src/app/models/spring-principal';
import {AuthService} from 'src/app/services/auth.service';
import {NumAchievementsService} from 'src/app/services/num-achievements.service';
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
  user: CustomUserDetails;
  constructor(
      public authService: AuthService, private httpClient: HttpClient,
      private userGroupService: UserGroupService,
      private numAchievementsService: NumAchievementsService) {
    this.groupId = this.userGroupService.userGroupId;
    authService.getUser().subscribe((user) => {
      this.user = user.principal;
      this.user.gamificationOn = user.principal.user.gamificationOn;
    });
  }

  ngOnInit(): void {
    this.getActiveAchievements();
    this.getNonActiveAchievements();
    this.activeAchievementsWrapper.length
  }


  private getActiveAchievements(): void {
    this.httpClient
        .get<AchievementWrapper[]>(
            `${environment.url}user_group/${this.groupId}/achievements/active`)
        .subscribe((value) => {
          this.activeAchievementsWrapper = value;
          this.numAchievementsService.getNumber();
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
