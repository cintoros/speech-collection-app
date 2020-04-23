import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../environments/environment';
import {UserGroupService} from '../../../services/user-group.service';
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

interface TextElementDto {
  id: number;
  sourceId: number;
  skipped: number;
  isPrivate: boolean;
  isSentenceError: boolean;
  text: string;
}

@Component({
  selector: 'app-document-overview',
  templateUrl: './document-overview.component.html',
  styleUrls: ['./document-overview.component.scss']
})
export class DocumentOverviewComponent implements OnInit {
  documents: Source[] = [];
  textElements: MatTableDataSource<TextElementDto>;
  private baseUrl: string;
  @ViewChild(MatPaginator, {static: true}) private paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) private sort: MatSort;
  private domains: Domain[] = [];

  constructor(private httpClient: HttpClient, private userGroupService: UserGroupService) {
  }

  ngOnInit(): void {
    this.baseUrl = `${environment.url}user_group/${this.userGroupService.userGroupId}/admin/source/`;
    this.httpClient.get<Domain[]>(`${environment.url}user_group/${this.userGroupService.userGroupId}/admin/domain`)
      .subscribe(d => this.domains = d);
    this.reload();
  }

  edit(text: Source) {
    this.httpClient.get<TextElementDto[]>(`${this.baseUrl + text.id}/element`).subscribe(v => {
      this.textElements = new MatTableDataSource(v);
      this.textElements.paginator = this.paginator;
      this.textElements.sort = this.sort;
    });
  }

  deleteTextElement(text: TextElementDto) {
    if (confirm('are you sure you want to delete this element?')) {
      this.httpClient.delete<TextElementDto[]>(`${this.baseUrl + text.sourceId}/element/${text.id}`)
        .subscribe(() => this.textElements.data = this.textElements.data.filter(v => v.id !== text.id));
    }
  }

  deleteDocument(source: Source) {
    if (confirm('are you sure you want to delete this element?')) {
      this.httpClient.delete<Source[]>(this.baseUrl + source.id)
        .subscribe(() => this.documents = this.documents.filter(v => v.id !== source.id));
    }
  }

  findDomain = (id: number) => this.domains.find(value => value.id === id);
  back = () => this.textElements = undefined;

  private reload() {
    this.httpClient.get<Source[]>(this.baseUrl).subscribe(v => this.documents = v);
  }
}
