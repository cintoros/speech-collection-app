import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../environments/environment';
import {UserGroupService} from '../../../services/user-group.service';
import {Text} from '../../../models/text';
import {MatTableDataSource} from '@angular/material/table';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {Domain} from '../../../models/domain';

interface Source {
  id: number;
  userId: number;
  dialectID: number;
  domainId: number;
  userGroupId: number;
  createdTime: Date;
  licence: string;
  name: string;
}

@Component({
  selector: 'app-document-overview',
  templateUrl: './document-overview.component.html',
  styleUrls: ['./document-overview.component.scss']
})
export class DocumentOverviewComponent implements OnInit {
  documents: Source[] = [];
  //TODO add/refactor dto
  excerpts: MatTableDataSource<Text>;
  private baseUrl: string;
  @ViewChild(MatPaginator, {static: true}) private paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) private sort: MatSort;
  private domains: Domain[] = [];

  constructor(private httpClient: HttpClient, private userGroupService: UserGroupService) {
  }

  ngOnInit(): void {
    this.baseUrl = `${environment.url}user_group/${this.userGroupService.userGroupId}/admin/original_text/`;
    this.httpClient.get<Domain[]>(`${environment.url}user_group/${this.userGroupService.userGroupId}/admin/domain`)
      .subscribe(d => this.domains = d);
    this.reload();
  }

  edit(text: Source) {
    this.httpClient.get<Text[]>(`${this.baseUrl + text.id}/excerpt`).subscribe(v => {
      this.excerpts = new MatTableDataSource(v);
      this.excerpts.paginator = this.paginator;
      this.excerpts.sort = this.sort;
    });
  }

  deleteE(text: Text) {
    if (confirm('are you sure you want to delete this element?')) {
      this.httpClient.delete<Text[]>(`${this.baseUrl + text.originalTextId}/excerpt/${text.id}`)
        .subscribe(() => this.excerpts.data = this.excerpts.data.filter(v => v.id !== text.id));
    }
  }

  delete(text: Source) {
    if (confirm('are you sure you want to delete this element?')) {
      this.httpClient.delete<Source[]>(this.baseUrl + text.id)
        .subscribe(() => this.documents = this.documents.filter(v => v.id !== text.id));
    }
  }

  findDomain = (id: number) => this.domains.find(value => value.id === id);
  back = () => this.excerpts = undefined;

  private reload() {
    this.httpClient.get<Source[]>(this.baseUrl).subscribe(v => this.documents = v);
  }
}
