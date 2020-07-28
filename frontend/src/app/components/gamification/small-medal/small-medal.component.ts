import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-small-medal',
  templateUrl: './small-medal.component.html',
  styleUrls: ['./small-medal.component.scss']
})
export class SmallMedalComponent {
  @Input() withRibons = false;
  @Input() level = 0;
  private classes = [undefined, 'bronze', 'silver', 'gold', 'platin', 'red'];

  getClass() {
    return this.level === 0 ? undefined : `quiz-medal__circle--${this.classes[this.level]}`;
  }
}
