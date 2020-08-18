import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { filter } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { EmailPassword } from '../models/email-password';
import { SpringPrincipal } from '../models/spring-principal';
import { SnackBarService } from './snack-bar.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  static currentUserStore = 'currentUser';
  private user: BehaviorSubject<SpringPrincipal> = new BehaviorSubject<SpringPrincipal>(null);

  constructor(private router: Router, private httpClient: HttpClient, private snackBarService: SnackBarService) {
  }

  getUser() {
    if (this.user.value === null) {
      this.reloadUser();
    }
    return this.user.pipe(filter(value => value !== null));
  }

  reloadUser = () => this.httpClient.get<SpringPrincipal>(environment.url + 'user').subscribe(value => this.user.next(value));

  checkAuthenticated(): boolean {
    const item = sessionStorage.getItem(AuthService.currentUserStore);
    return item != null && item.trim().length > 0;
  }

  login(emailPassword: EmailPassword, p: () => void) {
    this.loginUser(emailPassword).subscribe(user => {
      this.user.next(user);
      sessionStorage.setItem(AuthService.currentUserStore, this.buildAuthenticationHeader(emailPassword.email, emailPassword.password));
      this.router.navigate(['/home']);
    }, error => {
      this.snackBarService.openError(error);
      sessionStorage.clear();
      p.apply(null);
    });
  }

  buildAuthenticationHeader(username: string, password: string): string {
    return 'Basic ' + btoa(username + ':' + password);
  }

  logout(b: boolean) {
    sessionStorage.clear();
    this.httpClient.post(`${environment.url}public/logout`, {}).subscribe(() => {
    }, () => {
    }, () => {
      this.router.navigate(['/login'])
          .finally(() => {
            if (b) {
              location.reload();
            }
          });
    });

  }

  private loginUser(user: EmailPassword): Observable<SpringPrincipal> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        // needed to prevent browser popup - only happens once angular is served from docker,spring
        'X-Requested-With': 'XMLHttpRequest',
        Authorization: this.buildAuthenticationHeader(user.email, user.password)
      })
    };
    return this.httpClient.get<SpringPrincipal>(environment.url + 'user', httpOptions);
  }
}
