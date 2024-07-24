import { HttpServices } from './../../services/http.service';
import { AddDepartmentModalComponent } from './../../components/add-department-modal/add-department-modal.component';
import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import {
    LazyLoadEvent,
    MenuItem,
    MessageService,
    SelectItem,
    SortEvent,
} from 'primeng/api';
import { Table, TableModule } from 'primeng/table';
import {
    catchError,
    debounceTime,
    distinctUntilChanged,
    finalize,
    tap,
    throwError,
    of as observableOf,
} from 'rxjs';
import { ProductService } from '../../services/product.service';
import {
    AbstractControl,
    FormArray,
    FormBuilder,
    FormControl,
    FormGroup,
    FormsModule,
    ReactiveFormsModule,
    Validators
} from '@angular/forms';
import { Product } from 'src/app/models/Product';
import {UploadEvent} from '../../../models/UploadEvent';import { expandedRows } from 'src/app/models/ExpandedRows';
import { CategoryComponent } from '../category/category.component';
import { SaveCategoryModalComponent } from '../../components/save-category-modal/save-category-modal.component';
import { PrintLabelComponent } from '../../components/print-label/print-label.component';
import { MultiSelectFilterComponent } from '../../components/multi-select-filter/multi-select-filter.component';
import { DateRangeFilterComponent } from '../../components/date-range-filter/date-range-filter.component';
import { CheckboxFilterComponent } from '../../components/checkbox-filter/checkbox-filter.component';
import { RadioFilterComponent } from '../../components/radio-filter/radio-filter.component';
import { ListboxModule } from 'primeng/listbox';
import { PanelModule } from 'primeng/panel';
import { CarouselModule } from 'primeng/carousel';
import { EditorModule } from 'primeng/editor';
import { CommonModule } from '@angular/common';
import { BadgeModule } from 'primeng/badge';
import { FileUploadModule } from 'primeng/fileupload';
import { ButtonModule } from 'primeng/button';
import { MenuModule } from 'primeng/menu';
import { ToolbarModule } from 'primeng/toolbar';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { DropdownModule } from 'primeng/dropdown';
import { RadioButtonModule } from 'primeng/radiobutton';
import { InputNumberModule } from 'primeng/inputnumber';
import { DialogModule } from 'primeng/dialog';
import { ProductRoutingModule } from './product-routing.module';
import { SkeletonModule } from 'primeng/skeleton';
import { MenubarModule } from 'primeng/menubar';
import { MultiSelectModule } from 'primeng/multiselect';
import { TabViewModule } from 'primeng/tabview';
import { RatingModule } from 'primeng/rating';
import { AppFooterComponent } from '../../layout/app.footer.component';

@Component({
    templateUrl: './product.component.html',
    styleUrl: './product.component.scss',
    standalone: true,
    imports: [
        CommonModule,
        TableModule,
        FileUploadModule,
        FormsModule,
        ReactiveFormsModule,
        ButtonModule,
        MenuModule,
        ToolbarModule,
        RatingModule,
        InputTextModule,
        InputTextareaModule,
        DropdownModule,
        RadioButtonModule,
        InputNumberModule,
        DialogModule,
        SkeletonModule,
        MenubarModule,
        MultiSelectModule,
        TabViewModule,
        EditorModule,
        CarouselModule,
        PanelModule,
        ListboxModule,
        RadioFilterComponent,
        CheckboxFilterComponent,
        DateRangeFilterComponent,
        DialogModule,
        MultiSelectFilterComponent,
        PrintLabelComponent,
        AddDepartmentModalComponent,
        SaveCategoryModalComponent,
    ],
    providers: [MessageService],
})
export class ProductComponent implements OnInit, AfterViewInit {
    // For dialog
    productDiaVisible: boolean = false;
    editDialogVisible: boolean = false;
    importFileDialogVisible: boolean = false;
    deleteProductDialogVisible: boolean = false;
    deleteProductsDialogVisible: boolean = false;
    // -- end for dialog
    //----------------- For filter
    // http parameters
    filter: Filter;
    sort: string = '';

