import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AchievementWrapper } from 'src/app/models/achievement-wrapper';
import { DataElementDto } from 'src/app/models/data-element-dto';
import { ElementType } from 'src/app/models/element-type';
import { ImageDto } from 'src/app/models/image-dto';
import { ReturnWrapper } from 'src/app/models/return-wrapper';
import { CustomUserDetails } from 'src/app/models/spring-principal';
import { AuthService } from 'src/app/services/auth.service';
import { NumAchievementsService } from 'src/app/services/num-achievements.service';
import { environment } from '../../../environments/environment';
import { CheckedDataElementType } from '../../models/checked-data-element-type';
import { RecordingDto } from '../../models/recording-dto';
import { TextDto } from '../../models/text-dto';
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

  dataElement1: DataElementDto = null;
  dataElement2: DataElementDto = null;
  dataElementTranslation: DataElementDto;
  achievementWrapper: AchievementWrapper;

  // Depending on the current mode only two of the fields below will be != null
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
  withTranslation = false;
  isTranslated = false;
  additionalData = true;
  // this controls the visibility of the selector component
  isDebug = false;
  user: CustomUserDetails;
  private groupId = 1;

  constructor(
      public authService: AuthService, private snackBarService: SnackBarService, private httpClient: HttpClient,
      private userGroupService: UserGroupService, private numAchievementsService: NumAchievementsService,
      private featuresService: FeaturesService) {
    this.groupId = this.userGroupService.userGroupId;
    authService.getUser().subscribe((user) => {
      this.user = user.principal;
      this.user.gamificationOn = user.principal.user.gamificationOn;
    });
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

  selectorUpdate(selectedElement: ElementType) {
    this.resetFields();
    this.selectedElement = selectedElement;
    this.getNext();
  }

  translationUpdate(withTranslation: boolean) {
    this.resetFields();
    this.withTranslation = withTranslation;
    this.getNext();
  }

  resetAndNext() {
    this.resetFields();
    this.getNext();
  }

  triggerRecord(elem: ReturnWrapper) {
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
    const formData = new FormData();
    formData.append(`selectedElement`, JSON.stringify(this.selectedElement));
    this.httpClient.post<ReturnWrapper>(`${environment.url}user_group/${this.groupId}/next`, formData)
        .subscribe(value => {
          this.dataElement1 = value.dataElementDto;
          this.textDto1 = value.textDto;
          this.recordingDto1 = value.recordingDto;
          this.imageDto1 = value.imageDto;
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
        });
  }
}

