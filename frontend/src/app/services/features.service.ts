import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

interface Features {
  emailIntegration: boolean;
  swissGermanText: boolean;
  additionalData: boolean;
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
