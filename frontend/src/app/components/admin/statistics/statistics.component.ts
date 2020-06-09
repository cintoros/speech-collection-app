import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../environments/environment';
import {MatDatepickerInputEvent} from '@angular/material/datepicker';

interface SeriesValueDto {
  name: Date
  value: number
}

interface SeriesDto {
  name: string;
  series: SeriesValueDto[]
}

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.scss']
})
export class StatisticsComponent implements OnInit {
  multi: SeriesDto[] = [];
  myDate: Date;

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

  private reload(date: Date) {
    this.httpClient.get<Array<SeriesDto>>(`${environment.url}admin/statistics?since=${date.toISOString()}`)
      .subscribe(array => {
        console.log(array);
        // we need to convert the json date-string into a javascript string
        array.forEach(v => v.series.forEach(v1 => v1.name = new Date(v1.name)));
        console.log(array);
        this.multi = array;
      });
  }
}
