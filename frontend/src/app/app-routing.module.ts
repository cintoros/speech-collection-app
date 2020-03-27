import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {AuthGuardService} from './guards/auth-guard.service';
import {ProfileComponent} from './components/profile/profile.component';
import {OverviewComponent} from './components/admin/overview/overview.component';
import {LoginComponent} from './components/login/login.component';
import {RecordComponent} from './components/record/record.component';
import {NavigationMenuComponent} from './components/navigation-menu/navigation-menu.component';
import {GroupAdminComponent} from './components/admin/group-admin/group-admin.component';
import {AdminComponent} from './components/admin/groups-admin/admin.component';
import {HomeComponent} from './components/home/home.component';
import {AdminGuardService} from './guards/admin-guard.service';
import {GroupAdminGuardService} from './guards/group-admin-guard.service';
import {CheckComponent} from './components/check/check/check.component';

const routes: Routes = [
  {
    path: 'admin',
    component: NavigationMenuComponent,
    canActivate: [AuthGuardService],
    children: [
      {
        path: 'user_group',
        canActivate: [GroupAdminGuardService],
        component: GroupAdminComponent,
      },
      {
        path: 'admin',
        canActivate: [AdminGuardService],
        component: AdminComponent,
      },
      {
        path: 'overview',
        canActivate: [GroupAdminGuardService],
        component: OverviewComponent,
      },
    ]
  },
  {
    path: '',
    component: NavigationMenuComponent,
    canActivate: [AuthGuardService],
    children: [
      {
        path: 'home',
        component: HomeComponent,
      },
      {
        path: 'check',
        component: CheckComponent,
      },
      {
        path: 'record',
        component: RecordComponent,
      },
      {
        path: 'profile',
        component: ProfileComponent,
      }
    ]
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: '**',
    redirectTo: '/overview'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
