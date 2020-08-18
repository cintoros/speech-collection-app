import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { CustomUserDetails } from 'src/app/models/spring-principal';
import { AuthService } from 'src/app/services/auth.service';
import { UserGroup } from '../../models/user-group';
import { UserGroupService } from '../../services/user-group.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  user: CustomUserDetails;
  userGroups: UserGroup[] = [];
  private groupId = 1;

  constructor(
      public authService: AuthService, private httpClient: HttpClient, public userGroupService: UserGroupService) {
    this.groupId = this.userGroupService.userGroupId;
    authService.getUser().subscribe(user => this.user = user.principal);
  }

  ngOnInit(): void {
    this.userGroupService.getUserGroups().subscribe(v => this.userGroups = v);
  }

  getSelectedGroupDescription() {
    const userGroup = this.userGroups.find(value => value.id === this.userGroupService.userGroupId);
    return userGroup ? userGroup.description : undefined;
  }
}
