export class MapWrapper {
  canton: CantonEnum;
  points: number;

  constructor($canton: CantonEnum, $points: number) {
    this.canton = $canton;
    this.points = $points;
  }
}

export enum CantonEnum {
  CH_AG = 'CH-AG',
  CH_AI = 'CH-AI',
  CH_AR = 'CH-AR',
  CH_BL = 'CH-BL',
  CH_BE = 'CH-BE',
  CH_BS = 'CH-BS',
  CH_FR = 'CH-FR',
  CH_GE = 'CH-GE',
  CH_GL = 'CH-GL',
  CH_GR = 'CH-GR',
  CH_JU = 'CH-JU',
  CH_LU = 'CH-LU',
  CH_NE = 'CH-NE',
  CH_NW = 'CH-NW',
  CH_OW = 'CH-OW',
  CH_SG = 'CH-SG',
  CH_SH = 'CH-SH',
  CH_SO = 'CH-SO',
  CH_SZ = 'CH-SZ',
  CH_TG = 'CH-TG',
  CH_TI = 'CH-TI',
  CH_UR = 'CH-UR',
  CH_VD = 'CH-VD',
  CH_VS = 'CH-VS',
  CH_ZG = 'CH-ZG',
  CH_ZH = 'CH-ZH',
}
