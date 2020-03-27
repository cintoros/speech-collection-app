import {Component, EventEmitter, Input, OnChanges, OnInit, Output} from '@angular/core';
import {User} from '../../../models/user';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../environments/environment';
import {AuthService} from '../../../services/auth.service';
import {EmailPassword} from '../../../models/email-password';
import {SnackBarService} from '../../../services/snack-bar.service';
import {Dialect} from '../../../models/dialect';
import {DialectService} from '../../../services/dialect.service';

@Component({
  selector: 'app-profile-editor',
  templateUrl: './profile-editor.component.html',
  styleUrls: ['./profile-editor.component.scss']
})
export class ProfileEditorComponent implements OnInit, OnChanges {
  @Input() isNewUser;
  @Input() user: User;
  @Output() output = new EventEmitter();
  @Input() disabled: boolean;
  registerForm: FormGroup;
  dialects: Dialect[] = [];
  private userCopy: User;

  constructor(
    private formBuilder: FormBuilder, private snackBarService: SnackBarService, private httpClient: HttpClient,
    private authService: AuthService, private dialectService: DialectService
  ) {
  }

  ngOnInit() {
    this.dialectService.getDialects().subscribe(v => this.dialects = v);
    const cc = {
      firstName: [this.user.firstName, []],
      lastName: [this.user.lastName, []],
      email: [this.user.email, Validators.compose([
        Validators.required,
        Validators.pattern('^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$')
      ])],
      username: [this.user.username, [Validators.required, Validators.pattern('^[a-zA-Z0-9-.]+$')]],
      canton: [undefined, []],
      zipCode: [undefined, [Validators.required]],
      password: undefined,
      sex: [this.user.sex, [Validators.required]],
      age: [this.user.age, [Validators.required]],
      licence: [this.user.licence, [Validators.required]],
    };
    if (this.isNewUser) {
      cc.password = ['', Validators.compose([
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(50)
      ])];
    }
    this.registerForm = this.formBuilder.group(cc);
    this.userCopy = JSON.parse(JSON.stringify(this.user));
    //TODO change zipcode/canton validators
    /*
    this.form.get('userCategory').valueChanges
      .subscribe(userCategory => {

        if (userCategory === 'student') {
          institutionControl.setValidators([Validators.required]);
          companyControl.setValidators(null);
          salaryControl.setValidators(null);
        }

        if (userCategory === 'employee') {
          institutionControl.setValidators(null);
          companyControl.setValidators([Validators.required]);
          salaryControl.setValidators([Validators.required]);
        }

        institutionControl.updateValueAndValidity();
        companyControl.updateValueAndValidity();
        salaryControl.updateValueAndValidity();
      });
     */
    this.checkDisabled();
  }

  ngOnChanges(): void {
    if (this.registerForm) {
      this.checkDisabled();
    }
  }

  register(): void {
    // we need to deep copy the object to prevent updating the object inside the observables
    const user = JSON.parse(JSON.stringify(this.user));
    user.firstName = this.registerForm.controls.firstName.value;
    user.lastName = this.registerForm.controls.lastName.value;
    user.email = this.registerForm.controls.email.value;
    user.username = this.registerForm.controls.username.value;
    user.password = this.registerForm.controls.password.value;
    user.canton = this.registerForm.controls.canton.value;
    user.zipCode = this.registerForm.controls.zipCode.value;
    if (this.registerForm.valid) {
      if (this.isNewUser) {
        this.httpClient.post(environment.url + 'public/register', user).subscribe(() => {
          this.authService.login(new EmailPassword(user.username, user.password));
        }, () => {
          this.snackBarService.openError('failed to create user: username/email already taken');
        });
      } else {
        this.httpClient.put(environment.url + 'user', user).subscribe(() => {
          if (this.userCopy.username !== user.username) {
            this.snackBarService.openMessage('logout caused by username change');
            this.authService.logout(false);
          } else {
            this.snackBarService.openMessage('successfully updated user');
          }
        }, error => {
          this.snackBarService.openError('failed to update user');
        });
      }
    }
  }

  isCanctonError(errorCode: string) {
    return this.registerForm.controls.zipCode.hasError(errorCode) || this.registerForm.controls.canton.hasError(errorCode);
  }

  cancel = () => this.output.emit('cancel');
  isEmailError = (errorCode: string) => this.registerForm.controls.email.hasError(errorCode);
  isUNError = (errorCode: string) => this.registerForm.controls.username.hasError(errorCode);
  isPwError = (errorCode: string) => this.registerForm.controls.password.hasError(errorCode);

  private checkDisabled() {
    if (this.disabled) {
      this.registerForm.disable();
    } else {
      this.registerForm.enable();
    }
  }
}