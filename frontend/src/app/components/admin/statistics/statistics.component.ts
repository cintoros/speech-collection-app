import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { environment } from '../../../../environments/environment';
import { SeriesDto } from './seriesDto';
import { SeriesValueDto } from './seriesValueDto';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.scss']
})
export class StatisticsComponent implements OnInit {
  myDate: Date;
  multi: SeriesDto[] = [];
  multi2: SeriesDto[];
  single1: SeriesValueDto[] = [];

  constructor(private httpClient: HttpClient) {
  }

  ngOnInit(): void {
    // load one week by default.
    this.myDate = new Date();
    console.log(this.myDate.getDate());
    this.myDate.setDate(this.myDate.getDate() - 7);
    this.reload(this.myDate);
  }

  changeDate = (event: MatDatepickerInputEvent<Date>) => this.reload(event.value);
  /**
   * needed because the javascript date toISOString may return another day than the selected one.
   */
  private toIsoString = (date: Date) => `${date.getFullYear()}-${this.toIsoNumber(date.getMonth() + 1)}-${this.toIsoNumber(date.getDate())}T00:00:00.000Z`;
  private toIsoNumber = (i: number) => `${i < 10 ? '0' : ''}${i}`;
  private getSeriesDto = (endpoint: string, date: Date) => this.httpClient.get<SeriesDto[]>(`${environment.url}admin/statistics/${endpoint}?since=${this.toIsoString(date)}`);

  private reload(date: Date) {
    this.getSeriesDto('basic', date)
        .subscribe(array => {
          // we need to convert the json date-string into a javascript string
          array.forEach(v => v.series.forEach(v1 => v1.name = new Date(v1.name)));
          this.multi = array;
        });
    this.getSeriesDto('audio_duration_statistics', date)
        .subscribe(array => {
          this.multi2 = [array[0]];
          this.single1 = array[1].series;
        });
  }
}
