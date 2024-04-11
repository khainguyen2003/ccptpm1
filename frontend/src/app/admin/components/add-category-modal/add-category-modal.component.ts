import { AfterViewInit, ChangeDetectorRef, Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { RadioButtonModule } from 'primeng/radiobutton';
import { MultiSelectModule } from 'primeng/multiselect';
import { CategoryService } from '../../services/category.service';
import { catchError, finalize, tap, throwError } from 'rxjs';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'add-category-modal',
  standalone: true,
  imports: [DialogModule, ButtonModule, InputTextModule, RadioButtonModule, MultiSelectModule, FormsModule, ReactiveFormsModule, ToastModule],
  templateUrl: './add-category-modal.component.html',
  styleUrl: './add-category-modal.component.scss',
  providers: [MessageService]
})
export class AddCategoryModalComponent implements OnInit {
  visible: boolean = false;
  addForm!: FormGroup;
  categories: any[];
  category: any = null;
  cities!: any[];
  errorMessage: string = "";
  loading: boolean = false;
  successMessage: string = "";
  @ViewChild('btnSubmit') btnSubmit: ElementRef;

  constructor(
    private cdr: ChangeDetectorRef,
    private categoryService: CategoryService,
    private message: MessageService
  ) { }

  ngOnInit(): void {
    this.addForm = new FormGroup({
      name: new FormControl('', Validators.required),
      parentCates: new FormControl([])
    })
    this.getCategories();
  }
  get form(): { [key: string]: AbstractControl } {
    return this.addForm.controls;
  }
  showDialog() {
      this.visible = true;
  }
  // Thêm department
  save() {
    // Sau khi thực hiện thay đổi
    this.loading = true;
    console.log(this.addForm.value);
    this.categoryService.createCategory(this.addForm.value)
      .pipe(
        tap((res) => {
          this.categories.push(res);
          this.visible = false;
          this.message.add({ severity: 'success', summary: 'Đăng ký', detail: 'Đăng ký thành công' })
        }),
        catchError((err) => {
            this.message.add({ severity: 'error', detail: err.error })
              console.log('Không tải được thông tin sản phẩm', err);
              return throwError(err);
            }),
        finalize(() => (this.loading = false))
      )
      .subscribe();
  }

  // đóng dialog
  hideDialog() {
    this.visible = false;
  }

  getCategories() {
    this.loading = true;
    this.categoryService.getCategories()
    .pipe(
      tap((res) => (this.categories = res.categories)),
          catchError((err) => {
              this.errorMessage = 'Không tải được thông tin sản phẩm';
              console.log('Không tải được thông tin sản phẩm', err);
              return throwError(err);
          }),
          finalize(() => (this.loading = false))
    )
    .subscribe()
  }
  getCategoryById(id: string) {
    this.loading = true;  
    this.categoryService.getCategoryById(id)
      .pipe(
        tap((res) => (this.category = res)),
        catchError((err) => {
              this.errorMessage = 'Không tải được thông tin sản phẩm';
              console.log('Không tải được thông tin sản phẩm', err);
              return throwError(err);
          }),
          finalize(() => (this.loading = false))
      )
      .subscribe();
  }
  deleteCategoryById(id: string) {
    this.loading = true;
    this.categoryService.deleteCategory(id)
      .pipe(
        tap((res) => {
              this.successMessage = res.message;
              this.categories = this.categories.filter(c => c.id !== id);
            }),
        catchError((err) => {
              this.errorMessage = 'Không tải được thông tin sản phẩm';
              console.log('Không tải được thông tin sản phẩm', err);
              return throwError(err);
          }),
          finalize(() => (this.loading = false))
      )
      .subscribe();
  }
  
}
