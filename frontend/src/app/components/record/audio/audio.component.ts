import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { UserGroupService } from 'src/app/services/user-group.service';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-audio',
  templateUrl: './audio.component.html',
  styleUrls: ['./audio.component.scss'],
})
export class AudioComponent implements OnInit {
  isPlaying = false;
  @Input() dataElementId: number;
  blobUrl: any;
  private groupId = 1;

  constructor(
      private httpClient: HttpClient, private userGroupService: UserGroupService, private domSanitizer: DomSanitizer) {
    this.groupId = this.userGroupService.userGroupId;
  }

  ngOnInit(): void {
    this.loadAudioBlob();
  }

  togglePlay() {
    if (this.isPlaying) {
      this.stop();
    } else {
      this.play();
    }
  }

  private audioPlayer = (): HTMLAudioElement => (document.getElementById('htmlAudioPlayer') as HTMLAudioElement);

  private play() {
    this.audioPlayer().play();
    this.isPlaying = true;
  }

  private stop() {
    this.audioPlayer().pause();
    this.audioPlayer().currentTime = 0;
    this.isPlaying = false;
  }

  private loadAudioBlob(): void {
    this.httpClient
        .get(`${environment.url}user_group/${this.groupId}/occurrence/audio/${this.dataElementId}`, {responseType: 'blob'})
        .subscribe(resp => {
          this.blobUrl = this.domSanitizer.bypassSecurityTrustUrl(URL.createObjectURL(resp));
          this.audioPlayer().onended = () => this.isPlaying = false;
        });
  }
}
