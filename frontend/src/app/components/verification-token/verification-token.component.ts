import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';

@Component({
  selector: 'app-verification-token',
  templateUrl: './verification-token.component.html',
  styleUrls: ['./verification-token.component.scss']
})
export class VerificationTokenComponent implements OnInit {
  private token: string;

  constructor(private route: ActivatedRoute, private httpClient: HttpClient) {
  }

  ngOnInit(): void {
    this.token = this.route.snapshot.queryParamMap.get('token');
    console.log(this.token);
    const baseUrl = `${environment.url}public/token/verify`;

    this.httpClient.get(`${baseUrl}?token=${this.token}`).subscribe(value => {
      // TODO maybe also sent the type on verify?
    });
    // TODO implement component
  }

  resendVerificationEmail() {
    //TODO implement
  }
}
