import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RecordRandomComponent } from './record-random.component';

describe('RecordRandomComponent', () => {
  let component: RecordRandomComponent;
  let fixture: ComponentFixture<RecordRandomComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RecordRandomComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RecordRandomComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
