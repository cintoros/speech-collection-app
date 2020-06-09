import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {AudioNoiseLevel, AudioQuality, RecordingDto} from '../../models/recording-dto';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {SnackBarService} from '../../services/snack-bar.service';
import {DomSanitizer, SafeUrl} from '@angular/platform-browser';
import {TextDto} from '../../models/text-dto';
import {UserGroupService} from '../../services/user-group.service';
import {Observable, Subscription, timer} from 'rxjs';
import {CheckedDataElementType} from '../../models/checked-data-element-type';

@Component({
  selector: 'app-record',
  templateUrl: './record.component.html',
  styleUrls: ['./record.component.scss']
})
export class RecordComponent implements OnInit {
  textDto: TextDto = null;
  isRecording = false;
  blobUrl: SafeUrl;
  recordingQuality = AudioQuality;
  recordingNoiseLevel = AudioNoiseLevel;
  selectedQuality = AudioQuality.INTEGRATED;
  selectedNoiseLevel = AudioNoiseLevel.MODERATE_NOISE;
  // @ts-ignore
  private mediaRecorder: MediaRecorder;
  private audioChunks: Blob[] = [];
  private groupId = 1;
  private elapsedTime = 0;
  private numberObservable: Observable<number>;
  private subscription: Subscription;
  private browserVersion: string;

  constructor(
    private snackBarService: SnackBarService, private detector: ChangeDetectorRef, private httpClient: HttpClient,
    private domSanitizer: DomSanitizer, private userGroupService: UserGroupService
  ) {
    this.groupId = this.userGroupService.userGroupId;
  }

  ngOnInit() {
    this.browserVersion = window.navigator.userAgent;
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
    const recording = new RecordingDto(this.textDto.id, this.selectedQuality, this.selectedNoiseLevel, this.browserVersion);
    const formData = new FormData();
    formData.append(`file`, new Blob(this.audioChunks), 'audio');
    formData.append('recording', JSON.stringify(recording));
    this.httpClient.post(`${environment.url}user_group/${this.groupId}/recording`, formData).subscribe(() => {
      this.audioChunks = [];
      this.blobUrl = undefined;
      this.snackBarService.openMessage('Successfully uploaded recording');
      this.getNext();
    });
  }

  private() {
    this.check(CheckedDataElementType.PRIVATE).subscribe(() => {
      this.snackBarService.openMessage('marked as private');
      this.textDto.isPrivate = true;
    });
  }

  skip() {
    this.check(CheckedDataElementType.SKIPPED).subscribe(() => {
      this.snackBarService.openMessage('marked as skipped');
      this.getNext();
    });
  }

  sentenceError() {
    this.check(CheckedDataElementType.SENTENCE_ERROR).subscribe(() => {
      this.snackBarService.openMessage('marked as "Not a sentence"');
      this.getNext();
    });
  }

  isReady = () => this.audioChunks.length > 0;
  private check = (type: CheckedDataElementType) => this.httpClient.post<void>(`${environment.url}user_group/${this.groupId}/element/${this.textDto.id}/checked?type=${type}`, {});

  private getNext() {
    this.httpClient.get<TextDto>(`${environment.url}user_group/${this.groupId}/excerpt`).subscribe(value => this.textDto = value);
  }
}
