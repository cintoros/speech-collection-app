import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AchievementWrapper } from 'src/app/models/achievement-wrapper';
import { AuthService } from 'src/app/services/auth.service';
import { NumAchievementsService } from 'src/app/services/num-achievements.service';
import { UserGroupService } from 'src/app/services/user-group.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-gamification',
  templateUrl: './gamification.component.html',
  styleUrls: ['./gamification.component.scss']
})
export class GamificationComponent implements OnInit {
  activeAchievementsWrapper: AchievementWrapper[] = [];
  nonActiveAchievementsWrapper: AchievementWrapper[] = [];
  gamificationOn = false;
  private groupId = 1;

  constructor(
      public authService: AuthService, private httpClient: HttpClient, private userGroupService: UserGroupService,
      private numAchievementsService: NumAchievementsService) {
    this.groupId = this.userGroupService.userGroupId;
    authService.getUser().subscribe(user => this.gamificationOn = user.principal.user.gamificationOn);
  }

  ngOnInit(): void {
    this.httpClient.get<AchievementWrapper[]>(`${environment.url}user_group/${this.groupId}/achievements/active`)
        .subscribe(value => {
          this.activeAchievementsWrapper = value;
          this.numAchievementsService.getNumber();
        });
    this.httpClient.get<AchievementWrapper[]>(`${environment.url}user_group/${this.groupId}/achievements/nonactive`)
        .subscribe(value => this.nonActiveAchievementsWrapper = value);
  }
}
