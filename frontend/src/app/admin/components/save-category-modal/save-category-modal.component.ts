import { CategoryService } from '../../services/category.service';
import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { MultiSelectModule } from 'primeng/multiselect';
import { catchError, finalize, of, tap } from 'rxjs';

@Component({
  selector: 'save-category-modal',
  standalone: true,
  imports: [
    CommonModule, FormsModule, ReactiveFormsModule, MultiSelectModule, InputTextModule, InputTextareaModule, DialogModule
  ],
  templateUrl: './save-category-modal.component.html',
  styleUrl: './save-category-modal.component.scss',
  providers: [MessageService]
})
export class SaveCategoryModalComponent {
  saveDialog = false;
  isLoading = false;
  saveForm = this.fb.group({
    name: ['', Validators.required],
    parentCates: [[]],
    notes: [''],
    enabled: [true]
  });
  constructor(private fb: FormBuilder, private categoryService: CategoryService, private messageService: MessageService) {}

  save(id?: number) {
    this.isLoading = true;
    this.saveDialog = false;
    if(id) {
        this.categoryService.updateCategory(this.saveForm, id)
        .pipe(
        tap((res) => {
                this.messageService.add({ severity: 'warn', summary: 'Warning', detail: 'Tạo danh mục thành công', life: 3000 });
                console.log(res);
                
            }),
            catchError((error) => {
                this.messageService.add({ severity: 'warn', summary: 'Warning', detail: error.message, life: 3000 });
                return of(null);
            }),
            finalize(() => {
                this.isLoading = false;
            })
        )
        .subscribe();
    } else {
        this.categoryService.createCategory(this.saveForm);
    }
  }

  showSaveCategoryDialog() {
    this.saveDialog = true;
  }

  hideDialog() {
    this.saveDialog = false;
  }
}
