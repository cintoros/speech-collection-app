import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {EmailPassword} from '../../models/email-password';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {User} from '../../models/user';
import {FeaturesService} from '../../services/features.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  isLogin = true;
  emailIntegration: boolean;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router, private featuresService: FeaturesService) {
  }

  ngOnInit() {
    this.featuresService.getFeatureFlags().subscribe(v => this.emailIntegration = v.emailIntegration);
    this.initForm();
    if (this.authService.checkAuthenticated()) {
      this.router.navigate(['/home']);
    }
  }

  initForm(): void {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }

  login(): void {
    if (this.loginForm.valid) {
      this.authService.login(new EmailPassword(this.loginForm.controls.email.value, this.loginForm.controls.password.value), () => null);
    }
  }

  getEmailErrorMessage(): string {
    if (this.loginForm.controls.email.hasError('required')) {
      return 'Please enter your email or username';
    }
  }

  getPasswordErrorMessage(): string {
    if (this.loginForm.controls.password.hasError('required')) {
      return 'Please enter a password';
    }
  }

  toggleIsLogin = () => this.isLogin = !this.isLogin;
  newUser = () => User.default();
}
