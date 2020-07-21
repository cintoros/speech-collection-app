import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { AchievementWrapper } from 'src/app/models/achievement-wrapper';
import { CheckWrapper } from 'src/app/models/check-wrapper';
import { CustomUserDetails } from 'src/app/models/spring-principal';
import { TupleDto, TupleType } from 'src/app/models/tuple-dto';
import { NumAchievementsService } from 'src/app/services/num-achievements.service';
import { environment } from '../../../../environments/environment';
import { CheckedOccurrence, CheckedOccurrenceLabel } from '../../../models/checked-occurrence';
import { AuthService } from '../../../services/auth.service';
import { SnackBarService } from '../../../services/snack-bar.service';
import { UserGroupService } from '../../../services/user-group.service';

@Component({
  selector: 'app-check',
  templateUrl: './check.component.html',
  styleUrls: ['./check.component.scss'],
})
export class CheckComponent implements OnInit {
  selectedTupleType = TupleType.TEXT_TEXT;
  tuple: TupleDto;
  isDebug = false;
  achievementWrapper: AchievementWrapper;
  message: string;
  user: CustomUserDetails;
  checkedOccurrenceLabel = CheckedOccurrenceLabel;
  private userId: number;
  private groupId = 1;

  constructor(
      private httpClient: HttpClient, private dialog: MatDialog,
      private authService: AuthService, private router: Router,
      private userGroupService: UserGroupService,
      private snackBarService: SnackBarService,
      private numAchievementsService: NumAchievementsService) {
    this.groupId = this.userGroupService.userGroupId;
    authService.getUser().subscribe(user => {
      this.user = user.principal;
      this.user.gamificationOn = user.principal.user.gamificationOn;
    });
  }

  ngOnInit() {
    this.authService.getUser().subscribe(
        (user) => (this.userId = user.principal.user.id));
    this.isDebug ? this.getTupleWithSelection() : this.getTuple();
    this.numAchievementsService.currentMessage.subscribe(message => this.message = message);
  }

  getTuple() {
    this.httpClient.get<CheckWrapper>(`${environment.url}user_group/${this.groupId}/check-next`)
        .subscribe(value => {
          this.tuple = value.tupleDto;
          this.achievementWrapper = value.achievementWrapper;
          this.numAchievementsService.getNumber();
        });
  }

  onSelectorChange(tupleType: TupleType) {
    this.selectedTupleType = tupleType;
    this.isDebug ? this.getTupleWithSelection() : this.getTuple();
  }

  afterCheck() {
    this.tuple = null;
    this.isDebug ? this.getTupleWithSelection() : this.getTuple();
  }

  /**
   * set the checked type and prepare the next carousel
   */
  setCheckedType(checkType: CheckedOccurrenceLabel): void {
    // only trigger this method if the user has played the audio at least once to prevent accidental button presses
    if ((checkType === CheckedOccurrenceLabel.SENTENCE_ERROR || checkType === CheckedOccurrenceLabel.PRIVATE)) {
      const url = `${environment.url}user_group/${this.groupId}/element/${this.tuple.data_element_id_1}/checked?type=${checkType}`;
      this.httpClient.post(url, {})
          .subscribe(value => this.snackBarService.openMessage('data successfully flagged.'));
    } else {
      const cta = new CheckedOccurrence(this.tuple.id, checkType);
      this.httpClient.post(`${environment.url}user_group/${this.groupId}/occurrence/check`, cta)
          // get the next one after we have already marked the old one or else we might get the same one.
          .subscribe(() => this.afterCheck());
    }
  }

  private getTupleWithSelection() {
    const formData = new FormData();
    formData.append(`selectedTupleType`, JSON.stringify(this.selectedTupleType));
    this.httpClient.post<CheckWrapper>(`${environment.url}user_group/${this.groupId}/check-next`, formData)
        .subscribe(value => {
          this.tuple = value.tupleDto;
          this.achievementWrapper = value.achievementWrapper;
        });
  }
}
