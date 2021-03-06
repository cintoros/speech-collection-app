import { HttpClient } from '@angular/common/http';
import { Component, Input, OnChanges, OnInit } from '@angular/core';
import { Text } from 'src/app/models/text';
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
  textDto: Text;

  constructor(private httpClient: HttpClient, private userGroupService: UserGroupService) {
    this.groupId = this.userGroupService.userGroupId;
  }

  ngOnInit(): void {
    this.loadTextDto();
  }

  ngOnChanges(changes) {
    this.loadTextDto();
  }

  private loadTextDto(): void {
    if (this.dataElementId) {
      this.httpClient.get<Text>(`${environment.url}user_group/${this.groupId}/textDto/${this.dataElementId}`)
          .subscribe(v => this.textDto = v);
    }
  }
}