    // -- For filter section
    @ViewChild('filterSection') filterSection: ElementRef;
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
    slcSellStatus = {
        name: 'invenFilter',
        items: [
            {
                label: 'Tất cả',
                value: 0
            },
            {
                label: 'Hàng đang kinh doanh',
                checked: true,
                value: 1
            },
            {
                label: 'Hàng ngừng kinh doanh',
                value: 2
            }
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
    positionFilter = [
        {
            name: 'Nguyên xá',
            checked: true,
            value: 0
        },
        {
            name: 'Trường đại học Công nghiệp Hà Nội',
            value: 1
        },
        {
            name: 'Ngõ 11, Nguyên Xá Quận Bắc Từ Liêm, Hà Nội',
            value: 2
        }
    ];
    catFilter: any[];
    search: string | undefined;
    selectedCat: SelectItem = { value: '' };
    // for date filter
    minDateFilter: Date = new Date();
    // --  end for filter

    // for print barcode
    // barcodeData: BarcodeData[];
    // -- end for print barcode

    // for interact with product
    products: Product[] = [];
    product: Product = {};
    selectedProducts: Product[] = [];
    submitted: boolean = false;
    headerProductDia: string = 'Thêm hàng';
    //-- end for interact with product

    cols: any[] = [];
    expandedRows: expandedRows = {};
    isExpanded: boolean = false;
    rowsPerPageOptions = [10, 25, 50];
    loading = false;
    first = 0;
    rows = 10;
    totalRecords!: number;
    errorMessage = '';

    // For upload files
    uploadedFiles: File[] = [];
    selectedFiles: File[] = [];
    defaultImgs: string[] = [];
    imgUploadedUrl: string[] = [];
    defaultImgUrl = "https://static.vecteezy.com/system/resources/previews/004/141/669/non_2x/no-photo-or-blank-image-icon-loading-images-or-missing-image-mark-image-not-available-or-image-coming-soon-sign-simple-nature-silhouette-in-frame-isolated-illustration-vector.jpg";
    lengthToDisplayDefault: number = 6;
    saveForm!: FormGroup;

    // Menu import
    importOptions: MenuItem[] | undefined;
    // add department dialog
    @ViewChild('addDepartment')
    addDepartmentDialog: AddDepartmentModalComponent;
    @ViewChild('saveCategory') saveCategory: SaveCategoryModalComponent;

    constructor(
        private productService: ProductService,
        private message: MessageService,
        private httpServices: HttpServices,
        private fb: FormBuilder
    ) {}
    ngAfterViewInit(): void {
        this.loadProductsPage();
    }

    ngOnInit() {
        this.saveForm = new FormGroup({
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
            newImages: new FormControl(null),
            thumb: new FormControl(null, Validators.required),
            attrs: new FormArray([]),
            units: new FormArray([]),
        });

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

        this.catFilter = [
            {
                name: 'Abc',
                value: 'abc',
            },
            {
                name: 'cc',
                value: 'cc',
            },
        ];
        

        this.generateDefaultImgs();
    }

    saveProduct() {
        this.submitted = true;
        const saveFormData = this.createFormData(this.saveForm);
        this.uploadedFiles.forEach(file => saveFormData.append('new_images', file));

        console.log(saveFormData);
        
        this.productService.createProduct(saveFormData)
        .pipe(
            tap((res) => {
            //   this.categories.push(res);
              this.loading = false;
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

    loadProductsPage(event?: LazyLoadEvent) {
        this.loading = true;
        
        this.productService
            .getProducts(
                JSON.stringify(event?.filters) ?? '',
                this.httpServices.getSort(event?.sortField ?? 'id', event?.sortOrder),
                event?.first ?? 0,
                event?.rows ?? 10,
            )
            .pipe(
                tap((res) =>{
                    this.loading = false;
                    this.products = res?.items ?? [];
                    this.totalRecords = res?.totalElements;
                    this.first = res?.curPage;
                } ),
                catchError((err) => {
                    console.log(err);
                
                    this.message.add({ severity: 'warn', summary: 'Warning', detail: err.message, life: 3000 });
                    return observableOf(null);
                })
            )
            .subscribe();
    }

    thumbChanged(event: any) {
        if(event.target?.files?.length > 0) {
            const file = event.target.files[0];
            this.saveForm.patchValue({
                thumb: file
            })
        }
    }

    onSelect(event: any) {
        this.selectedFiles = event.target?.files;
        this.uploadedFiles.push(event.target.files);
        
        this.saveForm.patchValue({
            newImages: this.uploadedFiles
        });
        
        for (let file of event.target.files) {
            const reader = new FileReader();
            reader.onload = (e: any) => {
                if(this.imgUploadedUrl.indexOf(e.target.result) === -1) {
                    this.imgUploadedUrl.push(e.target.result);
                    this.defaultImgs.pop();
                }
            };
            reader.readAsDataURL(file);
        }
    }

    removeImg(src: string) {
        this.imgUploadedUrl = this.imgUploadedUrl.filter(item => item !== src);
        if(this.imgUploadedUrl?.length < this.lengthToDisplayDefault) {
            this.defaultImgs.push(this.defaultImgUrl);
        }
    }
    generateDefaultImgs() {
        if(this.imgUploadedUrl?.length < this.lengthToDisplayDefault){
            for(var i = this.imgUploadedUrl.length; i < this.lengthToDisplayDefault; i++) {
                this.defaultImgs.push(this.defaultImgUrl);
            }
        } else {
            this.defaultImgs = [];
        }
    }

    /**
     * Phương thức lấy đối tượng FormControl của form thông qua formControlName
     * </br>form['email']
     */
    get form(): { [key: string]: AbstractControl } {
        return this.saveForm.controls;
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
        return this.first === this.products?.length - this.rows
            ? true
            : false;
    }

    isFirstPage(): boolean {
        return this.first === 0? true : false;
    }

    // Sort
    customSort(event: LazyLoadEvent) {
        console.log("sort: "+ event);
        
    }
    // pagination end

    openNew() {
        this.headerProductDia = 'Thêm hàng';
        this.generateDefaultImgs();
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

    onGlobalFilter(table: Table, event: Event) {
        table.filterGlobal(
            (event.target as HTMLInputElement).value,
            'contains'
        );
    }

    showImportFileDialog() {
        this.importFileDialogVisible = true;
    }

    showAddDepartmentDialog() {
        this.addDepartmentDialog.showDialog();
    }
    showAddCategoryDialog() {
        this.saveCategory.showSaveCategoryDialog();
    }

    // For filter
    /**
     * 
     * @param option Là giá trị nhận từ emit của thằng con
     */
    selectInvenOpt(option: string[]) {
        // gọi http service để tạo request filter
        console.log(option);
        //this.filter.onHandFilter = option[0];
    }

    slcTypeOpt(options: any[]) {
        console.log(options);
    }
    slcPosOpt(options: any[]) {
        console.log(options);
    }

    createFormData(formGroup: FormGroup): FormData {
        const formData = new FormData();
        Object.keys(formGroup.controls).forEach(key => {
            const control = formGroup.get(key);
            if (control) {
            if(control.value instanceof File) {
                formData.append(key, control.value as Blob);  
            } else {
                formData.append(key, control.value);
            }
            }
        });

        return formData;
    }

    get attrs() {
        return this.saveForm.get('attrs') as FormArray;
    }
    addAttrs() {
        this.attrs.push(this.fb.control(''));
    }
    removeAttrs(index) {
        this.attrs.removeAt(index);
    }

    get units() {
        return this.saveForm.get('units') as FormArray;
    }
    addUnits() {
        this.units.push(this.fb.control(''));
    }
    removeUnits(index) {
        this.units.removeAt(index);
    }
}

interface Filter {
    categories:  number[],
    attrFilter: string[],
    supplierIds: number[],
    allowSale: number, // 0 -> all, 1 -> Đang kinh doanh, -1 -> Ngừng kinh doanh
    // alltime: toàn thời gian | other để chỉ định thời gian bắt đầu và kết thúc | thời gian chỉ định, ví dụ afterThreeDay, ...
    stockOutDate: string, 
    stockoutStartDate: string, // thời gian bắt đầu
    stockOutEndDate: string, // thời gian kết thúc
    onHandFilter: number, // theo thứ tự từ  0-4 | -1 để nhập giá trị
    onHandFilterStr: string, // >= 10 => str= >=:10
    branchIds: number[],
    directSell: number, // 0 -> all, 1 -> bán trực tiếp, -1 -> ko bán trực tiếp
    relateToChanel: number, // tương tự như trên 
}