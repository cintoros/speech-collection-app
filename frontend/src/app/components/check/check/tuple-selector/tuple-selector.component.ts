import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TupleType } from 'src/app/models/data-tuple';

@Component({
  selector: 'app-tuple-selector',
  templateUrl: './tuple-selector.component.html',
  styleUrls: ['./tuple-selector.component.scss']
})
export class TupleSelectorComponent {
  @Input() elementType: TupleType;
  @Output() elementTypeClicked = new EventEmitter<TupleType>();
  textText = TupleType.TEXT_TEXT;
  audioAudio = TupleType.AUDIO_AUDIO;
  textAudio = TupleType.TEXT_AUDIO;
  audioText = TupleType.AUDIO_TEXT;
  imageAudio = TupleType.IMAGE_AUDIO;
  imageText = TupleType.IMAGE_TEXT;
}
