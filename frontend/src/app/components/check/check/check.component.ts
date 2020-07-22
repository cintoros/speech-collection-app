import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AchievementWrapper } from 'src/app/models/achievement-wrapper';
import { CheckWrapper } from 'src/app/models/check-wrapper';
import { DataTuple } from 'src/app/models/data-tuple';
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
  tuple: DataTuple;
  achievementWrapper: AchievementWrapper;
  checkedOccurrenceLabel = CheckedOccurrenceLabel;
  gamificationOn = false;
  private groupId = 1;

  constructor(
      private httpClient: HttpClient, private authService: AuthService, private userGroupService: UserGroupService,
      private snackBarService: SnackBarService, private numAchievementsService: NumAchievementsService) {
    this.groupId = this.userGroupService.userGroupId;
    authService.getUser().subscribe(user => this.gamificationOn = user.principal.user.gamificationOn);
  }

  ngOnInit() {
    this.checkNext();
  }

  checkNext() {
    this.tuple = null;
    this.httpClient.get<CheckWrapper>(`${environment.url}user_group/${this.groupId}/check-next`)
        .subscribe(value => {
          this.tuple = value.dataTuple;
          this.achievementWrapper = value.achievementWrapper;
          this.numAchievementsService.getNumber();
        });
  }

  /**
   * set the checked type and prepare the next carousel
   */
  setCheckedType(checkType: CheckedOccurrenceLabel): void {
    // only trigger this method if the user has played the audio at least once to prevent accidental button presses
    if ((checkType === CheckedOccurrenceLabel.SENTENCE_ERROR || checkType === CheckedOccurrenceLabel.PRIVATE)) {
      const url = `${environment.url}user_group/${this.groupId}/element/${this.tuple.dataElementId_1}/checked?type=${checkType}`;
      this.httpClient.post(url, {})
          .subscribe(value => this.snackBarService.openMessage('data successfully flagged.'));
    } else {
      const cta = new CheckedOccurrence(this.tuple.id, checkType);
      this.httpClient.post(`${environment.url}user_group/${this.groupId}/occurrence/check`, cta)
          // get the next one after we have already marked the old one or else we might get the same one.
          .subscribe(() => this.checkNext());
    }
  }
}
