import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ValueRangeComponent } from './value-range.component';

describe('ValueRangeComponent', () => {
  let component: ValueRangeComponent;
  let fixture: ComponentFixture<ValueRangeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ValueRangeComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ValueRangeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
