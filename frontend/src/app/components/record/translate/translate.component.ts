import { HttpClient } from '@angular/common/http';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { DataElement } from 'src/app/models/data-element';
import { ElementType } from 'src/app/models/element-type';
import { ReturnWrapper } from 'src/app/models/return-wrapper';
import { Text } from 'src/app/models/text';
import { NumAchievementsService } from 'src/app/services/num-achievements.service';
import { UserGroupService } from 'src/app/services/user-group.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-translate',
  templateUrl: './translate.component.html',
  styleUrls: ['./translate.component.scss'],
})
export class TranslateComponent {
  @Input() otherDataElement: DataElement;
  @Input() otherElementType: ElementType;
  @Output() uploaded = new EventEmitter<ReturnWrapper>();
  isTranslated = false;
  translatedText = '';
  groupId: number;
  textDto: Text;
  dataElementDto: DataElement;

  constructor(
      private httpClient: HttpClient, private userGroupService: UserGroupService,
      private numAchievementsService: NumAchievementsService) {
    this.groupId = this.userGroupService.userGroupId;
  }

  submit_text(): void {
    if (this.translatedText.length < 3) {
      return;
    }
    if (this.isTranslated) {
      return;
    }
    const text = new Text(-1, -1, -1, false, this.translatedText);
    const formData = new FormData();
    formData.append(`text`, JSON.stringify(text));
    formData.append(`otherDataElement`, JSON.stringify(this.otherDataElement));
    formData.append(`otherElementType`, this.otherElementType);
    this.httpClient.post<ReturnWrapper>(`${environment.url}user_group/${this.groupId}/excerpt`, formData)
        .subscribe(res => {
          this.textDto = res.text;
          this.dataElementDto = res.dataElement;
          this.isTranslated = true;
          this.uploaded.emit(res);
          this.numAchievementsService.getNumber();
        });
  }
}
