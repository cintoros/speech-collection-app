import { AchievementWrapper } from './achievement-wrapper';
import { DataElementDto } from './data-element-dto';
import { ElementType } from './element-type';
import { ImageDto } from './image-dto';
import { RecordingDto } from './recording-dto';
import { TextDto } from './text-dto';

// FIXME wtf does this do? see java wrapper
export class ReturnWrapper {
  dataElementDto: DataElementDto;
  textDto: TextDto;
  recordingDto: RecordingDto;
  imageDto: ImageDto;
  elementType: ElementType;
  achievementWrapper: AchievementWrapper;

  constructor(
      $dataElementDto: DataElementDto, $textDto: TextDto,
      $recordingDto: RecordingDto, $imageDto: ImageDto,
      $elementType: ElementType, $achievementWrapper: AchievementWrapper) {
    this.dataElementDto = $dataElementDto;
    this.textDto = $textDto;
    this.recordingDto = $recordingDto;
    this.imageDto = $imageDto;
    this.elementType = $elementType;
    this.achievementWrapper = $achievementWrapper;
  }
}
