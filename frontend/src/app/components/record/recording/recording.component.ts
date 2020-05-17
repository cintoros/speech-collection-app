import { Component, OnInit, ChangeDetectorRef, Input, Output, EventEmitter } from "@angular/core";
import { SafeUrl, DomSanitizer } from "@angular/platform-browser";
import { Subscription, Observable, timer } from "rxjs";
import { CheckedDataElementType } from "src/app/models/checked-data-element-type";
import { HttpClient } from "@angular/common/http";
import { environment } from "src/environments/environment";
import { UserGroupService } from "src/app/services/user-group.service";
import { RecordingDto, RecordingQuality, RecordingNoiseLevel } from "src/app/models/recording-dto";

@Component({
  selector: "app-recording",
  templateUrl: "./recording.component.html",
  styleUrls: ["./recording.component.scss"],
})
export class RecordingComponent implements OnInit {
  blobUrl: SafeUrl;
  isRecording = false; //is true when there is an active recording

  @Input() otherDataElement: number;
  @Output() uploaded = new EventEmitter<string>();

  private audioChunks = [];
  // @ts-ignore
  private mediaRecorder: MediaRecorder;
  private elapsedTime = 0;
  private groupId = 1;
  private subscription: Subscription;
  private numberObservable: Observable<number>;

  constructor(
    private domSanitizer: DomSanitizer,
    private userGroupService: UserGroupService,
    private detector: ChangeDetectorRef,
    private httpClient: HttpClient
  ) {
    this.groupId = this.userGroupService.userGroupId;
  }

  ngOnInit(): void {
    navigator.mediaDevices.getUserMedia({ audio: true }).then((stream) => {
      // @ts-ignore
      this.mediaRecorder = new MediaRecorder(stream, {
        mimeType: "audio/webm",
      });
      this.mediaRecorder.ondataavailable = (event) => this.audioChunks.push(event.data);
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
    this.subscription = this.numberObservable.subscribe((value) => (this.elapsedTime = value));
  }

  stopRecord(): void {
    this.subscription.unsubscribe();
    this.mediaRecorder.stop();
    this.isRecording = false;
  }

  isReady = () => this.audioChunks.length > 0;

  submit_recording(): void {
    const recording = new RecordingDto(-1, -1, -1, "", RecordingQuality.INTEGRATED, RecordingNoiseLevel.MODERATE_NOISE, "", -1, -1);
    const formData = new FormData();
    formData.append(`file`, new Blob(this.audioChunks), "audio");
    formData.append("recording", JSON.stringify(recording));
    formData.append("otherDataElement", JSON.stringify(this.otherDataElement));
    this.httpClient.post(`${environment.url}user_group/${this.groupId}/recording`, formData).subscribe(() => {
      this.audioChunks = [];
      this.blobUrl = undefined;
      this.uploaded.emit("complete");
    });
  }
}
