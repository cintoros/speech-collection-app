export class AchievementDto {
  id: number;
  domain_id: number;
  name: Text;
  batch_name: Text;
  description: Text;
  start_time: Date;
  end_time: Date;
  points_lvl1: number;
  points_lvl2: number;
  points_lvl3: number;
  points_lvl4: number;
  depends_on: DependsOnType;


  constructor(
      $id: number, $domain_id: number, $name: Text, $batch_name: Text,
      $description: Text, $start_time: Date, $end_time: Date,
      $points_lvl1: number, $points_lvl2: number, $points_lvl3: number,
      $points_lvl4: number, $depends_on: DependsOnType) {
    this.id = $id;
    this.domain_id = $domain_id;
    this.name = $name;
    this.batch_name = $batch_name;
    this.description = $description;
    this.start_time = $start_time;
    this.end_time = $end_time;
    this.points_lvl1 = $points_lvl1;
    this.points_lvl2 = $points_lvl2;
    this.points_lvl3 = $points_lvl3;
    this.points_lvl4 = $points_lvl4;
    this.depends_on = $depends_on;
  }
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
