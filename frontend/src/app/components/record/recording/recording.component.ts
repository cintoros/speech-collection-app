import { HttpClient } from '@angular/common/http';
import { ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { Observable, Subscription, timer } from 'rxjs';
import { DataElement } from 'src/app/models/data-element';
import { ElementType } from 'src/app/models/element-type';
import { AudioNoiseLevel, AudioQuality, RecordingDto } from 'src/app/models/recording-dto';
import { UserGroupService } from 'src/app/services/user-group.service';
import { environment } from 'src/environments/environment';
import { CheckedDataElementType } from '../../../models/checked-data-element-type';
import { SnackBarService } from '../../../services/snack-bar.service';

@Component({
  selector: 'app-recording',
  templateUrl: './recording.component.html',
  styleUrls: ['./recording.component.scss'],
})
export class RecordingComponent implements OnInit {
  blobUrl: SafeUrl;
  isRecording = false;  // is true when there is an active recording
  @Input() otherDataElement: DataElement;
  @Input() otherElementType: ElementType;
  @Output() uploaded = new EventEmitter<string>();
  elapsedTime = 0;
  recordingQuality = AudioQuality;
  recordingNoiseLevel = AudioNoiseLevel;
  selectedQuality = AudioQuality.INTEGRATED;
  selectedNoiseLevel = AudioNoiseLevel.MODERATE_NOISE;
  isPrivate = false;
  private audioChunks = [];
  // @ts-ignore
  private mediaRecorder: MediaRecorder;
  private groupId = 1;
  private subscription: Subscription;
  private numberObservable: Observable<number>;
  private browserVersion: string;

  constructor(
      private domSanitizer: DomSanitizer, private userGroupService: UserGroupService, private detector: ChangeDetectorRef,
      private httpClient: HttpClient, private snackBarService: SnackBarService) {
    this.groupId = this.userGroupService.userGroupId;
  }

  ngOnInit(): void {
    this.browserVersion = window.navigator.userAgent;
    navigator.mediaDevices.getUserMedia({audio: true}).then((stream) => {
      // @ts-ignore
      this.mediaRecorder = new MediaRecorder(stream, {mimeType: 'audio/webm'});
      this.mediaRecorder.ondataavailable = (event) => this.audioChunks.push(event.data);
      this.mediaRecorder.onstop = () => {
        this.blobUrl = this.domSanitizer.bypassSecurityTrustUrl(
            URL.createObjectURL(new Blob(this.audioChunks)));
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
    this.subscription =
        this.numberObservable.subscribe((value) => (this.elapsedTime = value));
  }

  stopRecord(): void {
    this.subscription.unsubscribe();
    this.mediaRecorder.stop();
    this.isRecording = false;
  }

  isReady(): boolean {
    return this.audioChunks.length > 0;
  }

  submit(): void {
    const recording = new RecordingDto(this.selectedQuality, this.selectedNoiseLevel, this.browserVersion);
    const formData = new FormData();
    formData.append(`file`, new Blob(this.audioChunks), 'audio');
    formData.append('recording', JSON.stringify(recording));
    formData.append('otherDataElement', JSON.stringify(this.otherDataElement));
    formData.append('otherElementType', this.otherElementType);
    this.httpClient.post(`${environment.url}user_group/${this.groupId}/recording`, formData).subscribe(() => {
      this.getNext();
    });
  }

  private() {
    this.check(CheckedDataElementType.PRIVATE).subscribe(() => {
      this.snackBarService.openMessage('marked as private');
      this.isPrivate = true;
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

  private check(type: CheckedDataElementType) {
    const url = `${environment.url}user_group/${this.groupId}/element/${this.otherDataElement.id}/checked?type=${type}`;
    return this.httpClient.post<void>(url, {});
  }

  private getNext() {
    this.isPrivate = false;
    this.audioChunks = [];
    this.blobUrl = undefined;
    this.uploaded.emit('complete');
  }
}
