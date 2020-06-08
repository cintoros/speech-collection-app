import {Component, HostListener, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {AuthService} from '../../../services/auth.service';
import {ShortcutComponent} from '../shortcut/shortcut.component';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../environments/environment';
import {CheckedOccurrence, CheckedOccurrenceLabel, Occurrence} from './checked-occurrence';
import {Router} from '@angular/router';
import {UserGroupService} from '../../../services/user-group.service';
import {SnackBarService} from '../../../services/snack-bar.service';
import {DomSanitizer, SafeUrl} from '@angular/platform-browser';

export enum OccurrenceMode {
  RECORDING = 'RECORDING', TEXT_AUDIO = 'TEXT_AUDIO'
}

@Component({
  selector: 'app-check',
  templateUrl: './check.component.html',
  styleUrls: ['./check.component.scss']
})
export class CheckComponent implements OnInit {
  isPlaying = false;
  checkedOccurrenceLabel = CheckedOccurrenceLabel;
  occurrence: Occurrence;
  blobUrl: SafeUrl;
  private isReady = false;
  private userId: number;
  private groupId = 1;

  constructor(
    private httpClient: HttpClient, private dialog: MatDialog, private authService: AuthService, private router: Router,
    private userGroupService: UserGroupService, private snackBarService: SnackBarService, private domSanitizer: DomSanitizer
  ) {
    this.groupId = this.userGroupService.userGroupId;
  }

  ngOnInit() {
    this.authService.getUser().subscribe(user => this.userId = user.principal.user.id);
    this.getNextLabeledTextAudio();
  }

  @HostListener('window:keyup', ['$event'])
  keyEvent(event: KeyboardEvent) {
    if (event.key === 'p') {
      this.togglePlay();
    } else if (event.key === 'c') {
      this.setCheckedType(CheckedOccurrenceLabel.CORRECT);
    } else if (event.key === 'w') {
      this.setCheckedType(CheckedOccurrenceLabel.WRONG);
    } else if (event.key === 's') {
      this.setCheckedType(CheckedOccurrenceLabel.SKIPPED);
    }
  }

  /**
   * set the checked type and prepare the next carousel
   */
  setCheckedType(checkType: CheckedOccurrenceLabel): void {
    // only trigger this method if the user has played the audio at least once to prevent accidental button presses
    if (this.isReady) {
      this.stop();
      if ((checkType === CheckedOccurrenceLabel.SENTENCE_ERROR || checkType === CheckedOccurrenceLabel.PRIVATE)) {
        this.httpClient.post(`${environment.url}user_group/${this.groupId}/element/${this.occurrence.dataElementId_1}/checked?type=${checkType}`, {})
          .subscribe(value => this.snackBarService.openMessage('successfully updated element.'));
      } else {
        const cta = new CheckedOccurrence(this.occurrence.id, checkType);
        this.httpClient.post(`${environment.url}user_group/${this.groupId}/occurrence/check`, cta)
          // get the next one after we have already marked the old one or else we might get the same one.
          .subscribe(() => this.getNextLabeledTextAudio());
      }
    }
  }

  togglePlay() {
    this.isReady = true;
    if (this.isPlaying) {
      this.stop();
    } else {
      this.play();
    }
  }

  openShortcutDialog = () => this.dialog.open(ShortcutComponent, {width: '500px', disableClose: false});

  private audioPlayer = (): HTMLAudioElement => (document.getElementById('htmlAudioPlayer') as HTMLAudioElement);

  private play() {
    this.audioPlayer().play();
    this.isPlaying = true;
  }

  private stop() {
    this.audioPlayer().pause();
    this.audioPlayer().currentTime = 0;
    this.isPlaying = false;
  }

  private getNextLabeledTextAudio() {
    this.httpClient.get<Occurrence>(`${environment.url}user_group/${this.groupId}/occurrence/next`)
      .subscribe(occurrence => {
        this.occurrence = occurrence;
        if (occurrence) {
          this.isReady = false;
          this.httpClient.get(`${environment.url}user_group/${this.groupId}/occurrence/audio/${occurrence.dataElementId_2}`, {responseType: 'blob'})
            .subscribe(resp => {
              this.blobUrl = this.domSanitizer.bypassSecurityTrustUrl(URL.createObjectURL(resp));
              this.audioPlayer().onplaying = () => this.isReady = true;
              this.audioPlayer().onended = () => this.isPlaying = false;
            });
        }
      });
  }
}
