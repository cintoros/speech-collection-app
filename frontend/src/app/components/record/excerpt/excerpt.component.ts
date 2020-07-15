import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-excerpt',
  templateUrl: './excerpt.component.html',
  styleUrls: ['./excerpt.component.scss'],
})
export class ExcerptComponent {
  @Input() excerptText: string;
  @Input() canEdit: boolean;
  @Input() isDeactivated: boolean;
}
