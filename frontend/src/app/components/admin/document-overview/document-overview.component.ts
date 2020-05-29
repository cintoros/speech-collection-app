import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../environments/environment';
import {UserGroupService} from '../../../services/user-group.service';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort, SortDirection} from '@angular/material/sort';
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
export class DocumentOverviewComponent implements OnInit, AfterViewInit {
  documents: Source[] = [];
  // TODO refactor this so it works with biiiiig documents.
  textElements: Array<TextElementDto>;
  private baseUrl: string;
  @ViewChild(MatPaginator, {static: true}) private paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) private sort: MatSort;
  private domains: Domain[] = [];
  private selectedSource: Source;

  constructor(private httpClient: HttpClient, private userGroupService: UserGroupService) {
  }

  ngOnInit(): void {
    this.baseUrl = `${environment.url}user_group/${this.userGroupService.userGroupId}/admin/source/`;
    this.httpClient.get<Domain[]>(`${environment.url}user_group/${this.userGroupService.userGroupId}/admin/domain`)
      .subscribe(d => this.domains = d);
    this.reload();
  }

  ngAfterViewInit() {
    // If the user changes the sort order, reset back to the first page.
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
  }

  edit(text: Source) {
    this.selectedSource = text;
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          return this.getTextElementDto(
            this.sort.active, this.sort.direction, this.paginator.pageIndex, this.paginator.pageSize);
        }),
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

  private getTextElementDto(active: string, direction: SortDirection, pageIndex: number, pageSize: number) {
    if (active === 'isPrivate') {
      active = 'is_private';
    } else if (active === 'isSentenceError') {
      active = 'is_sentence_error';
    }
    let url = `${this.baseUrl + this.selectedSource.id}/element?pageIndex=${pageIndex}&pageSize=${pageSize}`;
    if (active) {
      url = `${url}&active=${active}`;
    }
    if (direction) {
      url = `${url}&direction=${direction}`;
    }
    return this.httpClient.get<PaginationResultDto<TextElementDto>>(url);
  }

  private reload() {
    this.httpClient.get<Source[]>(this.baseUrl).subscribe(v => this.documents = v);
  }
}
