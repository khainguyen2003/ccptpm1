import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddJobtitleDialogComponent } from './add-jobtitle-dialog.component';

describe('AddJobtitleDialogComponent', () => {
  let component: AddJobtitleDialogComponent;
  let fixture: ComponentFixture<AddJobtitleDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddJobtitleDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddJobtitleDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
