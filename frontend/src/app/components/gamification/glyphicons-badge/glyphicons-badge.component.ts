import { Component, Input, OnChanges, OnInit } from '@angular/core';

@Component({
  selector: 'app-glyphicons-badge',
  templateUrl: './glyphicons-badge.component.html',
  styleUrls: ['./glyphicons-badge.component.scss']
})
export class GlyphiconsBadgeComponent implements OnInit, OnChanges {
  @Input() glyph: string;
  @Input() level: number;
  @Input() text: string;
  color = 'bronze';
  color2 = 'bronzeB';
  color3 = 'bronzeD';
  private colors = ['bronze', 'bronze', 'silver', 'gold', 'green'];

  ngOnInit(): void {
    this.setColors();
  }

  ngOnChanges(): void {
    this.setColors();
  }

  private setColors() {
    this.color = this.colors[this.level];
    this.color2 = `${this.color}B`;
    this.color3 = `${this.color}D`;
  }
}
