import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../environments/environment';
import {MatDatepickerInputEvent} from '@angular/material/datepicker';
import {SeriesValueDto} from './seriesValueDto';
import {SeriesDto} from './seriesDto';

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
  /**
   *  convert the json date-string into a javascript date so it can be correctly displayed by the charts library.
   */
  private convertJsonToDate = (v: SeriesDto) => v.series.forEach(v1 => v1.name = new Date(v1.name));

  private reload(date: Date) {
    this.getSeriesDto('basic', date).subscribe(array => {
      array.forEach(v => this.convertJsonToDate(v));
      this.multi = array;
    });
    this.getSeriesDto('audio_duration_statistics', date).subscribe(array => {
      this.convertJsonToDate(array[0]);
      this.multi2 = [array[0]];
      this.single1 = array[1].series;
    });
  }
}
