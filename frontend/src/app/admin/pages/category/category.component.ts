import { Component, OnInit, ViewChild } from '@angular/core';
import { MessageService } from 'primeng/api';
import { Table } from 'primeng/table';
import { Category } from 'src/app/models/category';
import { CategoryService } from '../../services/category.service';
import { fakeCategories } from './fake-data';
import { AddCategoryModalComponent } from '../../components/add-category-modal/add-category-modal.component';

@Component({
    templateUrl: './category.component.html',
    providers: [MessageService]
})
export class CategoryComponent implements OnInit {

    categoryDialog: boolean = false;
    deleteCategoryDialog: boolean = false;
    deleteCategoriesDialog: boolean = false;
    categoryResponse: any;
    categories: Category[] = [];
    category:Category;
    selectedCategories: Category[] = [];
    submitted: boolean = false;
    cols: any[] = [];
    statuses: any[] = [];
    rowsPerPageOptions = [5, 10, 20];
    countPage: number = 1;
    pageNum: number = 0;
    pageSize: number = 10;

    @ViewChild('addCategory') addCategory: AddCategoryModalComponent;

    constructor(
        private categoryService: CategoryService,
        private messageService: MessageService,
    ) { }

    ngOnInit() {
        this.loadCategories();
    }

    loadCategories() {
        this.categoryService.getCategories().subscribe({
            next: res => {
                this.categoryResponse = res;
                this.categories = res.categories;
            },
            error: err => {
                this.categories = fakeCategories;
                this.messageService.add({ severity: 'warn', summary: 'Warning', detail: `Đây là danh sách giả để demo và được tạo ra khi không lấy đc dữ liệu`, life: 3000 });
                console.log(err);
            },
            complete: () => {}
        }).add(() => {
            //Chạy sau khi subcribe xong.
        });
    }

    showAddCategoryDialog() {
        this.addCategory.showDialog();
    }

    deleteSelectedCategories() {
        this.deleteCategoriesDialog = true;
    }

    editCategory(category: Category) {
        this.category = { ...category };
        this.categoryDialog = true;
    }

    saveCategory() {
        this.categoryDialog = false;
        this.categoryService.updateCategory(this.category).subscribe({
            next: res => {
                this.categories = [this.category, ...this.categories.filter(item => item.id !== this.category.id)];
                this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Cập nhật thành công.', life: 3000 });
                console.log(res);
            },
            error: err => {
                this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Cập nhật không thành công.', life: 3000 });
                console.log(err);
            },
            complete: () => {}
        }).add(() => this.category = null);
    }

    deleteCategory(category: Category) {
        this.deleteCategoryDialog = true;
        this.category = { ...category };
    }

    confirmDeleteSelected() {
        this.deleteCategoriesDialog = false;
        this.categories = this.categories.filter(val => !this.selectedCategories.includes(val));
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Đã xóa danh sách thể loại được chọn', life: 3000 });
        this.selectedCategories = [];
    }

    confirmDelete() {
        this.deleteCategoryDialog = false;
        this.categoryService.deleteCategory(this.category.id).subscribe({
            next: res => {
                this.categories = this.categories.filter(val => val.id !== this.category.id);
                this.messageService.add({ severity: 'success', summary: 'Successful', detail: `Đã xóa thể loại ${this.category.name}`, life: 3000 });
                console.log(res);
            },
            error: err => {
                this.messageService.add({ severity: 'error', summary: 'Error', detail: `Xóa thể loại ${this.category.name} không thành công!`, life: 3000 });
                console.log(err);
            },
            complete: () => {}
        }).add(() => {
            this.category = null;
        });
    }

    hideDialog() {
        this.categoryDialog = false;
        this.submitted = false;
        this.category = null;
    }

    findIndexById(id: string): number {
        // let index = -1;
        // for (let i = 0; i < this.products.length; i++) {
        //     if (this.products[i].id === id) {
        //         index = i;
        //         break;
        //     }
        // }

        // return index;
        return 0;
    }

    createId(): string {
        let id = '';
        const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
        for (let i = 0; i < 5; i++) {
            id += chars.charAt(Math.floor(Math.random() * chars.length));
        }
        return id;
    }

    onGlobalFilter(table: Table, event: Event) {
        table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
    }
}