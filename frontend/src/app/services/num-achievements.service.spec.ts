import { TestBed } from '@angular/core/testing';

import { NumAchievementsService } from './num-achievements.service';

describe('NumAchievementsService', () => {
  let service: NumAchievementsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NumAchievementsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
