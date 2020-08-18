import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { AchievementWrapper } from '../../../models/achievement-wrapper';
import { AuthService } from '../../../services/auth.service';
import { UserGroupService } from '../../../services/user-group.service';
import { SeriesValueDto } from '../../admin/statistics/seriesValueDto';

@Component({
  selector: 'app-public-stats',
  templateUrl: './public-stats.component.html',
  styleUrls: ['./public-stats.component.scss']
})
export class PublicStatsComponent implements OnInit {
  activeAchievementWrapper: AchievementWrapper;
  counts: SeriesValueDto[];
  single1: SeriesValueDto[];
  single2: SeriesValueDto[];
  gamificationOn: boolean;

  constructor(public authService: AuthService, private httpClient: HttpClient, public userGroupService: UserGroupService) {
    authService.getUser().subscribe(user => {
      this.gamificationOn = user.principal.user.gamificationOn;
    });
    this.httpClient.get<SeriesValueDto[]>(`${environment.url}statistics/cumulative_counts`).subscribe(v => this.counts = v);
    this.httpClient.get<SeriesValueDto[][]>(`${environment.url}statistics/top_3_user`).subscribe(v => {
      this.single1 = v[0];
      this.single2 = v[1];
    });
  }

  ngOnInit(): void {
    this.httpClient
        .get<AchievementWrapper>(`${environment.url}user_group/${this.userGroupService.userGroupId}/achievement/active`)
        .subscribe(value => this.activeAchievementWrapper = value);
  }

}
