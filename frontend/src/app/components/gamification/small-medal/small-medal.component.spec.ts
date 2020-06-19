import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SmallMedalComponent } from './small-medal.component';

describe('SmallMedalComponent', () => {
  let component: SmallMedalComponent;
  let fixture: ComponentFixture<SmallMedalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SmallMedalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SmallMedalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
