import { HttpClient } from "@angular/common/http";
import { ChangeDetectorRef, Component, OnInit } from "@angular/core";
import { DomSanitizer, SafeUrl } from "@angular/platform-browser";
import { Observable, Subscription, timer } from "rxjs";
import { ExcerptComponent } from "src/app/components/record/excerpt/excerpt.component";

@Component({
  selector: "app-record",
  templateUrl: "./record.component.html",
  styleUrls: ["./record.component.scss"],
})
export class RecordComponent implements OnInit {
  tempWrapper: ReturnWrapper = null;
  dataElement1: DataElementDto = null;
  dataElement2: DataElementDto = null;

  // Depending on the current mode only two
  // of the fields below will be != null
  textDto1: TextDto = null;
  textDto2: TextDto = null;
  recordingDto1: RecordingDto = null;
  recordingDto2: RecordingDto = null;
  imageDto1: ImageDto = null;
  imageDto2: ImageDto = null;
  elementType1: ElementType = null;
  elementType2: ElementType = null;

  // recording fields
  isRecording = false; //is true when there is an active recording
  recordingQuality = RecordingQuality;
  recordingNoiseLevel = RecordingNoiseLevel;
  selectedQuality = RecordingQuality.INTEGRATED;
  selectedNoiseLevel = RecordingNoiseLevel.MODERATE_NOISE;
  isPrivate = false;

  // text fields
  recording_sentence = "Satz auf Schweizerdeutsch";
  isTranslated = false;
  translatedText = "";
  translatedText_id = -1;

  // controlfields
  withTranslation = true;

  blobUrl: SafeUrl;

  // @ts-ignore
  private mediaRecorder: MediaRecorder;
  private audioChunks = [];
  private groupId = 1;
  private elapsedTime = 0;
  private numberObservable: Observable<number>;
  private subscription: Subscription;

  constructor(
    private snackBarService: SnackBarService,
    private detector: ChangeDetectorRef,
    private httpClient: HttpClient,
    private domSanitizer: DomSanitizer,
    private userGroupService: UserGroupService
  ) {
    this.groupId = this.userGroupService.userGroupId;
  }

  ngOnInit() {
    this.getNext();
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

  submit_text_recording(): void {
    const recording = new RecordingDto(this.translatedText_id, this.selectedQuality, this.selectedNoiseLevel);
    const formData = new FormData();
    formData.append(`file`, new Blob(this.audioChunks), "audio");
    formData.append("recording", JSON.stringify(recording));
    this.httpClient.post(`${environment.url}user_group/${this.groupId}/recording`, formData).subscribe(() => {
      this.audioChunks = [];
      this.blobUrl = undefined;
      this.snackBarService.openMessage("Successfully uploaded recording");
      this.isTranslated = false;
      this.translatedText = "";
      this.getNext();
    });
  }

  submit_text_text(): void {
    if (this.translatedText.length < 3) return;
    const text = new TextDto(-1, -1, -1, false, this.translatedText);
    const data_element = new DataElementDto(-1, -1, -1, this.groupId, null, false, this.isPrivate, 0);
    const formData = new FormData();
    formData.append(`text`, JSON.stringify(text));
    formData.append(`data_element`, JSON.stringify(data_element));

    this.httpClient.post<number>(`${environment.url}user_group/${this.groupId}/excerpt`, formData).subscribe((translatedText_id) => {
      this.translatedText_id = translatedText_id;
      this.isTranslated = true;
      this.recording_sentence = this.translatedText;
    });
  }

  private() {
    this.check(CheckedDataElementType.PRIVATE).subscribe(() => {
      this.snackBarService.openMessage("marked as private");
      this.isPrivate = true;
    });
  }

  skip() {
    this.check(CheckedDataElementType.SKIPPED).subscribe(() => {
      this.snackBarService.openMessage("marked as skipped");
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
  private check = (type: CheckedDataElementType) =>
    this.httpClient.post<void>(`${environment.url}user_group/${this.groupId}/element/${this.dataElement1.id}/checked?type=${type}`, {});

  private getNext() {
    const formData = new FormData();
    formData.append(`textAllowed`, JSON.stringify(true));
    formData.append(`audioAllowed`, JSON.stringify(false));
    formData.append(`imageAllowed`, JSON.stringify(false));

    this.httpClient.post<ReturnWrapper>(`${environment.url}user_group/${this.groupId}/next`, formData).subscribe((value) => {
      this.dataElement1 = value.dataElementDto;
      this.textDto1 = value.textDto;
      this.recordingDto1 = value.recordingDto;
      this.imageDto1 = value.imageDto;
      this.elementType1 = value.elementType;
      if (this.elementType1 == ElementType.TEXT) this.elementType2 = Math.random() < 0.5 ? ElementType.TEXT : ElementType.AUDIO;
      if (this.elementType1 == ElementType.IMAGE) this.elementType2 = Math.random() < 0.5 ? ElementType.TEXT : ElementType.AUDIO;
      if (this.elementType1 == ElementType.AUDIO) this.elementType2 = ElementType.TEXT;
      if (this.elementType2 == ElementType.TEXT) this.withTranslation = false;
    });
  }
}

import { environment } from "../../../environments/environment";
import { CheckedDataElementType } from "../../models/checked-data-element-type";
import { RecordingDto, RecordingNoiseLevel, RecordingQuality } from "../../models/recording-dto";
import { TextDto } from "../../models/text-dto";
import { LoginComponent } from "../login/login.component";

import { SnackBarService } from "../../services/snack-bar.service";
import { UserGroupService } from "../../services/user-group.service";
import { DataElementDto } from "src/app/models/data-element-dto";
import { ImageDto } from "src/app/models/image-dto";
import { ReturnStatement } from "@angular/compiler";
import { ReturnWrapper } from "src/app/models/return-wrapper";
import { ElementType } from "src/app/models/element-type";
