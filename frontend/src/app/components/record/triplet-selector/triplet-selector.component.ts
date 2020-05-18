import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-triplet-selector',
  templateUrl: './triplet-selector.component.html',
  styleUrls: ['./triplet-selector.component.scss']
})
export class TripletSelectorComponent implements OnInit {
  @Input() isThreeTuple: boolean;
  @Output() threeTupleClicked = new EventEmitter<boolean>();

  constructor() {}

  ngOnInit(): void {}
}
