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
  @Input() isNewUser: boolean;
  @Input() user: User;
  @Output() output = new EventEmitter();
  @Input() disabled: boolean;
  registerForm: FormGroup;
  dialects: Dialect[] = [];
  private userCopy: User;
  private zV = [Validators.required, Validators.pattern('[0-9]{4}'), Validators.minLength(4), Validators.maxLength(4)];

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
      canton: [this.user.dialectId, []],
      zipCode: [this.user.zipCode, this.zV],
      password: undefined,
      sex: [this.user.sex, [Validators.required]],
      age: [this.user.age, [Validators.required]],
      licence: [this.user.licence, [Validators.required]],
      notCH: [this.user.notCh, []]
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


    this.registerForm.controls.notCH.valueChanges
      .subscribe(notCH => {
        const zipCode = this.registerForm.controls.zipCode;
        const canton = this.registerForm.controls.canton;
        if (notCH) {
          zipCode.setValidators([]);
          canton.setValidators([Validators.required]);
        } else {
          zipCode.setValidators(this.zV);
          canton.setValidators([]);
        }

        zipCode.updateValueAndValidity();
        canton.updateValueAndValidity();
      });

    this.checkDisabled();
  }

  ngOnChanges(): void {
    if (this.registerForm) {
      this.checkDisabled();
    }
  }

  register(): void {
    // we need to deep copy the object to prevent updating the object inside the observables
    const firstName = this.registerForm.controls.firstName.value;
    const lastName = this.registerForm.controls.lastName.value;
    const email = this.registerForm.controls.email.value;
    const username = this.registerForm.controls.username.value;
    const password = this.registerForm.controls.password.value;
    const zipCode = this.registerForm.controls.zipCode.value;
    const dialectId = this.registerForm.controls.canton.value;
    const sex = this.registerForm.controls.sex.value;
    const licence = this.registerForm.controls.licence.value;
    const age = this.registerForm.controls.age.value;
    const notCh = this.registerForm.controls.notCH.value;
    const user: User = new User(this.user.id, firstName, lastName, email, username, password, dialectId, sex, licence, age, zipCode, notCh);

    if (this.registerForm.valid) {
      if (this.isNewUser) {
        this.httpClient.post(environment.url + 'public/register', user).subscribe(() => {
          this.authService.login(new EmailPassword(user.username, user.password), () => this.cancel());
        }, () => {
          this.snackBarService.openError('failed to create user: username/email already taken');
        });
      } else {
        this.httpClient.put(environment.url + 'user', user).subscribe(() => {
          if (this.userCopy.username !== user.username) {
            this.snackBarService.openMessage('logout caused by username change');
            this.authService.logout(false);
          } else {
            this.authService.reloadUser();
            this.cancel();
            this.snackBarService.openMessage('successfully updated user');
          }
        }, error => {
          this.snackBarService.openError('failed to update user');
        });
      }
    }
  }

  isCanctonError = (errorCode: string) => this.registerForm.controls.canton.hasError(errorCode);
  cancel = () => this.output.emit('cancel');
  isEmailError = (errorCode: string) => this.registerForm.controls.email.hasError(errorCode);
  isUNError = (errorCode: string) => this.registerForm.controls.username.hasError(errorCode);
  isPwError = (errorCode: string) => this.registerForm.controls.password.hasError(errorCode);
  isZipError = (errorCode: string) => !this.registerForm.controls.zipCode.valid;

  private checkDisabled() {
    if (this.disabled) {
      this.registerForm.disable();
    } else {
      this.registerForm.enable();
    }
  }
}
