import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-triplet-selector',
  templateUrl: './triplet-selector.component.html',
  styleUrls: ['./triplet-selector.component.scss']
})
export class TripletSelectorComponent {
  @Input() isThreeTuple: boolean;
  @Output() threeTupleClicked = new EventEmitter<boolean>();
}
