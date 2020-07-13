import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DocumentOverviewComponent} from './components/admin/document-overview/document-overview.component';
import {GroupAdminComponent} from './components/admin/group-admin/group-admin.component';
import {AdminComponent} from './components/admin/groups-admin/admin.component';
import {OverviewComponent} from './components/admin/overview/overview.component';
import {CheckComponent} from './components/check/check/check.component';
import {GamificationComponent} from './components/gamification/gamification.component';
import {HomeComponent} from './components/home/home.component';
import {LoginComponent} from './components/login/login.component';
import {NavigationMenuComponent} from './components/navigation-menu/navigation-menu.component';
import {ProfileComponent} from './components/profile/profile.component';
import {RecordRandomComponent} from './components/record/record-random/record-random.component';
import {TermsComponent} from './components/terms/terms.component';
import {VerificationTokenComponent} from './components/verification-token/verification-token.component';
import {AdminGuardService} from './guards/admin-guard.service';
import {AuthGuardService} from './guards/auth-guard.service';
import {GroupAdminGuardService} from './guards/group-admin-guard.service';
import {StatisticsComponent} from './components/admin/statistics/statistics.component';
import {AboutComponent} from './components/about/about.component';

const routes: Routes = [
  {
    path: 'admin',
    component: NavigationMenuComponent,
    canActivate: [AuthGuardService],
    children: [
      {path: 'user_group', canActivate: [GroupAdminGuardService], component: GroupAdminComponent},
      {path: 'admin', canActivate: [AdminGuardService], component: AdminComponent},
      {path: 'statistics', canActivate: [AdminGuardService], component: StatisticsComponent},
      {path: 'overview', canActivate: [GroupAdminGuardService], component: OverviewComponent},
      {path: 'document_overview', canActivate: [GroupAdminGuardService], component: DocumentOverviewComponent},
    ]
  },
  {
    path: '',
    component: NavigationMenuComponent,
    canActivate: [AuthGuardService],
    children: [
      {path: 'home', component: HomeComponent},
      {path: 'check', component: CheckComponent},
      {path: 'record', component: RecordRandomComponent},
      {path: 'profile', component: ProfileComponent},
      {path: 'gamification', component: GamificationComponent},
      {path: 'terms', component: TermsComponent},
      {path: 'about', component: AboutComponent},
    ]
  },
  {path: 'login', component: LoginComponent},
  {path: 'token', component: VerificationTokenComponent},
  {path: '**', redirectTo: '/home'}
];

@NgModule({imports: [RouterModule.forRoot(routes)], exports: [RouterModule]})
export class AppRoutingModule {
}
