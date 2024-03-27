import { AddDepartmentModalComponent } from './../../components/add-department-modal/add-department-modal.component';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Product } from 'src/app/demo/api/product';
import { SelectEvent } from 'src/app/models/SelectEvent';
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
import { AddCategoryModalComponent } from '../../components/add-category-modal/add-category-modal.component';

@Component({
    templateUrl: './product.component.html',
    styleUrl: './product.component.scss',
    providers: [MessageService],
})
export class ProductComponent implements OnInit {
    // For dialog
    // addDialogVisible: boolean = false;
    // updateDiaVisible: boolean = false;
    productDiaVisible: boolean = false;
    editDialogVisible: boolean = false;
    importFileDialogVisible: boolean = false;
    deleteProductDialogVisible: boolean = false;
    deleteProductsDialogVisible: boolean = false;
    // -- end for dialog
    // For filter
    slcInvenStatus = {
        name: 'invenFilter',
        items: [
            {
                label: 'Tất cả',
                checked: true,
                value: 0
            },
            {
                label: 'Dưới định mức tồn',
                value: 1
            },
            {
                label: 'Vượt định mức tồn',
                value: 2
            },
            {
                label: 'Còn hàng trong kho',
                value: 3
            },
            {
                label: 'Hết hàng',
                value: 4
            },
            {
                label: 'Lựa chọn khác',
                value: 5
            },
        ]
    };
    proTypeFilter = {
        name: 'typeFilter',
        items: [
            {
                label: 'Hàng hóa',
                checked: true,
                value: 0
            },
            {
                label: 'Dịch vụ',
                value: 1
            },
            {
                label: 'Combo - Đóng gói',
                value: 2
            }
        ]
    };
    slcSellStatus: string = '0';
    search: string | undefined;
    selectedCat: SelectItem = { value: '' };
    // --  end for filter
    // for interact with product
    products: any[] = [];
    product: Product = {};
    selectedProducts: Product[] = [];
    submitted: boolean = false;
    headerProductDia: string = 'Thêm hàng';
    //-- end for interact with product
    cols: any[] = [];
    expandedRows: expandedRows = {};
    isExpanded: boolean = false;
    statuses: any[] = [];
    rowsPerPageOptions = [10, 25, 50];
    loading = false;
    first = 0;
    rows = 10;
    totalRecords!: number;
    errorMessage = '';
    categories: SelectItem[];
    // For upload files
    uploadedFiles: File[] = [];
    selectedFiles: File[] = [];
    imgUploadedUrl: string[] = [
        'https://th.bing.com/th?id=OSK.da7181f009aaebfd25febccdbaca6a04&w=80&h=80&c=7&o=6&dpr=1.3&pid=SANGAM',
        'https://th.bing.com/th?id=OSK.de50cc0c235faefc5d8cd6ba77c8de04&w=80&h=80&c=7&o=6&dpr=1.3&pid=SANGAM',
        'https://th.bing.com/th?id=OSK.3722be2b715b17dba8a171946f8aa0e2&w=80&h=80&c=7&o=6&dpr=1.3&pid=SANGAM',
        'https://th.bing.com/th?id=OSK.7c981adf524b78e11bf03e32204fcd68&w=80&h=80&c=7&o=6&dpr=1.3&pid=SANGAM',
        'https://th.bing.com/th?id=OSK.aa5618135e9f039c89b28eda34197ee1&w=80&h=80&c=7&o=6&dpr=1.3&pid=SANGAM',
    ];
    defaultImg: number[] = [];

    lengthToDisplayDefault: number = 6;
    addForm!: FormGroup;

    // Menu import
    importOptions: MenuItem[] | undefined;
    // add department dialog
    @ViewChild('addDepartment')
    addDepartmentDialog: AddDepartmentModalComponent;
    @ViewChild('addCategory') addCategory: AddCategoryModalComponent;

    constructor(
        private productService: ProductService,
        private message: MessageService
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
        // Lấy dữ liệu cho product
        // this.loadProductsPage();

        // fake dữ liệu danh sách sản phẩm
        this.fakeData();

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

        this.generateIndexForDefault();
    }

