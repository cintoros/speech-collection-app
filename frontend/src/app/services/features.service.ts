import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';

interface Features {
  emailIntegration: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class FeaturesService {
  private features: Observable<Features>;

  constructor(private httpClient: HttpClient) {
  }

  getFeatureFlags() {
    if (!this.features) {
      this.features = this.httpClient.get<Features>(environment.url + 'public/features');
    }
    return this.features;
  }
}
