import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { NumAchievementsService } from 'src/app/services/num-achievements.service';
import { CustomUserDetails, UserGroupRoleRole } from '../../models/spring-principal';
import { AuthService } from '../../services/auth.service';
import { UserGroupService } from '../../services/user-group.service';

@Component({
  selector: 'app-navigation-menu',
  templateUrl: './navigation-menu.component.html',
  styleUrls: ['./navigation-menu.component.scss']
})
export class NavigationMenuComponent implements OnInit {
  @ViewChild('sidenav', {static: true}) sidenav: MatSidenav;
  user: CustomUserDetails;
  message: string;

  constructor(
      public authService: AuthService, public router: Router,
      private userGroupService: UserGroupService,
      private numAchievementsService: NumAchievementsService) {
    authService.getUser().subscribe((user) => {
      this.user = user.principal;
      this.user.gamificationOn = user.principal.user.gamificationOn;
    });
  }

  ngOnInit(): void {
    this.numAchievementsService.currentMessage.subscribe(
        message => this.message = message);
    this.numAchievementsService.getNumber();
  }

  redirectToPage(route: string): void {
    this.router.navigate(['/' + route]);
    this.toggleSidenav();
  }

  isGroupAdmin() {
    return this.user &&
        (this.isAdmin() ||
            this.user.userGroupRoles.find(
                a => a.userGroupId === this.userGroupService.userGroupId &&
                    a.role === UserGroupRoleRole.GROUP_ADMIN));
  }

  toggleSidenav = () => this.sidenav.toggle();
  isAdmin = () => this.user &&
      this.user.userGroupRoles.find(a => a.role === UserGroupRoleRole.ADMIN)
}
