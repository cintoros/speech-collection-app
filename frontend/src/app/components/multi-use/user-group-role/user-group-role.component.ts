import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { UserGroupRoleRole } from '../../../models/spring-principal';
import { SnackBarService } from '../../../services/snack-bar.service';
import { UserGroupService } from '../../../services/user-group.service';

interface UserGroupRoleDto {
  username: string;
  email: string;
  id: number;
  gamificationOn: boolean;
}

@Component({
  selector: 'app-user-group-role',
  templateUrl: './user-group-role.component.html',
  styleUrls: ['./user-group-role.component.scss']
})
export class UserGroupRoleComponent implements OnInit {
  @Input() mode: UserGroupRoleRole;
  userGroupRoles: UserGroupRoleDto[] = [];
  columns = ['username', 'email', 'game', 'change-game', 'remove'];
  userEmail: string;
  private baseUrl: string;

  constructor(
      private httpClient: HttpClient,
      private userGroupService: UserGroupService,
      private snackBarService: SnackBarService) {
  }

  ngOnInit(): void {
    this.baseUrl = `${environment.url}user_group/${
        this.userGroupService.userGroupId}/admin/user_group_role?mode=${
        this.mode}`;
    this.reload();
  }

  reload() {
    this.httpClient.get<UserGroupRoleDto[]>(this.baseUrl)
        .subscribe(v => this.userGroupRoles = v);
  }

  remove(id: number) {
    this.httpClient.delete<boolean>(`${this.baseUrl}&id=${id}`)
        .subscribe(() => {
          this.snackBarService.openMessage('successfully removed permission');
          this.reload();
        });
  }

  changeGame(userName: string) {
    this.httpClient
        .get(`${environment.url}user_group/${
            this.userGroupService
                .userGroupId}/admin/user_group_role/changeGame/${userName}`)
        .subscribe(() => {
          this.snackBarService.openMessage('successfully changed gamification');
          this.reload();
        });
  }


  newUserGroupRole = () => this.userEmail = 'email/username';
  cancel = () => this.userEmail = undefined;

  save() {
    this.httpClient
        .post<boolean>(`${this.baseUrl}&email=${this.userEmail}`, null)
        .subscribe(v => {
          if (v) {
            this.snackBarService.openMessage(
                'successfully updated permissions');
            this.reload();
            this.cancel();
          } else {
            this.snackBarService.openError('user not found');
          }
        });
  }
}
