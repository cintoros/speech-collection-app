import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ElementType } from 'src/app/models/element-type';

@Component({
  selector: 'app-element-selector',
  templateUrl: './element-selector.component.html',
  styleUrls: ['./element-selector.component.scss']
})
export class ElementSelectorComponent {
  @Input() elementType: ElementType;
  @Output() elementTypeClicked = new EventEmitter<ElementType>();
  image = ElementType.IMAGE;
  text = ElementType.TEXT;
  audio = ElementType.AUDIO;
}
