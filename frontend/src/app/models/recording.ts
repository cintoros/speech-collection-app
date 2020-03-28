export class Recording {
  id: number;
  excerptId: number;
  userId: number;
  audio: Blob;
  time: Date;
  quality: RecordingQuality;
  noiseLevel: RecordingNoiseLevel;

  constructor(id: number, excerptId: number, userId: number, audio: Blob, time: Date, quality: RecordingQuality, noiseLevel: RecordingNoiseLevel) {
    this.id = id;
    this.excerptId = excerptId;
    this.userId = userId;
    this.audio = audio;
    this.time = time;
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
