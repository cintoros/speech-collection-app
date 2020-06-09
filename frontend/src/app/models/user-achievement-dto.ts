export class UserAchievementDto {
  id: number;
  user_id: number;
  achievements_id: number;
  points: number;

  constructor(
      $id: number, $user_id: number, $achievements_id: number,
      $points: number) {
    this.id = $id;
    this.user_id = $user_id;
    this.achievements_id = $achievements_id;
    this.points = $points;
  }
}
