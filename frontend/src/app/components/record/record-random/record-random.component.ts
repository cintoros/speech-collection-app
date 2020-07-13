import { Component, OnInit } from '@angular/core';
import { ElementType } from 'src/app/models/element-type';

@Component({
  selector: 'app-record-random',
  templateUrl: './record-random.component.html',
  styleUrls: ['./record-random.component.scss']
})
export class RecordRandomComponent implements OnInit {
  selection = ElementType.TEXT_OR_IMAGE;

  constructor() {
  }

  ngOnInit(): void {
  }
}

