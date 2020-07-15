import { Injectable, NgZone } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({providedIn: 'root'})
export class SnackBarService {
  constructor(private matSnackBar: MatSnackBar, private ngZone: NgZone) {
  }

  openError(error: string): void {
    // see https://github.com/angular/components/issues/9875#issuecomment-444218890
    this.ngZone.run(() => {
      setTimeout(() => {
        this.matSnackBar.open(error, 'schliessen', {
          duration: 5000,
          panelClass: 'snack-bar-error',
          verticalPosition: 'bottom',
          horizontalPosition: 'center'
        });
      }, 0);
    });
    console.error(error);
  }

  openMessage(message: string): void {
    // see https://github.com/angular/components/issues/9875#issuecomment-444218890
    this.ngZone.run(() => {
      setTimeout(() => {
        this.matSnackBar.open(message, 'schliessen', {
          duration: 5000,
          panelClass: 'snack-bar-message',
          verticalPosition: 'bottom',
          horizontalPosition: 'center'
        });
      }, 0);
    });
  }
}
