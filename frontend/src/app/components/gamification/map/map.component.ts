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
  cantonScore: MapWrapper[];
  maxScore: number;

  score100percent = 53;
  score0percent = 95;

  CH_AG = 'cantonColor_' + this.score0percent;
  CH_AI = 'cantonColor_' + this.score0percent;
  CH_AR = 'cantonColor_' + this.score0percent;
  CH_BL = 'cantonColor_' + this.score0percent;
  CH_BE = 'cantonColor_' + this.score0percent;
  CH_BS = 'cantonColor_' + this.score0percent;
  CH_FR = 'cantonColor_' + this.score0percent;
  CH_GE = 'cantonColor_' + this.score0percent;
  CH_GL = 'cantonColor_' + this.score0percent;
  CH_GR = 'cantonColor_' + this.score0percent;
  CH_JU = 'cantonColor_' + this.score0percent;
  CH_LU = 'cantonColor_' + this.score0percent;
  CH_NE = 'cantonColor_' + this.score0percent;
  CH_NW = 'cantonColor_' + this.score0percent;
  CH_OW = 'cantonColor_' + this.score0percent;
  CH_SG = 'cantonColor_' + this.score0percent;
  CH_SH = 'cantonColor_' + this.score0percent;
  CH_SO = 'cantonColor_' + this.score0percent;
  CH_SZ = 'cantonColor_' + this.score0percent;
  CH_TG = 'cantonColor_' + this.score0percent;
  CH_TI = 'cantonColor_' + this.score0percent;
  CH_UR = 'cantonColor_' + this.score0percent;
  CH_VD = 'cantonColor_' + this.score0percent;
  CH_VS = 'cantonColor_' + this.score0percent;
  CH_ZG = 'cantonColor_' + this.score0percent;
  CH_ZH = 'cantonColor_' + this.score0percent;
  groupId = 1;

  constructor(
      private httpClient: HttpClient,
      private userGroupService: UserGroupService
  ) {
    this.groupId = this.userGroupService.userGroupId;
  }

  ngOnInit(): void {
    this.getCantonsScores();
  }

  setScores() {
    for (const mapWrapper of this.cantonScore) {
      switch (mapWrapper.canton.toString()) {
        case 'CH_AG':
          this.CH_AG =
              'cantonColor_' +
              this.getScore(
                  mapWrapper.points,
                  this.getCountyWeight(mapWrapper.canton)
              );
          break;
        case 'CH_AG':
          this.CH_AG =
              'cantonColor_' +
              this.getScore(
                  mapWrapper.points,
                  this.getCountyWeight(mapWrapper.canton)
              );
          break;
        case 'CH_AI':
          this.CH_AI =
              'cantonColor_' +
              this.getScore(
                  mapWrapper.points,
                  this.getCountyWeight(mapWrapper.canton)
              );
          break;
        case 'CH_AR':
          this.CH_AR =
              'cantonColor_' +
              this.getScore(
                  mapWrapper.points,
                  this.getCountyWeight(mapWrapper.canton)
              );
          break;
        case 'CH_BL':
          this.CH_BL =
              'cantonColor_' +
              this.getScore(
                  mapWrapper.points,
                  this.getCountyWeight(mapWrapper.canton)
              );
          break;
        case 'CH_BE':
          this.CH_BE =
              'cantonColor_' +
              this.getScore(
                  mapWrapper.points,
                  this.getCountyWeight(mapWrapper.canton)
              );
          break;
        case 'CH_BS':
          this.CH_BS =
              'cantonColor_' +
              this.getScore(
                  mapWrapper.points,
                  this.getCountyWeight(mapWrapper.canton)
              );
          break;
        case 'CH_FR':
          this.CH_FR =
              'cantonColor_' +
              this.getScore(
                  mapWrapper.points,
                  this.getCountyWeight(mapWrapper.canton)
              );
          break;
        case 'CH_GE':
          this.CH_GE =
              'cantonColor_' +
              this.getScore(
                  mapWrapper.points,
                  this.getCountyWeight(mapWrapper.canton)
              );
          break;
        case 'CH_GL':
          this.CH_GL =
              'cantonColor_' +
              this.getScore(
                  mapWrapper.points,
                  this.getCountyWeight(mapWrapper.canton)
              );
          break;
        case 'CH_GR':
          this.CH_GR =
              'cantonColor_' +
              this.getScore(
                  mapWrapper.points,
                  this.getCountyWeight(mapWrapper.canton)
              );
          break;
        case 'CH_JU':
          this.CH_JU =
              'cantonColor_' +
              this.getScore(
                  mapWrapper.points,
                  this.getCountyWeight(mapWrapper.canton)
              );
          break;
        case 'CH_LU':
          this.CH_LU =
              'cantonColor_' +
              this.getScore(
                  mapWrapper.points,
                  this.getCountyWeight(mapWrapper.canton)
              );
          break;
        case 'CH_NE':
          this.CH_NE =
              'cantonColor_' +
              this.getScore(
                  mapWrapper.points,
                  this.getCountyWeight(mapWrapper.canton)
              );
          break;
        case 'CH_NW':
          this.CH_NW =
              'cantonColor_' +
              this.getScore(
                  mapWrapper.points,
                  this.getCountyWeight(mapWrapper.canton)
              );
          break;
        case 'CH_OW':
          this.CH_OW =
              'cantonColor_' +
              this.getScore(
                  mapWrapper.points,
                  this.getCountyWeight(mapWrapper.canton)
              );
          break;
        case 'CH_SG':
          this.CH_SG =
              'cantonColor_' +
              this.getScore(
                  mapWrapper.points,
                  this.getCountyWeight(mapWrapper.canton)
              );
          break;
        case 'CH_SH':
          this.CH_SH =
              'cantonColor_' +
              this.getScore(
                  mapWrapper.points,
                  this.getCountyWeight(mapWrapper.canton)
              );
          break;
        case 'CH_SO':
          this.CH_SO =
              'cantonColor_' +
              this.getScore(
                  mapWrapper.points,
                  this.getCountyWeight(mapWrapper.canton)
              );
          break;
        case 'CH_SZ':
          this.CH_SZ =
              'cantonColor_' +
              this.getScore(
                  mapWrapper.points,
                  this.getCountyWeight(mapWrapper.canton)
              );
          break;
        case 'CH_TG':
          this.CH_TG =
              'cantonColor_' +
              this.getScore(
                  mapWrapper.points,
                  this.getCountyWeight(mapWrapper.canton)
              );
          break;
        case 'CH_TI':
          this.CH_TI =
              'cantonColor_' +
              this.getScore(
                  mapWrapper.points,
                  this.getCountyWeight(mapWrapper.canton)
              );
          break;
        case 'CH_UR':
          this.CH_UR =
              'cantonColor_' +
              this.getScore(
                  mapWrapper.points,
                  this.getCountyWeight(mapWrapper.canton)
              );
          break;
        case 'CH_VD':
          this.CH_VD =
              'cantonColor_' +
              this.getScore(
                  mapWrapper.points,
                  this.getCountyWeight(mapWrapper.canton)
              );
          break;
        case 'CH_VS':
          this.CH_VS =
              'cantonColor_' +
              this.getScore(
                  mapWrapper.points,
                  this.getCountyWeight(mapWrapper.canton)
              );
          break;
        case 'CH_ZG':
          this.CH_ZG =
              'cantonColor_' +
              this.getScore(
                  mapWrapper.points,
                  this.getCountyWeight(mapWrapper.canton)
              );
          break;
        case 'CH_ZH':
          this.CH_ZH =
              'cantonColor_' +
              this.getScore(
                  mapWrapper.points,
                  this.getCountyWeight(mapWrapper.canton)
              );
          break;
      }
    }
  }

  getMaxScore() {
    let max = 0;
    for (const mapWrapper of this.cantonScore) {
      if (max < mapWrapper.points * this.getCountyWeight(mapWrapper.canton)) {
        max = mapWrapper.points * this.getCountyWeight(mapWrapper.canton);
      }
    }
    this.maxScore = max;
  }

  getScore(points: number, weight: number): number {
    const pts = points * weight;
    return Math.floor(
        this.score0percent -
        (pts / this.maxScore) * (this.score0percent - this.score100percent)
    );
  }

  getCountyWeight(county: CantonEnum): number {
    switch (county.toString()) {
      case 'CH_AG':
        return 0.023521021174315457;
      case 'CH_AI':
        return 0.2909330350699957;
      case 'CH_AR':
        return 1.0;
      case 'CH_BL':
        return 0.05572486904120192;
      case 'CH_BE':
        return 0.08237180960553266;
      case 'CH_BS':
        return 0.015520146742231986;
      case 'CH_FR':
        return 0.0501279074466068;
      case 'CH_GE':
        return 0.03199604786213547;
      case 'CH_GL':
        return 0.3947664741016352;
      case 'CH_GR':
        return 0.08104508814601885;
      case 'CH_JU':
        return 0.2192270570803257;
      case 'CH_LU':
        return 0.0390438883616432;
      case 'CH_NE':
        return 0.09138819155989505;
      case 'CH_NW':
        return 0.374384808245891;
      case 'CH_OW':
        return 0.42524522729669867;
      case 'CH_SG':
        return 0.19586577115998882;
      case 'CH_SH':
        return 0.10050667780152939;
      case 'CH_SO':
        return 0.05860591546531869;
      case 'CH_SZ':
        return 0.3158008106996691;
      case 'CH_TG':
        return 0.04588429770877256;
      case 'CH_TI':
        return 0.05770090843062259;
      case 'CH_UR':
        return 0.4394996457186461;
      case 'CH_VD':
        return 0.02003700017767043;
      case 'CH_VS':
        return 0.04669160437066075;
      case 'CH_ZG':
        return 0.12637526251449707;
      case 'CH_ZH':
        return 0.010479917444737882;
    }
  }

  getCantonsScores(): void {
    this.httpClient
        .get<MapWrapper[]>(`${environment.url}user_group/${this.groupId}/map`)
        .subscribe((value) => {
          this.cantonScore = value;
          this.getMaxScore();
          this.setScores();
        });
  }
}
