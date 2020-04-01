import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';

enum VerificationModus {
  EMAIL_VERIFIED, EMAIL_TOKEN_EXPIRED, PASSWORD_FORM, PASSWORD_CHANGED, PASSWORD_TOKEN_EXPIRED, EMAIL_SENDED
}

@Component({
  selector: 'app-verification-token',
  templateUrl: './verification-token.component.html',
  styleUrls: ['./verification-token.component.scss']
})
export class VerificationTokenComponent implements OnInit {
  verificationModus = VerificationModus;
  modus = VerificationModus.EMAIL_VERIFIED;
  password: string;
  email: string;
  private token: string;
  private baseUrl: string;

  constructor(private route: ActivatedRoute, private httpClient: HttpClient) {
  }

  ngOnInit(): void {
    this.token = this.route.snapshot.queryParamMap.get('token');
    // TODO if token is not set this is from the login page => ask email
    const mode = this.route.snapshot.queryParamMap.get('mode');
    if (mode === 'password') {
      this.modus = VerificationModus.PASSWORD_FORM;
    } else {
      this.baseUrl = `${environment.url}public/user/`;
      this.httpClient.put(`${this.baseUrl}email/confirm?token=${this.token}`, {}).subscribe(value => {
        if (value) {
          this.modus = VerificationModus.EMAIL_VERIFIED;
        } else {
          this.modus = VerificationModus.EMAIL_TOKEN_EXPIRED;
        }
      });
    }
  }

  resendVerificationEmail() {
    this.httpClient.get(`${this.baseUrl}email/resend?email=${this.email}`).subscribe(() => this.modus = VerificationModus.EMAIL_SENDED);
  }

  resetPasswordEmail() {
    this.httpClient.get(`${this.baseUrl}password/resend?email=${this.email}`).subscribe(() => this.modus = VerificationModus.EMAIL_SENDED);

  }

  resetPassword() {
    this.httpClient.put(`${this.baseUrl}password/reset?token=${this.token}`, {}).subscribe(value => {
      if (value) {
        this.modus = VerificationModus.EMAIL_VERIFIED;
      } else {
        this.modus = VerificationModus.EMAIL_TOKEN_EXPIRED;
      }
    }
  }
