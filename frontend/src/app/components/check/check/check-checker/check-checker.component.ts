import { HttpClient } from '@angular/common/http';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CheckedDataTuple, CheckedDataTupleType } from 'src/app/models/checked-data-tuple';
import { CheckedTupleWrapper } from 'src/app/models/checked-tuple-wrapper';
import { TupleDto } from 'src/app/models/tuple-dto';
import { UserGroupService } from 'src/app/services/user-group.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-check-checker',
  templateUrl: './check-checker.component.html',
  styleUrls: ['./check-checker.component.scss']
})

export class CheckCheckerComponent implements OnInit {
  @Input() tuple: TupleDto;
  @Output() completed = new EventEmitter<CheckedDataTupleType>();
  groupId: any;

  correct = CheckedDataTupleType.CORRECT;
  skipped = CheckedDataTupleType.SKIPPED;
  wrong = CheckedDataTupleType.WRONG;

  constructor(
      private httpClient: HttpClient,
      private userGroupService: UserGroupService) {
    this.groupId = this.userGroupService.userGroupId;
  }

  ngOnInit(): void {
  }

  buttonClicked(button: CheckedDataTupleType) {
    const checkedDataTuple: CheckedDataTuple =
        new CheckedDataTuple(-1, -1, this.tuple.id, button);
    const checkedTupleWrapper: CheckedTupleWrapper =
        new CheckedTupleWrapper(this.tuple, checkedDataTuple);

    const formData = new FormData();
    formData.append(`tuple`, JSON.stringify(this.tuple));
    formData.append(`checkedDataTuple`, JSON.stringify(checkedDataTuple));

    this.httpClient
        .post(
            `${environment.url}user_group/${this.groupId}/checked-data-tuple`,
            formData)
        .subscribe(() => {
          this.completed.emit(button);
        });
  }
}
