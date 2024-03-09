import { AddDepartmentModalComponent } from './../../components/add-department-modal/add-department-modal.component';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Product } from 'src/app/demo/api/product';
import { SelectEvent} from 'src/app/models/SelectEvent'
import {
    LazyLoadEvent,
    MenuItem,
    MessageService,
    SelectItem,
    SortEvent,
} from 'primeng/api';
import { Table } from 'primeng/table';
import {
    catchError,
    debounceTime,
    distinctUntilChanged,
    finalize,
    fromEvent,
    merge,
    tap,
    throwError,
} from 'rxjs';
import { ProductService } from '../../services/product.service';
import {
    AbstractControl,
    FormControl,
    FormGroup,
    Validators,
} from '@angular/forms';

@Component({
    templateUrl: './product.component.html',
    styles: [`
        :host ::ng-deep .p-fileupload .p-fileupload-buttonbar {
            display: none;
        }
    `],
    providers: [MessageService],
})
export class ProductComponent implements OnInit {
    productDialog: boolean = false;
    importFileDialog: boolean = false;
    deleteProductDialog: boolean = false;
    deleteProductsDialog: boolean = false;
    products: Product[] = [];
    product: Product = {};
    selectedProducts: Product[] = [];
    submitted: boolean = false;
    cols: any[] = [];
    statuses: any[] = [];
    rowsPerPageOptions = [10, 25, 50];
    loading = false;
    first = 0;
    rows = 10;
    totalRecords!: number;
    errorMessage = '';
    uploadedFiles: any[] = [];
    categories: SelectItem[];
    selectedFiles: any[] = [];
    selectedImgsUrl: string[] = [];

    addForm!: FormGroup;

    // Menu import
    importOptions: MenuItem[] | undefined;
    // add department dialog
    @ViewChild('addDepartment') addDepartmentDialog: AddDepartmentModalComponent;

    constructor(
        private productService: ProductService,
        private messageService: MessageService
    ) {}

    ngOnInit() {
        this.addForm = new FormGroup({
            code: new FormControl(''),
            name: new FormControl('', Validators.required),
            categories: new FormControl('', Validators.required),
            model: new FormControl('', Validators.required),
            position: new FormControl('', Validators.required),
            importPrice: new FormControl(0, Validators.required),
            sellPrice: new FormControl(0),
            minInventory: new FormControl(0),
            maxInventory: new FormControl(0),
            desc: new FormControl(''),
            inventory: new FormControl(0),
        });
        this.loadProductsPage();

        this.cols = [
            { field: 'product', header: 'Product' },
            { field: 'price', header: 'Price' },
            { field: 'category', header: 'Category' },
            { field: 'rating', header: 'Reviews' },
            { field: 'inventoryStatus', header: 'Status' },
        ];

        this.importOptions = [
            {
                label: 'Json',
                icon: 'pi pi-file',
                command: () => {
                    this.showImportFileDialog();
                },
            },
            {
                label: 'Excel',
                icon: 'pi pi-file-excel',
                command: () => {
                    this.showImportFileDialog();
                },
            },
        ];

        this.categories = [
            {
                label: 'Abc',
                value: 'abc',
            },
            {
                label: 'cc',
                value: 'cc',
            },
        ];
    }

    onSelect(event: SelectEvent) {
        for (let file of event.files) {
            this.selectedFiles.push(file);
            const reader = new FileReader();
            reader.onload = (e: any) => {
                this.selectedImgsUrl.push(e.target.result);
            };
            reader.readAsDataURL(file);
        }
    }

    /**
     * Phương thức lấy đối tượng FormControl của form thông qua formControlName
     * </br>form['email']
     */
    get form(): { [key: string]: AbstractControl } {
        return this.addForm.controls;
    }

    // Pagination
    next() {
        this.first = this.first + this.rows;
    }

    prev() {
        this.first = this.first - this.rows;
    }

    reset() {
        this.first = 0;
    }

    pageChange(event) {
        this.first = event.first;
        this.rows = event.rows;
    }

    isLastPage(): boolean {
        return this.products
            ? this.first === this.products.length - this.rows
            : true;
    }

    isFirstPage(): boolean {
        return this.products ? this.first === 0 : true;
    }