    onSelect(event: any) {
        this.selectedFiles = event.target.files;
        this.uploadedFiles.push(event.target.files);
        console.log(event.target.files);
        
        for (let file of event.target.files) {
            const reader = new FileReader();
            reader.onload = (e: any) => {
                console.log(this.imgUploadedUrl);
                if(this.imgUploadedUrl.indexOf(e.target.result) === -1) {
                    this.imgUploadedUrl.push(e.target.result);
                    this.defaultImg.pop();
                    console.log(this.defaultImg);
                }
            };
            reader.readAsDataURL(file);
        }
    }

    removeImg(src: string) {
        this.imgUploadedUrl = this.imgUploadedUrl.filter(item => item !== src);
        if(this.imgUploadedUrl.length < this.lengthToDisplayDefault) {
            this.defaultImg.push(1);
        }
        console.log(this.defaultImg);
        console.log(this.imgUploadedUrl)
    }
    generateIndexForDefault() {
        if(this.imgUploadedUrl.length < this.lengthToDisplayDefault){
            for(var i = this.imgUploadedUrl.length; i < 6; i++) {
                this.defaultImg.push(1);
            }
        } else {
            this.defaultImg = [];
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
    // pagination end

    openNew() {
        this.headerProductDia = 'Thêm hàng';
        this.product = {};
        this.submitted = false;
        this.productDiaVisible = true;
    }

    onUploadJson($event: UploadEvent) {
        console.log($event);
        for (let file of $event.files) {
            this.uploadedFiles.push(file);
        }
    }

    deleteSelectedProducts() {
        this.deleteProductsDialogVisible = true;
    }

    editProduct(product: Product) {
        this.headerProductDia = 'Sửa hàng';
        this.product = { ...product };
        this.productDiaVisible = true;
    }

    deleteProduct(product: Product) {
        this.deleteProductDialogVisible = true;
        this.product = { ...product };
    }

    confirmDeleteSelected() {
        this.deleteProductsDialogVisible = false;
        this.products = this.products.filter(
            (val) => !this.selectedProducts.includes(val)
        );
        this.message.add({
            severity: 'success',
            summary: 'Successful',
            detail: 'Products Deleted',
            life: 3000,
        });
        this.selectedProducts = [];
    }

    confirmDelete() {
        this.deleteProductDialogVisible = false;
        this.products = this.products.filter(
            (val) => val.id !== this.product.id
        );
        this.message.add({
            severity: 'success',
            summary: 'Successful',
            detail: 'Product Deleted',
            life: 3000,
        });
        this.product = {};
    }

    hideDialog() {
        this.productDiaVisible = false;
        this.submitted = false;
    }

    saveProduct() {
        this.submitted = true;
        var addFormData = new FormData();
        this.uploadedFiles.forEach(file => addFormData.append('files', file));
        console.log(addFormData);
        
        // this.productService.createProduct(this.addForm.value)
        // .pipe(
        //     tap((res) => {
        //       this.categories.push(res);
        //       this.loading = false;
        //       this.message.add({ severity: 'success', summary: 'Đăng ký', detail: 'Đăng ký thành công' })
        //     }),
        //     catchError((err) => {
        //         this.message.add({ severity: 'error', detail: err.error })
        //           console.log('Không tải được thông tin sản phẩm', err);
        //           return throwError(err);
        //         }),
        //     finalize(() => (this.loading = false))
        //   )
        //   .subscribe();
        // ;
        
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

    fakeData() {
        this.productService.fakeData().then((data) => {
            this.products = data;
            this.loading = false;
        })
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
        this.importFileDialogVisible = true;
    }

    showAddDepartmentDialog() {
        this.addDepartmentDialog.showDialog();
    }
    showAddCategoryDialog() {
        this.addCategory.showDialog();
    }

    // For filter
    /**
     * 
     * @param option Là giá trị nhận từ emit của thằng con
     */
    selectInvenOpt(option: any) {
        // gọi http service để tạo request filter
        console.log(option);
    }

    slcTypeOpt(options: any) {
        console.log(options);
    }
}

interface UploadEvent {
    originalEvent: Event;
    files: File[];
}

interface expandedRows {
    [key: string]: boolean;
}