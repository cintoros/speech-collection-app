import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {TupleType} from 'src/app/models/tuple-dto';

@Component({
  selector: 'app-tuple-selector',
  templateUrl: './tuple-selector.component.html',
  styleUrls: ['./tuple-selector.component.scss']
})
export class TupleSelectorComponent implements OnInit {
  @Input() elementType: TupleType;
  @Output() elementTypeClicked = new EventEmitter<TupleType>();

  text_text = TupleType.TEXT_TEXT;
  audio_audio = TupleType.AUDIO_AUDIO;
  text_audio = TupleType.TEXT_AUDIO;
  audio_text = TupleType.AUDIO_TEXT;
  image_audio = TupleType.IMAGE_AUDIO;
  image_text = TupleType.IMAGE_TEXT;

  constructor() {}

  ngOnInit(): void {}
}
