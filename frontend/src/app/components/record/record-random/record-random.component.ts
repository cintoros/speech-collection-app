import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-record-random',
  templateUrl: './record-random.component.html',
  styleUrls: ['./record-random.component.scss']
})
export class RecordRandomComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}

  selection = ElementType.TEXT_OR_IMAGE;
}
import {ElementType} from 'src/app/models/element-type';
