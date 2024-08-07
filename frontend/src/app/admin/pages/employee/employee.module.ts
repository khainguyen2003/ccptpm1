import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
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
import { SkeletonModule } from 'primeng/skeleton';
import { MenubarModule } from 'primeng/menubar';
import { MenuModule } from 'primeng/menu';
import { MultiSelectModule } from 'primeng/multiselect';
import { TabViewModule } from 'primeng/tabview';
import { EditorModule } from 'primeng/editor';
import { AddDepartmentModalComponent } from '../../components/add-department-modal/add-department-modal.component';
import { CarouselModule } from 'primeng/carousel';
import { BadgeModule } from 'primeng/badge';
import { AddCategoryModalComponent } from '../../components/add-category-modal/add-category-modal.component';
import { PanelModule } from 'primeng/panel';
import { ListboxModule } from 'primeng/listbox';
import { RadioFilterComponent } from '../../components/radio-filter/radio-filter.component';
import { CheckboxFilterComponent } from '../../components/checkbox-filter/checkbox-filter.component';
import { DateRangeFilterComponent } from '../../components/date-range-filter/date-range-filter.component';
import { MultiSelectFilterComponent } from '../../components/multi-select-filter/multi-select-filter.component';
import { EmployeeComponent } from './employee.component';
import { EmployeeRoutingModule } from './employee-routing.module';
import { MessageService } from 'primeng/api';

@NgModule({
    imports: [
        CommonModule,
        TableModule,
        BadgeModule,
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
        EmployeeRoutingModule,
        SkeletonModule,
        MenubarModule,
        MultiSelectModule,
        TabViewModule,
        EditorModule,
        AddDepartmentModalComponent,
        AddCategoryModalComponent,
        CarouselModule,
        PanelModule,
        ListboxModule,
        RadioFilterComponent,
        CheckboxFilterComponent,
        DateRangeFilterComponent,
        MultiSelectFilterComponent
    ],
    declarations: [
        EmployeeComponent,
    ],
    providers: [
        MessageService,
    ]
})
export class EmployeeModule { }
