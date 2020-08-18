import { Component, Input } from '@angular/core';
import { SeriesValueDto } from '../../admin/statistics/seriesValueDto';

@Component({
  selector: 'app-bar-chart',
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.scss']
})
export class BarChartComponent {
  @Input() results: SeriesValueDto[] = [];
  @Input() xAxisLabel = '';
  @Input() yAxisLabel = '';
  @Input() h3 = '';
}
