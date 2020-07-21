export interface AchievementWrapper {
  achievements: Achievements;
  userAchievements: UserAchievements;
  percentOfUsers: number;
}

export interface Achievements {
  id: number;
  domainId: number;
  name: Text;
  batchName: Text;
  title: Text;
  startTime: Date;
  endTime: Date;
  pointsLvl1: number;
  pointsLvl2: number;
  pointsLvl3: number;
  pointsLvl4: number;
  descriptionLvl1: Text;
  descriptionLvl2: Text;
  descriptionLvl3: Text;
  descriptionLvl4: Text;
  dependsOn: DependsOnType;
}

export enum DependsOnType {
  TEXT_CREATED = 'TEXT_CREATED',
  AUDIO_CREATED = 'AUDIO_CREATED',
  IMAGE_CREATED = 'IMAGE_CREATED',
  TOTAL_CREATED = 'TOTAL_CREATED',
  TEXT_TEXT_CHECKED = 'TEXT_TEXT_CHECKED',
  AUDIO_AUDIO_CHECKED = 'AUDIO_AUDIO_CHECKED',
  TEXT_AUDIO_CHECKED = 'TEXT_AUDIO_CHECKED',
  AUDIO_TEXT_CHECKED = 'AUDIO_TEXT_CHECKED',
  IMAGE_AUDIO_CHECKED = 'IMAGE_AUDIO_CHECKED',
  IMAGE_TEXT_CHECKED = 'IMAGE_TEXT_CHECKED',
  TOTAL_CHECKED = 'TOTAL_CHECKED',
  ALL = 'ALL',
  MANUAL = 'MANUAL',
}

export interface UserAchievements {
  id: number;
  userId: number;
  achievementsId: number;
  points: number;
}
