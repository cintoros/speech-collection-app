import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-small-medal',
  templateUrl: './small-medal.component.html',
  styleUrls: ['./small-medal.component.scss']
})
export class SmallMedalComponent implements OnInit {
  @Input() withRibons: Boolean = false;
  @Input() message: String = '';
  @Input() level: number = 0;

  constructor() {
  }

  ngOnInit(): void {
  }

  getClass() {
    if (this.level == 1) {
      return 'quiz-medal__circle--bronze';
    }
    if (this.level == 2) {
      return 'quiz-medal__circle--silver';
    }
    if (this.level == 3) {
      return 'quiz-medal__circle--gold';
    }
    if (this.level == 4) {
      return 'quiz-medal__circle--platin';
    }
    if (this.level == 5) {
      return 'quiz-medal__circle--red';
    }
  }
}
