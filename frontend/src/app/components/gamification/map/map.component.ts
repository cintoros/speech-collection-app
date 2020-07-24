import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { CantonEnum, MapWrapper } from 'src/app/models/map-wrapper';
import { UserGroupService } from 'src/app/services/user-group.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss'],
})
export class MapComponent implements OnInit {
  scores = Array(26).map(() => 0.0);
  private score100percent = 53;
  private score0percent = 95;
  classes = Array(26).map(() => 'cantonColor_' + this.score0percent);
  private countries = ['CH_AG', 'CH_AI', 'CH_AR', 'CH_BL', 'CH_BE', 'CH_BS', 'CH_FR', 'CH_GE', 'CH_GL', 'CH_GR', 'CH_JU',
    'CH_LU', 'CH_NE', 'CH_NW', 'CH_OW', 'CH_SG', 'CH_SH', 'CH_SO', 'CH_SZ', 'CH_TG', 'CH_TI', 'CH_UR', 'CH_VD', 'CH_VS',
    'CH_ZG', 'CH_ZH'];
  // TODO not sure how this weights were calculated?
  private countryWeights = [0.023521021174315457, 0.2909330350699957, 1.0, 0.05572486904120192, 0.08237180960553266,
    0.015520146742231986, 0.0501279074466068, 0.03199604786213547, 0.3947664741016352, 0.08104508814601885, 0.2192270570803257,
    0.0390438883616432, 0.09138819155989505, 0.374384808245891, 0.42524522729669867, 0.19586577115998882, 0.10050667780152939,
    0.05860591546531869, 0.3158008106996691, 0.04588429770877256, 0.05770090843062259, 0.4394996457186461, 0.02003700017767043,
    0.04669160437066075, 0.12637526251449707, 0.010479917444737882];

  constructor(private httpClient: HttpClient, private userGroupService: UserGroupService) {
  }

  ngOnInit(): void {
    this.getCantonsScores();
  }

  private getCountyWeight = (county: CantonEnum) => this.countryWeights[this.countries.indexOf(county.toString())];

  private getCantonsScores(): void {
    this.httpClient
        .get<MapWrapper[]>(`${environment.url}user_group/${this.userGroupService.userGroupId}/map`)
        .subscribe(value => {
          const s = value.map(v => v.points * this.getCountyWeight(v.canton));
          const maxScore = Math.max(...s);
          value.forEach(value1 => {
            const i = this.countries.indexOf(value1.canton.toString());
            this.scores[i] = Math.floor(this.score0percent - ((value1.points * this.countryWeights[i]) / maxScore)
                * (this.score0percent - this.score100percent));
            this.classes[i] = 'cantonColor_' + this.scores[i];
          });
        });
  }
}
