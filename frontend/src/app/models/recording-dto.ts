export class RecordingDto {
  excerptId: number;
  quality: RecordingQuality;
  noiseLevel: RecordingNoiseLevel;

  constructor(excerptId: number, quality: RecordingQuality, noiseLevel: RecordingNoiseLevel) {
    this.excerptId = excerptId;
    this.quality = quality;
    this.noiseLevel = noiseLevel;
  }
}

export enum RecordingQuality {
  INTEGRATED = 'INTEGRATED', DEDICATED = 'DEDICATED'
}

export enum RecordingNoiseLevel {
  NO_NOISE = 'NO_NOISE', MODERATE_NOISE = 'MODERATE_NOISE', VERY_NOISY = 'VERY_NOISY'
}
