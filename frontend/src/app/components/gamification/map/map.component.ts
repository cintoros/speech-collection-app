import {HttpClient} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {CantonEnum, MapWrapper} from 'src/app/models/map-wrapper';
import {UserGroupService} from 'src/app/services/user-group.service';
import {environment} from 'src/environments/environment';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
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
  groupId: number = 1;

  constructor(
      private httpClient: HttpClient,
      private userGroupService: UserGroupService) {
    this.groupId = this.userGroupService.userGroupId;
  }

  ngOnInit(): void {
    this.getCantonsScores();
  }

  setScores() {
    for (let mapWrapper of this.cantonScore) {
      switch (mapWrapper.canton.toString()) {
        case 'CH_AG':
          this.CH_AG = 'cantonColor_' + this.getScore(mapWrapper.points);
          break;
        case 'CH_AG':
          this.CH_AG = 'cantonColor_' + this.getScore(mapWrapper.points);
          break;
        case 'CH_AI':
          this.CH_AI = 'cantonColor_' + this.getScore(mapWrapper.points);
          break;
        case 'CH_AR':
          this.CH_AR = 'cantonColor_' + this.getScore(mapWrapper.points);
          break;
        case 'CH_BL':
          this.CH_BL = 'cantonColor_' + this.getScore(mapWrapper.points);
          break;
        case 'CH_BE':
          this.CH_BE = 'cantonColor_' + this.getScore(mapWrapper.points);
          break;
        case 'CH_BS':
          this.CH_BS = 'cantonColor_' + this.getScore(mapWrapper.points);
          break;
        case 'CH_FR':
          this.CH_FR = 'cantonColor_' + this.getScore(mapWrapper.points);
          break;
        case 'CH_GE':
          this.CH_GE = 'cantonColor_' + this.getScore(mapWrapper.points);
          break;
        case 'CH_GL':
          this.CH_GL = 'cantonColor_' + this.getScore(mapWrapper.points);
          break;
        case 'CH_GR':
          this.CH_GR = 'cantonColor_' + this.getScore(mapWrapper.points);
          break;
        case 'CH_JU':
          this.CH_JU = 'cantonColor_' + this.getScore(mapWrapper.points);
          break;
        case 'CH_LU':
          this.CH_LU = 'cantonColor_' + this.getScore(mapWrapper.points);
          break;
        case 'CH_NE':
          this.CH_NE = 'cantonColor_' + this.getScore(mapWrapper.points);
          break;
        case 'CH_NW':
          this.CH_NW = 'cantonColor_' + this.getScore(mapWrapper.points);
          break;
        case 'CH_OW':
          this.CH_OW = 'cantonColor_' + this.getScore(mapWrapper.points);
          break;
        case 'CH_SG':
          this.CH_SG = 'cantonColor_' + this.getScore(mapWrapper.points);
          break;
        case 'CH_SH':
          this.CH_SH = 'cantonColor_' + this.getScore(mapWrapper.points);
          break;
        case 'CH_SO':
          this.CH_SO = 'cantonColor_' + this.getScore(mapWrapper.points);
          break;
        case 'CH_SZ':
          this.CH_SZ = 'cantonColor_' + this.getScore(mapWrapper.points);
          break;
        case 'CH_TG':
          this.CH_TG = 'cantonColor_' + this.getScore(mapWrapper.points);
          break;
        case 'CH_TI':
          this.CH_TI = 'cantonColor_' + this.getScore(mapWrapper.points);
          break;
        case 'CH_UR':
          this.CH_UR = 'cantonColor_' + this.getScore(mapWrapper.points);
          break;
        case 'CH_VD':
          this.CH_VD = 'cantonColor_' + this.getScore(mapWrapper.points);
          break;
        case 'CH_VS':
          this.CH_VS = 'cantonColor_' + this.getScore(mapWrapper.points);
          break;
        case 'CH_ZG':
          this.CH_ZG = 'cantonColor_' + this.getScore(mapWrapper.points);
          break;
        case 'CH_ZH':
          this.CH_ZH = 'cantonColor_' + this.getScore(mapWrapper.points);
          break;
      }
    }
  }

  getMaxScore() {
    var max = 0;
    for (let mapWrapper of this.cantonScore) {
      if (max < mapWrapper.points) max = mapWrapper.points;
    }
    this.maxScore = max;
  }

  getScore(points: number): number {
    return Math.floor(
        this.score0percent -
        (points / this.maxScore) * (this.score0percent - this.score100percent));
  }

  getCantonsScores(): void {
    this.httpClient
        .get<MapWrapper[]>(`${environment.url}user_group/${this.groupId}/map`)
        .subscribe((value) => {
          this.cantonScore = value;
          this.getMaxScore();
          this.setScores();
        })
  }
}
