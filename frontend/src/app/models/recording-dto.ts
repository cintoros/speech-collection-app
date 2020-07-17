export class RecordingDto {//TODO does this even represent the backend?
  id: number;
  dialectId: number;
  dataElementId: number;
  path: string;
  quality: RecordingQuality;
  noiseLevel: RecordingNoiseLevel;
  browserVersion: string;
  audioStart: number;
  audioEnd: number;

  constructor(
      $id: number,
      $dialectId: number,
      $dataElementId: number,
      $path: string,
      $quality: RecordingQuality,
      $noiseLevel: RecordingNoiseLevel,
      $browserVersion: string,
      $audioStart: number,
      $audioEnd: number
  ) {
    this.id = $id;
    this.dialectId = $dialectId;
    this.dataElementId = $dataElementId;
    this.path = $path;
    this.quality = $quality;
    this.noiseLevel = $noiseLevel;
    this.browserVersion = $browserVersion;
    this.audioStart = $audioStart;
    this.audioEnd = $audioEnd;
  }
}

export enum RecordingQuality {
  INTEGRATED = 'INTEGRATED',
  DEDICATED = 'DEDICATED',
}

export enum RecordingNoiseLevel {
  NO_NOISE = 'NO_NOISE',
  MODERATE_NOISE = 'MODERATE_NOISE',
  VERY_NOISY = 'VERY_NOISY',
}
