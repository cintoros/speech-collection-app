import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Recording} from '../../models/recording';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {SnackBarService} from '../../services/snack-bar.service';
import {DomSanitizer, SafeUrl} from '@angular/platform-browser';
import {Excerpt} from '../../models/excerpt';
import {UserGroupService} from '../../services/user-group.service';
import {Observable, Subscription, timer} from 'rxjs';

@Component({
  selector: 'app-record',
  templateUrl: './record.component.html',
  styleUrls: ['./record.component.scss']
})
export class RecordComponent implements OnInit {
  excerpt: Excerpt = null;
  isRecording = false;
  blobUrl: SafeUrl;
  // @ts-ignore
  private mediaRecorder: MediaRecorder;
  private audioChunks = [];
  private groupId = 1;
  private elapsedTime = 0;
  private numberObservable: Observable<number>;
  private subscription: Subscription;

  constructor(
    private snackBarService: SnackBarService, private detector: ChangeDetectorRef, private httpClient: HttpClient,
    private domSanitizer: DomSanitizer, private userGroupService: UserGroupService
  ) {
    this.groupId = this.userGroupService.userGroupId;
  }

  ngOnInit() {
    this.getNext();
    navigator.mediaDevices.getUserMedia({audio: true})
      .then(stream => {
        // @ts-ignore
        this.mediaRecorder = new MediaRecorder(stream, {mimeType: 'audio/webm'});
        this.mediaRecorder.ondataavailable = event => this.audioChunks.push(event.data);
        this.mediaRecorder.onstop = () => {
          this.blobUrl = this.domSanitizer.bypassSecurityTrustUrl(URL.createObjectURL(new Blob(this.audioChunks)));
          this.detector.detectChanges();
        };
      });
    this.numberObservable = timer(0, 1000);
  }

  startRecord(): void {
    this.audioChunks = [];
    this.mediaRecorder.start();
    this.elapsedTime = 0;
    this.isRecording = true;
    this.subscription = this.numberObservable.subscribe(value => this.elapsedTime = value);
  }

  stopRecord(): void {
    this.subscription.unsubscribe();
    this.mediaRecorder.stop();
    this.isRecording = false;
  }

  submit(): void {
    const recording = new Recording(undefined, this.excerpt.id, undefined, undefined, undefined);
    const formData = new FormData();
    formData.append(`file`, new Blob(this.audioChunks), 'audio');
    formData.append('excerptId', recording.excerptId + '');
    this.httpClient.post(`${environment.url}user_group/${this.groupId}/recording`, formData).subscribe(() => {
      this.audioChunks = [];
      this.blobUrl = undefined;
      this.snackBarService.openMessage('Successfully uploaded recording');
      this.getNext();
    });
  }

  private() {
    this.httpClient.put<Excerpt>(`${environment.url}user_group/${this.groupId}/excerpt/${this.excerpt.id}/private`, {})
      .subscribe(() => {
        this.snackBarService.openMessage('marked as private');
        this.excerpt.isPrivate = true;
      });
  }

  skip() {
    this.httpClient.put<Excerpt>(`${environment.url}user_group/${this.groupId}/excerpt/${this.excerpt.id}/skipped`, {})
      .subscribe(() => {
        this.snackBarService.openMessage('marked as skipped');
        this.getNext();
      });
  }

  isReady = () => this.audioChunks.length > 0;

  sentenceError() {
    this.httpClient.put<Excerpt>(`${environment.url}user_group/${this.groupId}/excerpt/${this.excerpt.id}/sentence_error`, {})
      .subscribe(() => {
        this.snackBarService.openMessage('marked as "Not a sentence"');
        this.getNext();
      });
  }

  private getNext() {
    this.httpClient.get<Excerpt>(`${environment.url}user_group/${this.groupId}/excerpt`).subscribe(value => this.excerpt = value);
  }
}
