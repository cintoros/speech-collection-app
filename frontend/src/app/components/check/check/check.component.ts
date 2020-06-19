import {HttpClient} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {Router} from '@angular/router';
import {AchievementWrapper} from 'src/app/models/achievement-wrapper';
import {CheckWrapper} from 'src/app/models/check-wrapper';
import {TupleDto, TupleType} from 'src/app/models/tuple-dto';
import {NumAchievementsService} from 'src/app/services/num-achievements.service';

import {environment} from '../../../../environments/environment';
import {AuthService} from '../../../services/auth.service';
import {SnackBarService} from '../../../services/snack-bar.service';
import {UserGroupService} from '../../../services/user-group.service';
import {ShortcutComponent} from '../shortcut/shortcut.component';

import {CheckedOccurrenceLabel} from './checked-occurrence';

export enum OccurrenceMode {
  RECORDING = 'RECORDING',
  TEXT_AUDIO = 'TEXT_AUDIO',
}

@Component({
  selector: 'app-check',
  templateUrl: './check.component.html',
  styleUrls: ['./check.component.scss'],
})
export class CheckComponent implements OnInit {
  selectedTupleType = TupleType.TEXT_TEXT;
  private audioPlayer = new Audio();
  private isReady = false;
  private userId: number;
  private groupId = 1;
  tuple: TupleDto;

  isDebug = false;
  achievementWrapper: AchievementWrapper;

  message: string;

  constructor(
      private httpClient: HttpClient, private dialog: MatDialog,
      private authService: AuthService, private router: Router,
      private userGroupService: UserGroupService,
      private snackBarService: SnackBarService,
      private numAchievementsService: NumAchievementsService) {
    this.groupId = this.userGroupService.userGroupId;
  }

  ngOnInit() {
    this.authService.getUser().subscribe(
        (user) => (this.userId = user.principal.user.id));
    this.isDebug ? this.getTupleWithSelection() : this.getTuple();
    this.numAchievementsService.currentMessage.subscribe(
        message => this.message = message);
  }

  getTuple() {
    this.httpClient
        .get<CheckWrapper>(
            `${environment.url}user_group/${this.groupId}/check-next`)
        .subscribe((value: CheckWrapper) => {
          var res: CheckWrapper = value;
          this.tuple = value.tupleDto;
          this.achievementWrapper = value.achievementWrapper;
          this.numAchievementsService.getNumber();
        });
  }

  openShortcutDialog = () => this.dialog.open(ShortcutComponent, {
    width: '500px',
    disableClose: false,
  });

  onSelectorChange($event) {
    this.selectedTupleType = $event;
    this.isDebug ? this.getTupleWithSelection() : this.getTuple();
  }

  afterCheck($event) {
    this.tuple = null;
    this.isDebug ? this.getTupleWithSelection() : this.getTuple();
  }


  private getTupleWithSelection() {
    const formData = new FormData();
    formData.append(
        `selectedTupleType`, JSON.stringify(this.selectedTupleType));
    this.httpClient
        .post<CheckWrapper>(
            `${environment.url}user_group/${this.groupId}/check-next`, formData)
        .subscribe((value: CheckWrapper) => {
          this.tuple = value.tupleDto;
          this.achievementWrapper = value.achievementWrapper;
        });
  }
}
