import {ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../environments/environment';
import {OccurrenceMode} from '../../check/check/check.component';
import {UserGroupService} from '../../../services/user-group.service';
import {OverviewOccurrence} from './overview-occurrence';

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.scss']
})
export class OverviewComponent implements OnInit {
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;
  dataSource = new MatTableDataSource<OverviewOccurrence | { id: number, text: string, username: string, time: string }>();
  columns = ['text', 'correct', 'wrong', 'options'];
  occurrenceMode = OccurrenceMode;
  selectedOverviewOccurrence: OverviewOccurrence;
  private audioPlayer = new Audio();
  private baseUrl: string;

  constructor(private det: ChangeDetectorRef, private httpClient: HttpClient, private userGroupService: UserGroupService) {
  }

  ngOnInit() {
    this.baseUrl = `${environment.url}user_group/${this.userGroupService.userGroupId}/`;
    this.dataSource = new MatTableDataSource<OverviewOccurrence>([]);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.reload();
  }

  reload() {
    this.getTextAudios().subscribe(textAudio => {
      this.dataSource.data = textAudio;
      this.selectedOverviewOccurrence = undefined;
    });
  }

  edit(occurrence: OverviewOccurrence) {
    this.selectedOverviewOccurrence = JSON.parse(JSON.stringify(occurrence));
  }

  play(occurrence: OverviewOccurrence) {
    this.httpClient.get(`${this.baseUrl}occurrence/audio/${occurrence.dataElementId_2}`, {responseType: 'blob'})
      .subscribe(resp => {
        this.audioPlayer.pause();
        this.audioPlayer = new Audio(URL.createObjectURL(resp));
        this.audioPlayer.play();
      });
  }

  private getTextAudios = () => this.httpClient.get<OverviewOccurrence[]>(`${this.baseUrl}admin/overview_occurrence`);
}
