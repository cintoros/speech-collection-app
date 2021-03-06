import { HTTP_INTERCEPTORS, HttpClient, HttpClientModule } from '@angular/common/http';
import { NgModule, SecurityContext } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatNativeDateModule, MatOptionModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDialogModule } from '@angular/material/dialog';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSelectModule } from '@angular/material/select';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatSliderModule } from '@angular/material/slider';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FaIconLibrary, FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { far } from '@fortawesome/free-regular-svg-icons';
import { fas } from '@fortawesome/free-solid-svg-icons';
import { BarChartModule, LineChartModule } from '@swimlane/ngx-charts';
import { MarkdownModule, MarkdownService } from 'ngx-markdown';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AboutComponent } from './components/about/about.component';
import { DocumentOverviewComponent } from './components/admin/document-overview/document-overview.component';
import { GroupAdminComponent } from './components/admin/group-admin/group-admin.component';
import { AdminComponent } from './components/admin/groups-admin/admin.component';
import { EditTextAudioComponent } from './components/admin/overview/edit-text-audio/edit-text-audio.component';
import { OverviewComponent } from './components/admin/overview/overview.component';
import { StatisticsComponent } from './components/admin/statistics/statistics.component';
import { CheckCheckerComponent } from './components/check/check/check-checker/check-checker.component';
import { CheckComponent } from './components/check/check/check.component';
import { ExcerptFromDataElemIdComponent } from './components/check/check/excerpt-from-data-elem-id/excerpt-from-data-elem-id.component';
import { ShortcutComponent } from './components/check/shortcut/shortcut.component';
import { BadgeComponent } from './components/gamification/badge/badge.component';
import { DailyGoalComponent } from './components/gamification/daily-goal/daily-goal.component';
import { GamificationComponent } from './components/gamification/gamification.component';
import { GlyphiconsBadgeComponent } from './components/gamification/glyphicons-badge/glyphicons-badge.component';
import { MapComponent } from './components/gamification/map/map.component';
import { ProgressbarComponent } from './components/gamification/progressbar/progressbar.component';
import { SmallMedalComponent } from './components/gamification/small-medal/small-medal.component';
import { CheckAnimationComponent } from './components/home/check-animation/check-animation.component';
import { HomeComponent } from './components/home/home.component';
import { RecordAnimationComponent } from './components/home/record-animation/record-animation.component';
import { LoginComponent } from './components/login/login.component';
import { BarChartComponent } from './components/multi-use/bar-chart/bar-chart.component';
import { LineChartComponent } from './components/multi-use/line-chart/line-chart.component';
import { ProfileEditorComponent } from './components/multi-use/profile-editor/profile-editor.component';
import { PublicStatsComponent } from './components/multi-use/public-stats/public-stats.component';
import { UserGroupRoleComponent } from './components/multi-use/user-group-role/user-group-role.component';
import { NavigationMenuComponent } from './components/navigation-menu/navigation-menu.component';
import { ProfileComponent } from './components/profile/profile.component';
import { AudioComponent } from './components/record/audio/audio.component';
import { ExcerptComponent } from './components/record/excerpt/excerpt.component';
import { ImageComponent } from './components/record/image/image.component';
import { MicSymbolComponent } from './components/record/mic-symbol/mic-symbol.component';
import { RecordComponent } from './components/record/record.component';
import { RecordingComponent } from './components/record/recording/recording.component';
import { SaveSymbolComponent } from './components/record/save-symbol/save-symbol.component';
import { TranslateComponent } from './components/record/translate/translate.component';
import { VerificationTokenComponent } from './components/verification-token/verification-token.component';
import { AdminGuardService } from './guards/admin-guard.service';
import { AuthGuardService } from './guards/auth-guard.service';
import { GroupAdminGuardService } from './guards/group-admin-guard.service';
import { AuthHeaderInterceptorService } from './services/auth-header-interceptor.service';
import { ErrorInterceptorService } from './services/error-interceptor.service';
import { LoadingInterceptorService } from './services/loading-interceptor.service';
import { LogosComponent } from './components/multi-use/logos/logos.component';

@NgModule({
  declarations: [
    AppComponent,
    NavigationMenuComponent,
    OverviewComponent,
    ShortcutComponent,
    CheckComponent,
    LoginComponent,
    ProfileComponent,
    RecordComponent,
    ProfileEditorComponent,
    AdminComponent,
    GroupAdminComponent,
    HomeComponent,
    UserGroupRoleComponent,
    EditTextAudioComponent,
    DocumentOverviewComponent,
    VerificationTokenComponent,
    StatisticsComponent,
    AboutComponent,
    VerificationTokenComponent,
    ExcerptComponent,
    SaveSymbolComponent,
    MicSymbolComponent,
    AudioComponent,
    ImageComponent,
    RecordingComponent,
    TranslateComponent,
    ExcerptFromDataElemIdComponent,
    CheckCheckerComponent,
    GamificationComponent,
    GlyphiconsBadgeComponent,
    BadgeComponent,
    RecordAnimationComponent,
    CheckAnimationComponent,
    ProgressbarComponent,
    DailyGoalComponent,
    SmallMedalComponent,
    MapComponent,
    LineChartComponent,
    BarChartComponent,
    PublicStatsComponent,
    LogosComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    MatCardModule,
    MatButtonToggleModule,
    MatIconModule,
    MatButtonModule,
    MatSlideToggleModule,
    MatGridListModule,
    MatTableModule,
    MatPaginatorModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatExpansionModule,
    MatListModule,
    MatToolbarModule,
    MatFormFieldModule,
    MatInputModule,
    MatMenuModule,
    MatSidenavModule,
    MatSliderModule,
    MatOptionModule,
    MatSelectModule,
    MatSnackBarModule,
    MatDialogModule,
    MatSortModule,
    MarkdownModule.forRoot({sanitize: SecurityContext.HTML}),
    MatTabsModule,
    MatCheckboxModule,
    LineChartModule,
    MatDatepickerModule,
    MatNativeDateModule,
    BarChartModule,
    FontAwesomeModule,
  ],
  providers: [
    HttpClient,
    AuthGuardService,
    AdminGuardService,
    GroupAdminGuardService,
    MarkdownService,
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptorService, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: AuthHeaderInterceptorService, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: LoadingInterceptorService, multi: true},
  ],
  bootstrap: [AppComponent],
  entryComponents: [ShortcutComponent],
})
export class AppModule {
  constructor(library: FaIconLibrary) {
    library.addIconPacks(fas, far);
  }
}
