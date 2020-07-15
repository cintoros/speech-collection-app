import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { UserGroupService } from 'src/app/services/user-group.service';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-audio',
  templateUrl: './audio.component.html',
  styleUrls: ['./audio.component.scss'],
})
// TODO semi-replace with a normal audio player?
export class AudioComponent implements OnInit {
  isPlaying = false;
  audioProgress = 0;
  @Input() dataElementId: number;
  private audioPlayer = new Audio();
  private isReady = false;
  private groupId = 1;

  constructor(private httpClient: HttpClient, private userGroupService: UserGroupService) {
    this.groupId = this.userGroupService.userGroupId;
  }

  ngOnInit(): void {
    this.loadAudioBlob();
  }

  togglePlay() {
    this.isReady = true;
    if (this.isPlaying) {
      this.stop();
    } else {
      this.play();
    }
  }

  private play() {
    this.audioPlayer.play();
    this.isPlaying = true;
  }

  private stop() {
    this.audioPlayer.pause();
    this.audioPlayer.currentTime = 0;
    this.audioProgress = 0;
    this.isPlaying = false;
  }

  private loadAudioBlob(): void {
    this.httpClient
        .get(`${environment.url}user_group/${this.groupId}/occurrence/audio/${this.dataElementId}`, {responseType: 'blob'})
        .subscribe((resp) => {
          this.audioPlayer = new Audio(URL.createObjectURL(resp));
          this.audioPlayer.onended = () => (this.isPlaying = false);
          this.audioPlayer.ontimeupdate = () => (this.audioProgress = (this.audioPlayer.currentTime / this.audioPlayer.duration) * 100);
        });
  }
}
