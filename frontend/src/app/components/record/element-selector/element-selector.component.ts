import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ElementType} from 'src/app/models/element-type';

@Component({
  selector: 'app-element-selector',
  templateUrl: './element-selector.component.html',
  styleUrls: ['./element-selector.component.scss']
})
export class ElementSelectorComponent implements OnInit {
  @Input() elementType: ElementType;
  @Output() elementTypeClicked = new EventEmitter<ElementType>();

  image = ElementType.IMAGE;
  text = ElementType.TEXT;
  audio = ElementType.AUDIO;

  constructor() {}

  ngOnInit(): void {}
}
