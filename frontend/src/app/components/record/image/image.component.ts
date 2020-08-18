import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { UserGroupService } from 'src/app/services/user-group.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-image',
  templateUrl: './image.component.html',
  styleUrls: ['./image.component.scss'],
})
export class ImageComponent implements OnInit {
  temp: SafeUrl;
  @Input() dataElementId: number;
  private groupId = 1;

  constructor(private httpClient: HttpClient, private domSanitizer: DomSanitizer, private userGroupService: UserGroupService) {
    this.groupId = this.userGroupService.userGroupId;
  }

  ngOnInit(): void {
    this.getImage();
  }

  private getImage() {
    this.httpClient
        .get(`${environment.url}user_group/${this.groupId}/image/${this.dataElementId}`, {responseType: 'blob'})
        .subscribe((resp) => {
          this.temp = this.domSanitizer.bypassSecurityTrustUrl(URL.createObjectURL(resp));
        });
  }
}
