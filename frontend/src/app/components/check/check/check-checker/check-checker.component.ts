import { HttpClient } from '@angular/common/http';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { DataTuple } from 'src/app/models/data-tuple';
import { UserGroupService } from 'src/app/services/user-group.service';
import { environment } from 'src/environments/environment';
import { SnackBarService } from '../../../../services/snack-bar.service';
import { CheckedOccurrence, CheckedOccurrenceLabel } from '../../../../models/checked-occurrence';

@Component({
  selector: 'app-check-checker',
  templateUrl: './check-checker.component.html',
  styleUrls: ['./check-checker.component.scss']
})

export class CheckCheckerComponent {
  @Input() tuple: DataTuple;
  // TODO output is ignored anyway.
  @Output() completed = new EventEmitter<any>();
  groupId: any;

  checkedOccurrenceLabel = CheckedOccurrenceLabel;

  constructor(private httpClient: HttpClient, private userGroupService: UserGroupService, private snackBarService: SnackBarService) {
    this.groupId = this.userGroupService.userGroupId;
  }

  setCheckedType(checkType: CheckedOccurrenceLabel): void {
    if ((checkType === CheckedOccurrenceLabel.SENTENCE_ERROR || checkType === CheckedOccurrenceLabel.PRIVATE)) {
      const url = `${environment.url}user_group/${this.groupId}/element/${this.tuple.dataElementId_1}/checked?type=${checkType}`;
      this.httpClient.post(url, {}).subscribe(value => this.snackBarService.openMessage('data successfully flagged.'));
    } else {
      const cta = new CheckedOccurrence(this.tuple.id, checkType);
      this.httpClient.post(`${environment.url}user_group/${this.groupId}/occurrence/check`, cta)
          // get the next one after we have already marked the old one or else we might get the same one.
          .subscribe(() => this.completed.emit(checkType));
    }
  }
}
