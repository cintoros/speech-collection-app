import { Component, Input } from '@angular/core';
import { SeriesDto } from '../../admin/statistics/seriesDto';

@Component({
  selector: 'app-line-chart',
  templateUrl: './line-chart.component.html',
  styleUrls: ['./line-chart.component.scss']
})
export class LineChartComponent {
  @Input() results: SeriesDto[] = [];
  @Input() xAxisLabel = '';
  @Input() yAxisLabel = '';
  @Input() h3 = '';
}
