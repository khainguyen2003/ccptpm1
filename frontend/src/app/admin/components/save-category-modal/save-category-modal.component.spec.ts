import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SaveCategoryModalComponent } from './save-category-modal.component';

describe('AddCategoryModalComponent', () => {
  let component: SaveCategoryModalComponent;
  let fixture: ComponentFixture<SaveCategoryModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SaveCategoryModalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SaveCategoryModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
