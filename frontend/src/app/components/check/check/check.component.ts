import { HttpClient } from "@angular/common/http";
import { Component, HostListener, OnInit, ViewChild } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { Router } from "@angular/router";
import { CarouselComponent } from "ngx-carousel-lib";
import { TupleDto, TupleType } from "src/app/models/tuple-dto";

import { environment } from "../../../../environments/environment";
import { AuthService } from "../../../services/auth.service";
import { SnackBarService } from "../../../services/snack-bar.service";
import { UserGroupService } from "../../../services/user-group.service";
import { CheckMoreComponent } from "../check-more/check-more.component";
import { ShortcutComponent } from "../shortcut/shortcut.component";

import { CheckedOccurrence, CheckedOccurrenceLabel, Occurrence } from "./checked-occurrence";

export enum OccurrenceMode {
  RECORDING = "RECORDING",
  TEXT_AUDIO = "TEXT_AUDIO",
}

@Component({
  selector: "app-check",
  templateUrl: "./check.component.html",
  styleUrls: ["./check.component.scss"],
})
export class CheckComponent implements OnInit {
  isPlaying = false;
  occurrences: Array<Occurrence> = [];
  occurrences_idx = 0;
  audioProgress = 0;
  checkedOccurrenceLabel = CheckedOccurrenceLabel;
  @ViewChild("carousel") private carousel: CarouselComponent;
  private audioPlayer = new Audio();
  private isReady = false;
  private userId: number;
  private groupId = 1;
  tuple: TupleDto;

  constructor(
    private httpClient: HttpClient,
    private dialog: MatDialog,
    private authService: AuthService,
    private router: Router,
    private userGroupService: UserGroupService,
    private snackBarService: SnackBarService
  ) {
    this.groupId = this.userGroupService.userGroupId;
  }

  ngOnInit() {
    this.authService.getUser().subscribe((user) => (this.userId = user.principal.user.id));
    this.getNonLabeledTuple();
    this.getTenNonLabeledTextAudios();
  }

  @HostListener("window:keyup", ["$event"])
  keyEvent(event: KeyboardEvent) {
    if (event.key === "p") {
      this.togglePlay();
    } else if (event.key === "c") {
      this.setCheckedType(CheckedOccurrenceLabel.CORRECT);
    } else if (event.key === "w") {
      this.setCheckedType(CheckedOccurrenceLabel.WRONG);
    } else if (event.key === "s") {
      this.setCheckedType(CheckedOccurrenceLabel.SKIPPED);
    }
  }

  /**
   * set the checked type and prepare the next carousel
   */
  setCheckedType(checkType: CheckedOccurrenceLabel): void {
    // only trigger this method if the user has played the audio at least once
    // to prevent accidental button presses
    if (this.isReady) {
      this.stop();

      const occurrence = this.occurrences[this.carousel.carousel.activeIndex];
      if (checkType === CheckedOccurrenceLabel.SENTENCE_ERROR || checkType === CheckedOccurrenceLabel.PRIVATE) {
        this.httpClient
          .post(`${environment.url}user_group/${this.groupId}/element/${occurrence.dataElementId_1}/checked?type=${checkType}`, {})
          .subscribe((value) => this.snackBarService.openMessage("successfully updated element."));
      } else {
        const cta = new CheckedOccurrence(occurrence.id, checkType);
        this.httpClient.post(`${environment.url}user_group/${this.groupId}/occurrence/check`, cta).subscribe();

        // checkIfFinishedChunk
        if (this.carousel.carousel.activeIndex === this.occurrences.length - 1) {
          this.dialog
            .open(CheckMoreComponent, { width: "500px", disableClose: true })
            .afterClosed()
            .subscribe((result) => {
              if (result) {
                // reset carousel and load new data
                this.carousel.carousel.activeIndex = 0;
                this.occurrences = [];
                this.getTenNonLabeledTextAudios();
              } else {
                this.router.navigate(["/home"]);
              }
            });
        } else {
          this.isReady = false;
          this.loadAudioBlob(this.occurrences[this.carousel.carousel.activeIndex + 1]);
          this.carousel.slideNext();
        }
      }
    }
  }

  togglePlay() {
    this.isReady = true;
    if (this.isPlaying) {
      this.stop();
    } else {
      this.play();
    }
  }

  openShortcutDialog = () =>
    this.dialog.open(ShortcutComponent, {
      width: "500px",
      disableClose: false,
    });

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

  private getTenNonLabeledTextAudios() {
    this.httpClient.get<Array<Occurrence>>(`${environment.url}user_group/${this.groupId}/occurrence/next`).subscribe((textAudios) => {
      this.occurrences = textAudios;
      if (textAudios.length > 0) {
        this.loadAudioBlob(textAudios[0]);
      }
    });
  }

  private getNonLabeledTuple() {
    this.tuple = new TupleDto(1, 2, TupleType.IMAGE_AUDIO, 0, 0, 0, 0);
  }

  private loadAudioBlob(occurrence: Occurrence): void {
    this.httpClient
      .get(`${environment.url}user_group/${this.groupId}/occurrence/audio/${occurrence.dataElementId_2}`, { responseType: "blob" })
      .subscribe((resp) => {
        this.audioPlayer = new Audio(URL.createObjectURL(resp));
        this.audioPlayer.onended = () => (this.isPlaying = false);
        this.audioPlayer.ontimeupdate = () => (this.audioProgress = (this.audioPlayer.currentTime / this.audioPlayer.duration) * 100);
      });
  }
}
