import {Component, OnInit} from '@angular/core';
import {UserGroupService} from '../../services/user-group.service';
import {UserGroup} from '../../models/user-group';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {SeriesValueDto} from '../admin/statistics/seriesValueDto';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  userGroups: UserGroup[] = [];
  counts: SeriesValueDto[];
  single1: SeriesValueDto[];
  single2: SeriesValueDto[];

  constructor(public userGroupService: UserGroupService, private httpClient: HttpClient) {
  }

  ngOnInit(): void {
    this.userGroupService.getUserGroups().subscribe(v => this.userGroups = v);
    this.reload();
  }

  getSelectedGroupDescription() {
    const userGroup = this.userGroups.find(value => value.id === this.userGroupService.userGroupId);
    return userGroup ? userGroup.description : undefined;
  }

  private reload() {
    this.httpClient.get<SeriesValueDto[]>(`${environment.url}statistics/cumulative_counts`).subscribe(v => this.counts = v);
    this.httpClient.get<SeriesValueDto[][]>(`${environment.url}statistics/top_3_user`).subscribe(v => {
      this.single1 = v[0];
      this.single2 = v[1];
    });
  }
}
