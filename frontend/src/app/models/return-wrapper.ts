import { DataElementDto } from "./data-element-dto";
import { TextDto } from "./text-dto";
import { RecordingDto } from "./recording-dto";
import { ImageDto } from "./image-dto";
import { ElementType } from "./element-type";

export class ReturnWrapper {
  dataElementDto: DataElementDto;
  textDto: TextDto;
  recordingDto: RecordingDto;
  imageDto: ImageDto;
  elementType: ElementType;

  constructor(
    $dataElementDto: DataElementDto,
    $textDto: TextDto,
    $recordingDto: RecordingDto,
    $imageDto: ImageDto,
    $elementType: ElementType
  ) {
    this.dataElementDto = $dataElementDto;
    this.textDto = $textDto;
    this.recordingDto = $recordingDto;
    this.imageDto = $imageDto;
    this.elementType = $elementType;
  }
}
