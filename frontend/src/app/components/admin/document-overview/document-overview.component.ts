import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../environments/environment';
import {UserGroupService} from '../../../services/user-group.service';
import {MatPaginator, PageEvent} from '@angular/material/paginator';
import {Domain} from '../../../models/domain';
import {merge, of as observableOf} from 'rxjs';
import {catchError, map, startWith, switchMap} from 'rxjs/operators';

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

class PaginationResultDto<T> {
  items: Array<T>;
  totalCount: number;

  constructor(items: Array<T>, totalCount: number) {
    this.items = items;
    this.totalCount = totalCount;
  }
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
  textElements: Array<TextElementDto>;
  private baseUrl: string;
  @ViewChild(MatPaginator, {static: true}) private paginator: MatPaginator;
  private domains: Domain[] = [];
  private selectedSource: Source;
  private lastBefore: boolean;

  constructor(private httpClient: HttpClient, private userGroupService: UserGroupService) {
  }

  ngOnInit(): void {
    this.baseUrl = `${environment.url}user_group/${this.userGroupService.userGroupId}/admin/source/`;
    this.httpClient.get<Domain[]>(`${environment.url}user_group/${this.userGroupService.userGroupId}/admin/domain`)
      .subscribe(d => this.domains = d);
    this.reload();
  }

  edit(text: Source) {
    this.selectedSource = text;
    const pe = new PageEvent();
    pe.pageIndex = 0;
    merge(this.paginator.page)
      .pipe(
        startWith(pe),
        switchMap((value: PageEvent) => this.loadFromRestPE(value)),
        map(data => {
          this.paginator.length = data.totalCount;
          return data.items;
        }),
        catchError(() => {
          return observableOf([]);
        })
      ).subscribe(data => this.textElements = data);
  }

  deleteTextElement(text: TextElementDto) {
    if (confirm('are you sure you want to delete this element?')) {
      this.httpClient.delete<TextElementDto[]>(`${this.baseUrl + text.sourceId}/element/${text.id}`)
        .subscribe(() => this.textElements = this.textElements.filter(v => v.id !== text.id));
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

  private loadFromRestPE(pageEvent: PageEvent) {
    if (pageEvent.pageIndex === 0) {
      return this.loadFirstPage();
    } else if (pageEvent.pageIndex >= Math.floor(pageEvent.length / pageEvent.pageSize)) {
      return this.loadLastPage();
    } else {
      const before = pageEvent.previousPageIndex > pageEvent.pageIndex;
      //this hack is needed becase of https://github.com/jOOQ/jOOQ/issues/6380
      const key = (before && !this.lastBefore) ? this.textElements[0] : this.textElements[pageEvent.pageSize - 1];
      return this.loadFromRest(this.paginator.pageSize, key.id, before);
    }
  }

  private loadFirstPage() {
    return this.loadFromRest(this.paginator.pageSize, 0, false);
  }

  private loadLastPage() {
    return this.loadFromRest(this.paginator.pageSize, 0x7ffffffffffffff, true);
  }

  private loadFromRest(pageSize: number, lastId: number, before: boolean) {
    this.lastBefore = before;
    const url = `${this.baseUrl + this.selectedSource.id}/element?lastId=${lastId}&pageSize=${pageSize}&before=${before}`;
    return this.httpClient.get<PaginationResultDto<TextElementDto>>(url);
  }

  private reload() {
    this.httpClient.get<Source[]>(this.baseUrl).subscribe(v => this.documents = v);
  }
}
