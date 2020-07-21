/**
 * NOTE: as this is only used to read the achievements not all fields are defined
 */
export interface AchievementWrapper {
  achievements: Achievements;
  userAchievements: UserAchievements;
  percentOfUsers: number;
  level: number;
}

export class Achievements {
  id: number;
  domainId: number;
  name: string;
  batchName: string;
  title: string;
  startTime: Date;
  endTime: Date;
}

export interface UserAchievements {
  id: number;
  userId: number;
  achievementsId: number;
  points: number;
}
