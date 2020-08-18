import { HttpClient } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { AchievementWrapper } from 'src/app/models/achievement-wrapper';
import { DataElement } from 'src/app/models/data-element';
import { ElementType } from 'src/app/models/element-type';
import { Image } from 'src/app/models/image';
import { ReturnWrapper } from 'src/app/models/return-wrapper';
import { AuthService } from 'src/app/services/auth.service';
import { NumAchievementsService } from 'src/app/services/num-achievements.service';
import { environment } from '../../../environments/environment';
import { CheckedDataElementType } from '../../models/checked-data-element-type';
import { RecordingDto } from '../../models/recording-dto';
import { Text } from '../../models/text';
import { FeaturesService } from '../../services/features.service';
import { SnackBarService } from '../../services/snack-bar.service';
import { UserGroupService } from '../../services/user-group.service';

@Component({
  selector: 'app-record',
  templateUrl: './record.component.html',
  styleUrls: ['./record.component.scss'],
})
// TODO simplify logic with backend?
// TODO test all modi ald & new?
export class RecordComponent implements OnInit {
  selectedElement = ElementType.TEXT_OR_IMAGE;

  dataElement1: DataElement = null;
  dataElement2: DataElement = null;
  dataElementTranslation: DataElement;
  achievementWrapper: AchievementWrapper;

  // Depending on the current mode only two of the fields below will be != null
  textDto1: Text = null;
  textDto2: Text = null;
  textDtoTranslation: Text;
  recordingDto1: RecordingDto = null;
  recordingDto2: RecordingDto = null;
  imageDto1: Image = null;
  imageDto2: Image = null;
  elementType1: ElementType = null;
  elementType2: ElementType = null;
  elementTypeTranslation: ElementType;

  isPrivate = false;

  // controlfields
  withTranslation = false;
  isTranslated = false;
  additionalData = true;
  gamificationOn = false;
  visible = true;
  private groupId = 1;

  constructor(
      public authService: AuthService, private snackBarService: SnackBarService, private httpClient: HttpClient,
      private userGroupService: UserGroupService, private numAchievementsService: NumAchievementsService,
      private featuresService: FeaturesService, private changeDetectorRef: ChangeDetectorRef) {
    this.groupId = this.userGroupService.userGroupId;
    authService.getUser().subscribe(user => this.gamificationOn = user.principal.user.gamificationOn);
    featuresService.getFeatureFlags().subscribe(v => {
      this.withTranslation = v.swissGermanText;
      this.additionalData = v.additionalData;
    });
  }

  ngOnInit() {
    this.getNext();
  }

  skip() {
    const url = `${environment.url}user_group/${this.groupId}/element/${this.dataElement1.id}/checked?type=${CheckedDataElementType.SKIPPED}`;
    this.httpClient.post<void>(url, {}).subscribe(() => {
      this.snackBarService.openMessage('marked as skipped');
      this.getNext();
    });
  }

  resetAndNext() {
    this.resetFields();
    this.getNext();
  }

  triggerRecord(elem: ReturnWrapper) {
    this.dataElementTranslation = elem.dataElement;
    this.textDtoTranslation = elem.text;
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
    const formData = new FormData();
    formData.append(`selectedElement`, this.selectedElement);
    this.httpClient.post<ReturnWrapper>(`${environment.url}user_group/${this.groupId}/next`, formData)
        .subscribe(value => {
          this.dataElement1 = value.dataElement;
          this.textDto1 = value.text;
          this.recordingDto1 = value.recordingDto;
          this.imageDto1 = value.image;
          this.elementType1 = value.elementType;
          if (this.elementType1 === ElementType.TEXT) {
            this.elementType2 = ElementType.AUDIO;
          }
          if (this.elementType1 === ElementType.IMAGE) {
            this.elementType2 = ElementType.AUDIO;
          }
          if (this.elementType1 === ElementType.AUDIO) {
            this.elementType2 = ElementType.TEXT;
          }
          if (this.elementType2 === ElementType.TEXT) {
            this.withTranslation = false;
          }
          this.achievementWrapper = value.achievementWrapper;
          this.numAchievementsService.getNumber();
          this.changeDetectorRef.detectChanges();
        });
  }
}

