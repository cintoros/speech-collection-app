import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from '@angular/common/http';
import {NgModule, SecurityContext} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatButtonModule} from '@angular/material/button';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import {MatCardModule} from '@angular/material/card';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatOptionModule} from '@angular/material/core';
import {MatDialogModule} from '@angular/material/dialog';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatListModule} from '@angular/material/list';
import {MatMenuModule} from '@angular/material/menu';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatSelectModule} from '@angular/material/select';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatSliderModule} from '@angular/material/slider';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatSortModule} from '@angular/material/sort';
import {MatTableModule} from '@angular/material/table';
import {MatTabsModule} from '@angular/material/tabs';
import {MatToolbarModule} from '@angular/material/toolbar';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FaIconLibrary, FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {far} from '@fortawesome/free-regular-svg-icons';
import {fas} from '@fortawesome/free-solid-svg-icons';
import {MarkdownModule, MarkdownService} from 'ngx-markdown';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {DocumentOverviewComponent} from './components/admin/document-overview/document-overview.component';
import {GroupAdminComponent} from './components/admin/group-admin/group-admin.component';
import {AdminComponent} from './components/admin/groups-admin/admin.component';
import {EditTextAudioComponent} from './components/admin/overview/edit-text-audio/edit-text-audio.component';
import {OverviewComponent} from './components/admin/overview/overview.component';
import {CheckMoreComponent} from './components/check/check-more/check-more.component';
import {CheckCheckerComponent} from './components/check/check/check-checker/check-checker.component';
import {CheckTextComponent} from './components/check/check/check-text/check-text.component';
import {CheckComponent} from './components/check/check/check.component';
import {ExcerptFromDataElemIdComponent} from './components/check/check/excerpt-from-data-elem-id/excerpt-from-data-elem-id.component';
import {TupleSelectorComponent} from './components/check/check/tuple-selector/tuple-selector.component';
import {ShortcutComponent} from './components/check/shortcut/shortcut.component';
import {GamificationComponent} from './components/gamification/gamification.component';
import {GlyphiconsBatchComponent} from './components/gamification/glyphicons-batch/glyphicons-batch.component';
import {HomeComponent} from './components/home/home.component';
import {LoginComponent} from './components/login/login.component';
import {AvatarComponent} from './components/multi-use/avatar/avatar.component';
import {ProfileEditorComponent} from './components/multi-use/profile-editor/profile-editor.component';
import {UserGroupRoleComponent} from './components/multi-use/user-group-role/user-group-role.component';
import {NavigationMenuComponent} from './components/navigation-menu/navigation-menu.component';
import {ProfileComponent} from './components/profile/profile.component';
import {AudioComponent} from './components/record/audio/audio.component';
import {ElementSelectorComponent} from './components/record/element-selector/element-selector.component';
import {ExcerptComponent} from './components/record/excerpt/excerpt.component';
import {ImageComponent} from './components/record/image/image.component';
import {MicSymbolComponent} from './components/record/mic-symbol/mic-symbol.component';
import {RecordComponent} from './components/record/record.component';
import {RecordingComponent} from './components/record/recording/recording.component';
import {SaveSymbolComponent} from './components/record/save-symbol/save-symbol.component';
import {TranslateComponent} from './components/record/translate/translate.component';
import {TripletSelectorComponent} from './components/record/triplet-selector/triplet-selector.component';
import {VerificationTokenComponent} from './components/verification-token/verification-token.component';
import {AdminGuardService} from './guards/admin-guard.service';
import {AuthGuardService} from './guards/auth-guard.service';
import {GroupAdminGuardService} from './guards/group-admin-guard.service';
import {AuthHeaderInterceptorService} from './services/auth-header-interceptor.service';
import {ErrorInterceptorService} from './services/error-interceptor.service';
import {LoadingInterceptorService} from './services/loading-interceptor.service';
import { BatchComponent } from './components/gamification/batch/batch.component';
import { RecordRandomComponent } from './components/record/record-random/record-random.component';
import { RecordAnimationComponent } from './components/home/record-animation/record-animation.component';
import { CheckAnimationComponent } from './components/home/check-animation/check-animation.component';
import { ProgressbarComponent } from './components/gamification/progressbar/progressbar.component';
import { DailyGoalComponent } from './components/gamification/daily-goal/daily-goal.component';
import { NumNewAchievementsComponent } from './components/gamification/num-new-achievements/num-new-achievements.component';
import { SmallMedalComponent } from './components/gamification/small-medal/small-medal.component';
import { MapComponent } from './components/gamification/map/map.component';
import { TermsComponent } from './components/terms/terms.component';

@NgModule({
  declarations: [
    AppComponent,
    NavigationMenuComponent,
    OverviewComponent,
    ShortcutComponent,
    CheckComponent,
    LoginComponent,
    ProfileComponent,
    AvatarComponent,
    CheckMoreComponent,
    RecordComponent,
    ProfileEditorComponent,
    AdminComponent,
    GroupAdminComponent,
    HomeComponent,
    UserGroupRoleComponent,
    EditTextAudioComponent,
    DocumentOverviewComponent,
    VerificationTokenComponent,
    ExcerptComponent,
    CheckTextComponent,
    SaveSymbolComponent,
    MicSymbolComponent,
    AudioComponent,
    ImageComponent,
    RecordingComponent,
    TranslateComponent,
    TripletSelectorComponent,
    ElementSelectorComponent,
    TupleSelectorComponent,
    ExcerptFromDataElemIdComponent,
    CheckCheckerComponent,
    GamificationComponent,
    GlyphiconsBatchComponent,
    BatchComponent,
    RecordRandomComponent,
    RecordAnimationComponent,
    CheckAnimationComponent,
    ProgressbarComponent,
    DailyGoalComponent,
    NumNewAchievementsComponent,
    SmallMedalComponent,
    MapComponent,
    TermsComponent,
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
    MarkdownModule.forRoot({
      sanitize: SecurityContext.HTML,
    }),
    MatTabsModule,
    MatCheckboxModule,
    FontAwesomeModule,
  ],
  providers: [
    HttpClient,
    AuthGuardService,
    AdminGuardService,
    GroupAdminGuardService,
    MarkdownService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorInterceptorService,
      multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthHeaderInterceptorService,
      multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: LoadingInterceptorService,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
  entryComponents: [ShortcutComponent, CheckMoreComponent],
})
export class AppModule {
  constructor(library: FaIconLibrary) {
    library.addIconPacks(fas, far);
  }
}
