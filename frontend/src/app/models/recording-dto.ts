export class RecordingDto {
  audioQuality: AudioQuality;
  audioNoiseLevel: AudioNoiseLevel;
  browserVersion: string;

  constructor(audioQuality: AudioQuality, audioNoiseLevel: AudioNoiseLevel, browserVersion: string) {
    this.audioQuality = audioQuality;
    this.audioNoiseLevel = audioNoiseLevel;
    this.browserVersion = browserVersion;
  }
}

export enum AudioQuality {
  INTEGRATED = 'INTEGRATED',
  DEDICATED = 'DEDICATED',
}

export enum AudioNoiseLevel {
  NO_NOISE = 'NO_NOISE',
  MODERATE_NOISE = 'MODERATE_NOISE',
  VERY_NOISY = 'VERY_NOISY',
}
