export class RecordingDto {
  excerptId: number;
  audioQuality: AudioQuality;
  audioNoiseLevel: AudioNoiseLevel;
  browserVersion: string;

  constructor(excerptId: number, quality: AudioQuality, noiseLevel: AudioNoiseLevel, browserVersion: string) {
    this.excerptId = excerptId;
    this.audioQuality = quality;
    this.audioNoiseLevel = noiseLevel;
    this.browserVersion = browserVersion;
  }
}

export enum AudioQuality {
  INTEGRATED = 'INTEGRATED', DEDICATED = 'DEDICATED'
}

export enum AudioNoiseLevel {
  NO_NOISE = 'NO_NOISE', MODERATE_NOISE = 'MODERATE_NOISE', VERY_NOISY = 'VERY_NOISY'
}
