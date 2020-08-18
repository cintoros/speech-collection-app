import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MapWrapper } from 'src/app/models/map-wrapper';
import { UserGroupService } from 'src/app/services/user-group.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss'],
})
export class MapComponent implements OnInit {
  classes = Array(26).map(() => 'cantonColor_' + 95);
  private scores = Array(26).map(() => 0.0);
  private points = Array(26).map(() => 0.0);
  private countries = ['CH_AG', 'CH_AI', 'CH_AR', 'CH_BL', 'CH_BE', 'CH_BS', 'CH_FR', 'CH_GE', 'CH_GL', 'CH_GR', 'CH_JU',
    'CH_LU', 'CH_NE', 'CH_NW', 'CH_OW', 'CH_SG', 'CH_SH', 'CH_SO', 'CH_SZ', 'CH_TG', 'CH_TI', 'CH_UR', 'CH_VD', 'CH_VS',
    'CH_ZG', 'CH_ZH'];

  constructor(private httpClient: HttpClient, private userGroupService: UserGroupService) {
  }

  ngOnInit(): void {
    this.getCantonsScores();
  }

  getScoreText = (index: number) => this.scores[index] + '% / ' + this.points[index];

  private getCantonsScores(): void {
    this.httpClient
        .get<MapWrapper[]>(`${environment.url}user_group/${this.userGroupService.userGroupId}/map`)
        .subscribe(value => {
          const sum = value.map(v => v.points).reduce((p, c) => p + c);
          value.forEach(value1 => {
            const i = this.countries.indexOf(value1.canton.toString());
            const score = value1.points / sum;
            this.points[i] = value1.points;
            this.scores[i] = Math.floor(score * 100);
            // calculate the color based on the score see the map.component.scss for details on how the coloring works
            // the color ranges from 95(light blue) to 53(dark blue)
            this.classes[i] = 'cantonColor_' + Math.floor(95 - score * (95 - 53));
          });
        });
  }
}
