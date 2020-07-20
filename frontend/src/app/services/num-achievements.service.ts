import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CustomUserDetails } from '../models/spring-principal';
import { AuthService } from './auth.service';
import { SnackBarService } from './snack-bar.service';
import { UserGroupService } from './user-group.service';

@Injectable({providedIn: 'root'})
export class NumAchievementsService {
  oldnumber: number;
  groupId: number;
  user: CustomUserDetails;
  currentMessage = new BehaviorSubject('0');

  constructor(
      public authService: AuthService, private httpClient: HttpClient,
      private userGroupService: UserGroupService,
      private snackBarService: SnackBarService) {
    this.groupId = this.userGroupService.userGroupId;
    authService.getUser().subscribe(user => {
      this.user = user.principal;
      this.user.gamificationOn = user.principal.user.gamificationOn;
    });
  }

  getNumber() {
    this.httpClient
        .get<number>(
            `${environment.url}user_group/${this.groupId}/numNewAchievements`)
        .subscribe(res => {
          this.currentMessage.next(res.toString());
          // FIXME this does not work...
          if (res > this.oldnumber && this.user.gamificationOn) {
            this.snackBarService.openMessage('Neue Errungenschaft freigeschaltet');
          }
          this.oldnumber = res;
        });
  }
}
