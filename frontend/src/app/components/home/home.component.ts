import {HttpClient} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {AchievementWrapper} from 'src/app/models/achievement-wrapper';
import {CustomUserDetails} from 'src/app/models/spring-principal';
import {AuthService} from 'src/app/services/auth.service';
import {environment} from 'src/environments/environment';

import {UserGroup} from '../../models/user-group';
import {UserGroupService} from '../../services/user-group.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  private groupId = 1;
  user: CustomUserDetails;

  activeAchievementWrapper: AchievementWrapper;

  constructor(
      public authService: AuthService, private httpClient: HttpClient,
      private userGroupService: UserGroupService) {
    this.groupId = this.userGroupService.userGroupId;
    authService.getUser().subscribe((user) => {
      this.user = user.principal;
      this.user.gamificationOn = user.principal.user.gamificationOn;
    });
  }

  ngOnInit(): void {
    this.getActiveAchievement();
  }


  private getActiveAchievement(): void {
    this.httpClient
        .get<AchievementWrapper>(
            `${environment.url}user_group/${this.groupId}/achievement/active`)
        .subscribe((value) => {
          this.activeAchievementWrapper = value;
        });
  }
}