    // Sort
    customSort($event: LazyLoadEvent) {
        // event.data.sort((data1, data2) => {
        //     let value1 = data1[event.field];
        //     let value2 = data2[event.field];
        //     let result = null;

        //     if (value1 == null && value2 != null) result = -1;
        //     else if (value1 != null && value2 == null) result = 1;
        //     else if (value1 == null && value2 == null) result = 0;
        //     else if (typeof value1 === 'string' && typeof value2 === 'string') result = value1.localeCompare(value2);
        //     else result = value1 < value2 ? -1 : value1 > value2 ? 1 : 0;

        //     return event.order * result;
        // });
        console.log($event);
    }

    openNew() {
        this.product = {};
        this.submitted = false;
        this.productDialog = true;
    }

    onUploadJson($event: UploadEvent) {
        console.log($event);
        for (let file of $event.files) {
            this.uploadedFiles.push(file);
        }
    }

    deleteSelectedProducts() {
        this.deleteProductsDialog = true;
    }

    editProduct(product: Product) {
        this.product = { ...product };
        this.productDialog = true;
    }

    deleteProduct(product: Product) {
        this.deleteProductDialog = true;
        this.product = { ...product };
    }

    confirmDeleteSelected() {
        this.deleteProductsDialog = false;
        this.products = this.products.filter(
            (val) => !this.selectedProducts.includes(val)
        );
        this.messageService.add({
            severity: 'success',
            summary: 'Successful',
            detail: 'Products Deleted',
            life: 3000,
        });
        this.selectedProducts = [];
    }

    confirmDelete() {
        this.deleteProductDialog = false;
        this.products = this.products.filter(
            (val) => val.id !== this.product.id
        );
        this.messageService.add({
            severity: 'success',
            summary: 'Successful',
            detail: 'Product Deleted',
            life: 3000,
        });
        this.product = {};
    }

    hideDialog() {
        this.productDialog = false;
        this.submitted = false;
    }

    saveProduct() {
        this.submitted = true;

        // if (this.product.name?.trim()) {
        //     if (this.product.id) {
        //         // @ts-ignore
        //         this.product.inventoryStatus = this.product.inventoryStatus
        //             .value
        //             ? this.product.inventoryStatus.value
        //             : this.product.inventoryStatus;
        //         this.products[this.findIndexById(this.product.id)] =
        //             this.product;
        //         this.messageService.add({
        //             severity: 'success',
        //             summary: 'Successful',
        //             detail: 'Product Updated',
        //             life: 3000,
        //         });
        //     } else {
        //         this.product.id = this.createId();
        //         this.product.code = this.createId();
        //         this.product.image = 'product-placeholder.svg';
        //         // @ts-ignore
        //         this.product.inventoryStatus = this.product.inventoryStatus
        //             ? this.product.inventoryStatus.value
        //             : 'INSTOCK';
        //         this.products.push(this.product);
        //         this.messageService.add({
        //             severity: 'success',
        //             summary: 'Successful',
        //             detail: 'Product Created',
        //             life: 3000,
        //         });
        //     }

        //     this.products = [...this.products];
        //     this.productDialog = false;
        //     this.product = {};
        // }
    }

    findIndexById(id: string): number {
        let index = -1;
        for (let i = 0; i < this.products.length; i++) {
            if (this.products[i].id === id) {
                index = i;
                break;
            }
        }

        return index;
    }

    onGlobalFilter(table: Table, event: Event) {
        table.filterGlobal(
            (event.target as HTMLInputElement).value,
            'contains'
        );
    }

    loadProductsPage($event?: LazyLoadEvent) {
        this.loading = true;

        this.productService
            .getProducts(
                JSON.stringify($event?.filters) ?? '',
                $event?.sortOrder ?? 1,
                $event?.first ?? 0,
                $event?.rows ?? 10,
                $event?.sortField ?? 'id'
            )
            .pipe(
                tap((res) => (this.products = res.products)),
                catchError((err) => {
                    this.errorMessage = 'Không tải được thông tin sản phẩm';
                    console.log('Không tải được thông tin sản phẩm', err);
                    return throwError(err);
                }),
                finalize(() => (this.loading = false))
            )
            .subscribe();
    }

    showImportFileDialog() {
        this.importFileDialog = true;
    }

    showAddDepartmentDialog() {
        this.addDepartmentDialog.showDialog();
    }
}

interface UploadEvent {
    originalEvent: Event;
    files: File[];
}
