import {Component, OnInit} from '@angular/core';
import {ApiService} from '../../../services/api.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {User} from '../../../models/User';
import {Router} from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;
  user: User = new User(-1, '', '', '', '', '', '');

  constructor(public apiService: ApiService, private fb: FormBuilder, private router: Router) {
  }

  ngOnInit() {
    this.initForm();
  }

  initForm(): void {
    this.registerForm = this.fb.group({
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      email: ['', [Validators.required]],
      username: ['', [Validators.required]],
      password: ['', [Validators.required]],
      canton: ['', [Validators.required]]
    });
  }

  register(): void {
    this.user.firstName = this.registerForm.controls.firstName.value;
    this.user.lastName = this.registerForm.controls.lastName.value;
    this.user.email = this.registerForm.controls.email.value;
    this.user.username = this.registerForm.controls.username.value;
    this.user.password = this.registerForm.controls.password.value;
    this.user.canton = this.registerForm.controls.canton.value;
    if (this.registerForm.valid) {
      this.apiService.createUser(this.user).subscribe(_ => {
        this.router.navigate(['labeling-tool/login']);
      });
    }
  }

  cancel(): void {
    this.router.navigate(['/labeling-tool/login']);
  }
}
