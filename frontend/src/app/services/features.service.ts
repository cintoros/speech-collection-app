import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { filter } from 'rxjs/operators';
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
  private features: BehaviorSubject<Features> = new BehaviorSubject<Features>(null);

  constructor(private httpClient: HttpClient) {
  }

  getFeatureFlags() {
    if (this.features.value === null) {
      this.httpClient.get<Features>(environment.url + 'public/features').subscribe(value => this.features.next(value));
    }
    return this.features.pipe(filter(value => value !== null));
  }
}
