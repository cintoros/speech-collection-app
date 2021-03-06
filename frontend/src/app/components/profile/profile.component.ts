import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { environment } from '../../../environments/environment';
import { ChangePassword } from '../../models/change-password';
import { User } from '../../models/user';
import { AuthService } from '../../services/auth.service';
import { DialectService } from '../../services/dialect.service';
import { SnackBarService } from '../../services/snack-bar.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})

export class ProfileComponent implements OnInit {
  isProfileEdit = false;
  isPasswordEdit = false;
  changePasswordForm: FormGroup;
  user: User;

  constructor(
      public authService: AuthService, private httpClient: HttpClient, private formBuilder: FormBuilder,
      private snackBarService: SnackBarService, private dialectService: DialectService
  ) {
  }

  ngOnInit() {
    this.authService.getUser().subscribe(sp => this.user = sp.principal.user);
    this.changePasswordForm = this.formBuilder.group({
      password: ['', [Validators.required]],
      newPassword: ['', Validators.compose([
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(50)
      ])]
    });
  }

  copyUser = () => JSON.parse(JSON.stringify(this.user));
  toggleProfileEdit = () => this.isProfileEdit = !this.isProfileEdit;
  togglePasswordEdit = () => this.isPasswordEdit = !this.isPasswordEdit;
  isOldPwError = (errorCode: string) => this.changePasswordForm.controls.password.hasError(errorCode);
  isNewPwError = (errorCode: string) => this.changePasswordForm.controls.newPassword.hasError(errorCode);

  changePassword() {
    this.httpClient.put(environment.url + 'user/password',
        new ChangePassword(this.changePasswordForm.controls.password.value, this.changePasswordForm.controls.newPassword.value)
    ).subscribe(() => {
      this.authService.logout(false);
    }, err => {
      if (err === 'BAD REQUEST') {
        this.snackBarService.openError('Wrong password');
      }
    });
  }
}
