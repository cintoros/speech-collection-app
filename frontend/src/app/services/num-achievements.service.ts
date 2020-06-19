import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {environment} from 'src/environments/environment';

import {SnackBarService} from './snack-bar.service';
import {UserGroupService} from './user-group.service';

@Injectable({providedIn: 'root'})
export class NumAchievementsService {
  private messageSource = new BehaviorSubject('0');
  currentMessage = this.messageSource.asObservable();

  oldnumber: number;
  hasIncreased: Boolean;

  groupId: number;

  constructor(
      private httpClient: HttpClient,
      private userGroupService: UserGroupService,
      private snackBarService: SnackBarService) {
    this.groupId = this.userGroupService.userGroupId;
  }

  changeMessage(message: string) {
    this.messageSource.next(message)
  }

  getHasIncreased() {
    var res = this.hasIncreased;
    this.hasIncreased = false;
    return res;
  }

  getNumber() {
    this.httpClient
        .get<number>(
            `${environment.url}user_group/${this.groupId}/numNewAchievements`)
        .subscribe((res) => {
          this.changeMessage(res.toString());
          if (res > this.oldnumber)
            this.snackBarService.openMessage(
                'Neue Errungenschaft freigeschaltet');
          ;
          this.oldnumber = res;
        });
  }
}
