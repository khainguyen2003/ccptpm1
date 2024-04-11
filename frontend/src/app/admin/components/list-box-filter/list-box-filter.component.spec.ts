import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListBoxFilterComponent } from './list-box-filter.component';

describe('ListBoxFilterComponent', () => {
  let component: ListBoxFilterComponent;
  let fixture: ComponentFixture<ListBoxFilterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListBoxFilterComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ListBoxFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
