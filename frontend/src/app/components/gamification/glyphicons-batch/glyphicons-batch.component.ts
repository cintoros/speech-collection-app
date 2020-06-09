import {Component, Input, OnInit} from '@angular/core';
import {faCoffee} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-glyphicons-batch',
  templateUrl: './glyphicons-batch.component.html',
  styleUrls: ['./glyphicons-batch.component.scss']
})
export class GlyphiconsBatchComponent implements OnInit {
  @Input() glyph: string;
  @Input() level: number;
  @Input() text: string;

  color: string;
  color2: string;
  color3: string;


  constructor() {}

  ngOnInit(): void {
    this.getColor();
  }

  ngOnChanges(): void {
    this.getColor();
  }

  getColor() {
    switch (this.level) {
      case 0:
        this.color = 'bronze';
        this.color2 = 'bronzeB';
        this.color3 = 'bronzeD';

      case 1:
        this.color = 'bronze';
        this.color2 = 'bronzeB';
        this.color3 = 'bronzeD';
        break;
      case 2:
        this.color = 'silver';
        this.color2 = 'silverB';
        this.color3 = 'silverD';
        break;
      case 3:
        this.color = 'gold';
        this.color2 = 'goldB';
        this.color3 = 'goldD';
        break;
      case 4:
        this.color = 'green';
        this.color2 = 'greenB';
        this.color3 = 'greenD';
        break;
    }
  }
}
