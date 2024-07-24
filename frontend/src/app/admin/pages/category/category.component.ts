import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { LazyLoadEvent, MessageService } from 'primeng/api';
import { Table, TableModule } from 'primeng/table';
import { Category } from 'src/app/models/Category';
import { CategoryService } from '../../services/category.service';
import { catchError, tap, finalize, startWith, of } from 'rxjs';
import { HttpServices } from '../../services/http.service';
import { DialogModule } from 'primeng/dialog';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MultiSelectModule } from 'primeng/multiselect';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { CheckboxModule } from 'primeng/checkbox';
import { InputTextareaModule } from 'primeng/inputtextarea';

@Component({
    selector: 'app-category',
    standalone: true,
    imports: [
        DialogModule, TableModule, ToastModule, ToolbarModule, CommonModule, FormsModule, ReactiveFormsModule, MultiSelectModule, InputTextModule, ButtonModule, 
        CheckboxModule, InputTextareaModule],
    templateUrl: './category.component.html',
    styleUrl: './category.component.scss',
    providers: [MessageService]
})
export class CategoryComponent implements AfterViewInit {
    isLoading: boolean = false;
    deleteCategoryDialog: boolean = false;
    delMultiple: boolean = false;
    
    categoryResponse: any;
    categories: Category[] = [];
    category:Category;
    selectedCategories: Category[] = [];
    // submitted: boolean = false;
    cols: any[] = [];
    statuses: any[] = [];
    rowsPerPageOptions = [5, 10, 20];
    countPage: number = 1;
    pageNum: number = 0;
    pageSize: number = 10;

    

    constructor(
        private categoryService: CategoryService,
        private messageService: MessageService,
        private httpServices: HttpServices
    ) { 
    }
    ngAfterViewInit(): void {
        this.loadCategories();
    }

    loadCategories(event?: LazyLoadEvent) {
        this.isLoading = true;
        var sort: string = this.httpServices.getSort(event?.sortField ?? 'id', event?.sortOrder);
        this.categoryService.getCategories(
            JSON.stringify(event?.filters) ?? '',
            sort,
            event?.first ?? 0,
            event?.rows ?? 10
        )
        .pipe(
            tap((res) => {
                this.isLoading = false;
                this.categories = res?.items ?? [];
            }),
            catchError((err) => {
                this.messageService.add({ severity: 'warn', summary: 'Warning', detail: err.error, life: 3000 });
                return of(null);
            }),
            finalize(() => (this.isLoading = false))
        ).subscribe();
    }

    getCategoryById(id: string) {
    this.isLoading = true;  
    this.categoryService.getCategoryById(id)
        .pipe(
        tap((res) => (this.category = res)),
        catchError((err) => {
                this.messageService.add({ severity: 'success', summary: 'Successful', detail: err, life: 3000 });
                return of(null);
            }),
            finalize(() => (this.isLoading = false))
        )
        .subscribe();
    }

    
    showDelDialog() {
        this.deleteCategoryDialog = true;
        this.delMultiple = false;
    }
    deleteSelectedCategories() {
        this.deleteCategoryDialog = true;
        this.delMultiple = true;
    }

    confirmDeleteSelected() {
        this.deleteCategoryDialog = false;
        this.delMultiple = false;
        this.categories = this.categories.filter(val => !this.selectedCategories.includes(val));
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Đã xóa danh sách thể loại được chọn', life: 3000 });
        this.selectedCategories = [];
    }

    confirmDelete() {
        this.deleteCategoryDialog = false;
        this.categoryService.deleteCategory(this.category.id).subscribe();
    }

    onGlobalFilter(table: Table, event: Event) {
        table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
    }
}