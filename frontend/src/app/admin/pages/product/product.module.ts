import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ProductComponent } from './product.component';
import { TableModule } from 'primeng/table';
import { FileUploadModule } from 'primeng/fileupload';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { RatingModule } from 'primeng/rating';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { DropdownModule } from 'primeng/dropdown';
import { RadioButtonModule } from 'primeng/radiobutton';
import { InputNumberModule } from 'primeng/inputnumber';
import { DialogModule } from 'primeng/dialog';
import { ProductRoutingModule } from './product-routing.module';
import { ProductService } from '../../services/product.service';
import { SkeletonModule } from 'primeng/skeleton';
import { MenubarModule } from 'primeng/menubar';
import { MenuModule } from 'primeng/menu';
import { MultiSelectModule } from 'primeng/multiselect';
import { TabViewModule } from 'primeng/tabview';
import { EditorModule } from 'primeng/editor';
import { AddDepartmentModalComponent } from '../../components/add-department-modal/add-department-modal.component';

@NgModule({
    imports: [
        CommonModule,
        TableModule,
        FileUploadModule,
        FormsModule,
        ReactiveFormsModule,
        ButtonModule,
        MenuModule,
        RippleModule,
        ToastModule,
        ToolbarModule,
        RatingModule,
        InputTextModule,
        InputTextareaModule,
        DropdownModule,
        RadioButtonModule,
        InputNumberModule,
        DialogModule,
        ProductRoutingModule,
        SkeletonModule,
        MenubarModule,
        MultiSelectModule,
        TabViewModule,
        EditorModule,
        AddDepartmentModalComponent
    ],
    providers: [ProductService],
    declarations: [ProductComponent]
})
export class ProductModule { }
