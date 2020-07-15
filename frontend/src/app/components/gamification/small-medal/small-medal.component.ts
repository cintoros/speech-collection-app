import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-small-medal',
  templateUrl: './small-medal.component.html',
  styleUrls: ['./small-medal.component.scss']
})
export class SmallMedalComponent {
  @Input() withRibons = false;
  @Input() message = '';
  @Input() level = 0;

  getClass() {
    if (this.level === 1) {
      return 'quiz-medal__circle--bronze';
    }
    if (this.level === 2) {
      return 'quiz-medal__circle--silver';
    }
    if (this.level === 3) {
      return 'quiz-medal__circle--gold';
    }
    if (this.level === 4) {
      return 'quiz-medal__circle--platin';
    }
    if (this.level === 5) {
      return 'quiz-medal__circle--red';
    }
  }
}
