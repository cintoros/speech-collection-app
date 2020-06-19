import {HttpClient} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {NumAchievementsService} from 'src/app/services/num-achievements.service';
import {UserGroupService} from 'src/app/services/user-group.service';
import {environment} from 'src/environments/environment';

@Component({
  selector: 'app-num-new-achievements',
  templateUrl: './num-new-achievements.component.html',
  styleUrls: ['./num-new-achievements.component.scss']
})
export class NumNewAchievementsComponent implements OnInit {
  groupId: number;
  message: string;

  constructor(
      private httpClient: HttpClient,
      private userGroupService: UserGroupService,
      private numAchievementsService: NumAchievementsService) {
    this.groupId = this.userGroupService.userGroupId;
  }

  ngOnInit(): void {
    this.numAchievementsService.currentMessage.subscribe(
        message => this.message = message);
    this.numAchievementsService.getNumber();
  }
}
