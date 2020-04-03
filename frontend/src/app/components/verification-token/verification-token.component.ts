import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {ChangePassword} from '../../models/change-password';

enum VerificationModus {
  EMAIL_FORM, EMAIL_VERIFIED, EMAIL_TOKEN_EXPIRED, PASSWORD_FORM, PASSWORD_CHANGING, PASSWORD_CHANGED, PASSWORD_TOKEN_EXPIRED, EMAIL_SENDED
}

@Component({
  selector: 'app-verification-token',
  templateUrl: './verification-token.component.html',
  styleUrls: ['./verification-token.component.scss']
})
export class VerificationTokenComponent implements OnInit {
  verificationModus = VerificationModus;
  modus = VerificationModus.EMAIL_FORM;
  password: string;
  email: string;
  private token: string;
  private baseUrl: string;

  constructor(private route: ActivatedRoute, private httpClient: HttpClient) {
  }

  ngOnInit(): void {
    this.token = this.route.snapshot.queryParamMap.get('token');
    const mode = this.route.snapshot.queryParamMap.get('mode');
    this.baseUrl = `${environment.url}public/user/`;
    if (this.token) {
      if (mode === 'password') {
        this.modus = VerificationModus.PASSWORD_CHANGING;
      } else {
        this.httpClient.put(`${this.baseUrl}email/confirm?token=${this.token}`, {}).subscribe(value => {
          if (value) {
            this.modus = VerificationModus.EMAIL_VERIFIED;
          } else {
            this.modus = VerificationModus.EMAIL_TOKEN_EXPIRED;
          }
        });
      }
    } else {
      this.modus = mode === 'password' ? VerificationModus.PASSWORD_FORM : VerificationModus.EMAIL_FORM;
    }
  }

  resendVerificationEmail() {
    this.httpClient.get(`${this.baseUrl}email/resend?email=${this.email}`).subscribe(() => this.modus = VerificationModus.EMAIL_SENDED);
  }

  resetPasswordEmail() {
    this.httpClient.get(`${this.baseUrl}password/resend?email=${this.email}`).subscribe(() => this.modus = VerificationModus.EMAIL_SENDED);
  }

  resetPassword() {
    //TODO why does this not work
    this.httpClient.put(`${this.baseUrl}password/reset?token=${this.token}`, this.password).subscribe(value => {
      if (value) {
        this.modus = VerificationModus.PASSWORD_CHANGED;
      } else {
        this.modus = VerificationModus.PASSWORD_TOKEN_EXPIRED;
      }
    });
  }
}
