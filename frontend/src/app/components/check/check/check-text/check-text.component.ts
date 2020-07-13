import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-check-text',
  templateUrl: './check-text.component.html',
  styleUrls: ['./check-text.component.scss'],
})
export class CheckTextComponent implements OnInit {
  @Input() excerpt_text: string;

  constructor() {
  }

  ngOnInit(): void {
  }
}
