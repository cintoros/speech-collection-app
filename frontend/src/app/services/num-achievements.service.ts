import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {environment} from 'src/environments/environment';

import {UserGroupService} from './user-group.service';

@Injectable({providedIn: 'root'})
export class NumAchievementsService {
  private messageSource = new BehaviorSubject('default message');
  currentMessage = this.messageSource.asObservable();
  groupId: number;

  constructor(
      private httpClient: HttpClient,
      private userGroupService: UserGroupService) {
    this.groupId = this.userGroupService.userGroupId;
  }

  changeMessage(message: string) {
    this.messageSource.next(message)
  }

  getNumber() {
    this.httpClient
        .get<number>(
            `${environment.url}user_group/${this.groupId}/numNewAchievements`)
        .subscribe((res) => {
          this.changeMessage(res.toString());
        });
  }
}
