import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../environments/environment';

interface SeriesValueDto {
  name: string
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

  constructor(private httpClient: HttpClient) {
  }

  ngOnInit(): void {
    const date = new Date('2020-06-01');
    this.httpClient.get<Array<SeriesDto>>(`${environment.url}admin/statistics?since=${date.toISOString()}`)
      .subscribe(array => {
        console.log(array);
        // TODO we need to stretch this in case we have not not enough data
        this.multi = array;
      });
  }
}
