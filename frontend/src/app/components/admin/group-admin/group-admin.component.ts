import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { Domain } from '../../../models/domain';
import { UserGroupRoleRole } from '../../../models/spring-principal';
import { UserGroupService } from '../../../services/user-group.service';

@Component({
  selector: 'app-group-admin',
  templateUrl: './group-admin.component.html',
  styleUrls: ['./group-admin.component.scss']
})
export class GroupAdminComponent implements OnInit {
  selectedDomain: Domain;
  domains: Domain[];
  user = UserGroupRoleRole.USER;
  ga = UserGroupRoleRole.GROUP_ADMIN;
  documentLicence: string;
  groupDescription = '';
  private baseUrl: string;

  constructor(private httpClient: HttpClient, private userGroupService: UserGroupService) {
    this.userGroupService.getUserGroups()
        .subscribe(v => this.groupDescription = v.find(value => value.id === this.userGroupService.userGroupId).description);
  }

  ngOnInit() {
    this.baseUrl = `${environment.url}user_group/${this.userGroupService.userGroupId}/admin/`;
    this.httpClient.get<Domain[]>(`${this.baseUrl}domain`).subscribe(domains => {
      this.domains = domains;
      this.selectedDomain = domains[0];
    });
    this.documentLicence = localStorage.getItem('documentLicence');
  }

  handleDocumentUpload(fileList: FileList): void {
    const formData = new FormData();
    // tslint:disable-next-line:prefer-for-of
    for (let i = 0; i < fileList.length; i++) {
      formData.append('files', fileList[i], fileList[i].name);
    }
    formData.append('domainId', this.selectedDomain.id.toFixed(0));
    formData.append('documentLicence', this.documentLicence);
    localStorage.setItem('documentLicence', this.documentLicence);
    // do not upload anything in case the input was canceled after a previous upload.
    if (fileList.length > 0) {
      this.httpClient.post(`${this.baseUrl}source/`, formData).subscribe(() => {
      });
    }
  }

  handleImageUpload(fileList: FileList) {
    const formData = new FormData();
    // tslint:disable-next-line:prefer-for-of
    for (let i = 0; i < fileList.length; i++) {
      formData.append('files', fileList[i], fileList[i].name);
    }
    formData.append('domainId', this.selectedDomain.id.toFixed(0));
    formData.append('documentLicence', this.documentLicence);
    localStorage.setItem('documentLicence', this.documentLicence);
    // do not upload anything in case the input was canceled after a previous upload.
    if (fileList.length > 0) {
      this.httpClient.post(`${this.baseUrl}source/image`, formData).subscribe(() => {
      });
    }
  }

  saveGroupDescription() {
    this.httpClient.put(`${this.baseUrl}description`, this.groupDescription).subscribe(() => {
    });
  }
}
