import { AchievementWrapper } from './achievement-wrapper';
import { DataElement } from './data-element';
import { ElementType } from './element-type';
import { Image } from './image';
import { RecordingDto } from './recording-dto';
import { Text } from './text';

// TODO what is even used of these?
export class ReturnWrapper {
  dataElement: DataElement;
  text: Text;
  // TODO is this even used? see audio
  recordingDto: RecordingDto;
  image: Image;
  elementType: ElementType;
  achievementWrapper: AchievementWrapper;
}
