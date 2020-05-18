import {HttpClient} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-record',
  templateUrl: './record.component.html',
  styleUrls: ['./record.component.scss'],
})
export class RecordComponent implements OnInit {
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

  isPrivate = false;

  // text fields
  recording_sentence = 'Satz auf Schweizerdeutsch';
  isTranslated = false;
  translatedText = '';
  translatedText_id = -1;

  // controlfields
  withTranslation = false;

  private groupId = 1;

  constructor(
      private snackBarService: SnackBarService, private httpClient: HttpClient,
      private userGroupService: UserGroupService) {
    this.groupId = this.userGroupService.userGroupId;
  }

  ngOnInit() {
    this.getNext();
  }

  /*

  */

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

  private getNext() {
    const formData = new FormData();
    formData.append(`textAllowed`, JSON.stringify(true));
    formData.append(`audioAllowed`, JSON.stringify(false));
    formData.append(`imageAllowed`, JSON.stringify(false));

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
            this.elementType2 =
                Math.random() < 0.5 ? ElementType.TEXT : ElementType.AUDIO;
          if (this.elementType1 == ElementType.IMAGE)
            this.elementType2 =
                Math.random() < 0.5 ? ElementType.TEXT : ElementType.AUDIO;
          if (this.elementType1 == ElementType.AUDIO)
            this.elementType2 = ElementType.TEXT;
          if (this.elementType2 == ElementType.TEXT)
            this.withTranslation = false;
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
