import { Component, OnInit, Input } from "@angular/core";
import { SafeUrl, DomSanitizer } from "@angular/platform-browser";
import { HttpClient } from "@angular/common/http";
import { environment } from "src/environments/environment";
import { UserGroupService } from "src/app/services/user-group.service";

@Component({
  selector: "app-image",
  templateUrl: "./image.component.html",
  styleUrls: ["./image.component.scss"],
})
export class ImageComponent implements OnInit {
  temp: SafeUrl;

  private groupId = 1;

  @Input() dataElementId: number;

  constructor(private httpClient: HttpClient, private domSanitizer: DomSanitizer, private userGroupService: UserGroupService) {
    this.groupId = this.userGroupService.userGroupId;
  }

  ngOnInit(): void {
    this.getImage();
  }

  private getImage() {
    this.httpClient
      .get(`${environment.url}user_group/${this.groupId}/image/${this.dataElementId}`, { responseType: "blob" })
      .subscribe((resp) => {
        this.temp = this.domSanitizer.bypassSecurityTrustUrl(URL.createObjectURL(resp));
      });
  }
}
