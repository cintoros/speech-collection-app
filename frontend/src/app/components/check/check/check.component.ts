import {HttpClient} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {Router} from '@angular/router';
import {TupleDto, TupleType} from 'src/app/models/tuple-dto';

import {environment} from '../../../../environments/environment';
import {AuthService} from '../../../services/auth.service';
import {SnackBarService} from '../../../services/snack-bar.service';
import {UserGroupService} from '../../../services/user-group.service';
import {ShortcutComponent} from '../shortcut/shortcut.component';

import {CheckedOccurrenceLabel} from './checked-occurrence';

export enum OccurrenceMode {
  RECORDING = 'RECORDING',
  TEXT_AUDIO = 'TEXT_AUDIO',
}

@Component({
  selector: 'app-check',
  templateUrl: './check.component.html',
  styleUrls: ['./check.component.scss'],
})
export class CheckComponent implements OnInit {
  selectedTupleType = TupleType.TEXT_TEXT;
  private audioPlayer = new Audio();
  private isReady = false;
  private userId: number;
  private groupId = 1;
  tuple: TupleDto;

  constructor(
      private httpClient: HttpClient, private dialog: MatDialog,
      private authService: AuthService, private router: Router,
      private userGroupService: UserGroupService,
      private snackBarService: SnackBarService) {
    this.groupId = this.userGroupService.userGroupId;
  }

  ngOnInit() {
    this.authService.getUser().subscribe(
        (user) => (this.userId = user.principal.user.id));
    this.getTuple();
  }



  openShortcutDialog = () => this.dialog.open(ShortcutComponent, {
    width: '500px',
    disableClose: false,
  });

  onSelectorChange($event) {
    this.selectedTupleType = $event;
    this.getTuple()
  }

  afterCheck($event) {
    this.tuple = null;
    this.getTuple();
  }


  private getTuple() {
    const formData = new FormData();
    formData.append(
        `selectedTupleType`, JSON.stringify(this.selectedTupleType));
    this.httpClient
        .post<TupleDto>(
            `${environment.url}user_group/${this.groupId}/check-next`, formData)
        .subscribe((tuple) => {
          this.tuple = tuple;
        });
  }
}
