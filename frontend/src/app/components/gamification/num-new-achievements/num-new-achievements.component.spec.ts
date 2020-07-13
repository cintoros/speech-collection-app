import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NumNewAchievementsComponent } from './num-new-achievements.component';

describe('NumNewAchievementsComponent', () => {
  let component: NumNewAchievementsComponent;
  let fixture: ComponentFixture<NumNewAchievementsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [NumNewAchievementsComponent]
    })
        .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NumNewAchievementsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
