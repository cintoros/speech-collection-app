import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

interface Features {
  emailIntegration: boolean;
  swissGermanText: boolean;
  additionalData: boolean;
  gamification: Gamification;
}

interface Gamification {
  mode: Mode;
  pointPerLevel: number[];
  dailyDivisor: number;
}

enum Mode {
  DISABLED = 'DISABLED', HALF_ENABLED = 'HALF_ENABLED', ENABLED = 'ENABLED'
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
