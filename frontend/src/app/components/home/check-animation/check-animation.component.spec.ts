import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckAnimationComponent } from './check-animation.component';

describe('CheckAnimationComponent', () => {
  let component: CheckAnimationComponent;
  let fixture: ComponentFixture<CheckAnimationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [CheckAnimationComponent]
    })
        .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CheckAnimationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
