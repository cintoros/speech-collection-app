import {HttpClient} from '@angular/common/http';
import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {DataElementDto} from 'src/app/models/data-element-dto';
import {ElementType} from 'src/app/models/element-type';
import {ReturnWrapper} from 'src/app/models/return-wrapper';
import {TextDto} from 'src/app/models/text-dto';
import {NumAchievementsService} from 'src/app/services/num-achievements.service';
import {UserGroupService} from 'src/app/services/user-group.service';
import {environment} from 'src/environments/environment';

@Component({
  selector: 'app-translate',
  templateUrl: './translate.component.html',
  styleUrls: ['./translate.component.scss'],
})
export class TranslateComponent implements OnInit {
  @Input() otherDataElement: DataElementDto;
  @Input() otherElementType: ElementType;
  @Output() uploaded = new EventEmitter<ReturnWrapper>();

  recording_sentence = 'Satz auf Schweizerdeutsch';
  isTranslated = false;
  translatedText = '';
  groupId: number;
  textDto: TextDto;
  dataElementDto: DataElementDto;

  constructor(
      private httpClient: HttpClient,
      private userGroupService: UserGroupService,
      private numAchievementsService: NumAchievementsService) {
    this.groupId = this.userGroupService.userGroupId;
  }

  ngOnInit(): void {}

  submit_text(): void {
    if (this.translatedText.length < 3) return;
    if (this.isTranslated) return;
    const text = new TextDto(-1, -1, -1, false, this.translatedText);
    const formData = new FormData();
    formData.append(`text`, JSON.stringify(text));
    formData.append(`otherDataElement`, JSON.stringify(this.otherDataElement));
    formData.append(`otherElementType`, JSON.stringify(this.otherElementType));
    this.httpClient
        .post<ReturnWrapper>(
            `${environment.url}user_group/${this.groupId}/excerpt`, formData)
        .subscribe((res) => {
          this.textDto = res.textDto;
          this.dataElementDto = res.dataElementDto;
          this.isTranslated = true;
          this.uploaded.emit(res);
          this.numAchievementsService.getNumber();
        });
  }
}
