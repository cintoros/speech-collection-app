import { HttpClient } from '@angular/common/http';
import { Component, Input, OnChanges, OnInit } from '@angular/core';
import { TextDto } from 'src/app/models/text-dto';
import { UserGroupService } from 'src/app/services/user-group.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-excerpt-from-data-elem-id',
  templateUrl: './excerpt-from-data-elem-id.component.html',
  styleUrls: ['./excerpt-from-data-elem-id.component.scss']
})
export class ExcerptFromDataElemIdComponent implements OnInit, OnChanges {
  @Input() dataElementId: number;
  groupId: number;
  textDto: TextDto;

  constructor(
      private httpClient: HttpClient,
      private userGroupService: UserGroupService) {
    this.groupId = this.userGroupService.userGroupId;
  }

  ngOnInit(): void {
    this.loadTextDto();
  }

  ngOnChanges(changes) {
    this.loadTextDto();
  }

  private loadTextDto(): void {
    if (!this.dataElementId) {
      return;
    }
    this.httpClient
        .get<TextDto>(`${environment.url}user_group/${this.groupId}/textDto/${
            this.dataElementId}`)
        .subscribe((resp) => {
          this.textDto = resp;
        });
  }
}
