import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RecordAnimationComponent } from './record-animation.component';

describe('RecordAnimationComponent', () => {
  let component: RecordAnimationComponent;
  let fixture: ComponentFixture<RecordAnimationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RecordAnimationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RecordAnimationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
