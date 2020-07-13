import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GlyphiconsBatchComponent } from './glyphicons-batch.component';

describe('GlyphiconsBatchComponent', () => {
  let component: GlyphiconsBatchComponent;
  let fixture: ComponentFixture<GlyphiconsBatchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [GlyphiconsBatchComponent]
    })
        .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GlyphiconsBatchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
