import {HttpClient} from '@angular/common/http';
import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-record',
  templateUrl: './record.component.html',
  styleUrls: ['./record.component.scss'],
})
export class RecordComponent implements OnInit {
  @Input() selectedElement: ElementType;

  dataElement1: DataElementDto = null;
  dataElement2: DataElementDto = null;
  dataElementTranslation: DataElementDto;
  achievementWrapper: AchievementWrapper;

  // Depending on the current mode only two
  // of the fields below will be != null
  textDto1: TextDto = null;
  textDto2: TextDto = null;
  textDtoTranslation: TextDto;
  recordingDto1: RecordingDto = null;
  recordingDto2: RecordingDto = null;
  imageDto1: ImageDto = null;
  imageDto2: ImageDto = null;
  elementType1: ElementType = null;
  elementType2: ElementType = null;
  elementTypeTranslation: ElementType;

  isPrivate = false;

  // controlfields
  withTranslation = true;
  isTranslated = false;

  // this controls the visibility of the selector component
  isDebug = false;

  private groupId = 1;

  constructor(
      private snackBarService: SnackBarService, private httpClient: HttpClient,
      private userGroupService: UserGroupService) {
    this.groupId = this.userGroupService.userGroupId;
  }

  ngOnInit() {
    this.getNext();
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

  private check = (type: CheckedDataElementType) => this.httpClient.post<void>(
      `${environment.url}user_group/${this.groupId}/element/${
          this.dataElement1.id}/checked?type=${type}`,
      {});

  private resetAndNext(elem: ReturnWrapper) {
    this.resetFields();
    this.getNext();
  }

  selectorUpdate($event) {
    this.resetFields();
    this.selectedElement = $event;
    this.getNext();
  }

  translationUpdate($event) {
    this.resetFields();
    this.withTranslation = $event;
    this.getNext();
  }

  private triggerRecord(elem: ReturnWrapper) {
    this.dataElementTranslation = elem.dataElementDto;
    this.textDtoTranslation = elem.textDto;
    this.elementTypeTranslation = elem.elementType;
    this.isTranslated = true;
  }

  private resetFields() {
    this.dataElement1 = null;
    this.dataElement2 = null;
    this.textDto1 = null;
    this.textDto2 = null;
    this.recordingDto1 = null;
    this.recordingDto2 = null;
    this.imageDto1 = null;
    this.imageDto2 = null;
    this.elementType1 = null;
    this.elementType2 = null;
    this.isPrivate = false;
    this.dataElementTranslation = null;
    this.textDtoTranslation = null;
    this.elementTypeTranslation = null;
    this.isTranslated = false;
  }

  private getNext() {
    console.log(this.selectedElement);
    const formData = new FormData();
    formData.append(`selectedElement`, JSON.stringify(this.selectedElement));
    console.log(formData);
    this.httpClient
        .post<ReturnWrapper>(
            `${environment.url}user_group/${this.groupId}/next`, formData)
        .subscribe((value) => {
          this.dataElement1 = value.dataElementDto;
          this.textDto1 = value.textDto;
          this.recordingDto1 = value.recordingDto;
          this.imageDto1 = value.imageDto;
          this.elementType1 = value.elementType;
          if (this.elementType1 == ElementType.TEXT)
            this.elementType2 = ElementType.AUDIO;
          if (this.elementType1 == ElementType.IMAGE)
            this.elementType2 = ElementType.AUDIO;
          if (this.elementType1 == ElementType.AUDIO)
            this.elementType2 = ElementType.TEXT;
          if (this.elementType2 == ElementType.TEXT)
            this.withTranslation = false;
          this.achievementWrapper = value.achievementWrapper;
        });
  }
}

import {environment} from '../../../environments/environment';
import {CheckedDataElementType} from '../../models/checked-data-element-type';
import {RecordingDto} from '../../models/recording-dto';
import {TextDto} from '../../models/text-dto';

import {SnackBarService} from '../../services/snack-bar.service';
import {UserGroupService} from '../../services/user-group.service';
import {DataElementDto} from 'src/app/models/data-element-dto';
import {ImageDto} from 'src/app/models/image-dto';
import {ReturnWrapper} from 'src/app/models/return-wrapper';
import {ElementType} from 'src/app/models/element-type';
import {AchievementWrapper} from 'src/app/models/achievement-wrapper';
