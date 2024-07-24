import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';

@NgModule({
    imports: [RouterModule.forChild([
        { path: '', component :DashboardComponent },
        { path: 'empty', loadChildren: () => import('./empty/emptydemo.module').then(m => m.EmptyDemoModule) },
        { path: 'timeline', title: "Timeline page", loadChildren: () => import('./timeline/timelinedemo.module').then(m => m.TimelineDemoModule) },
        { path: 'products', title: "Quản lý sản phẩm", loadChildren: () => import('./product/product.component').then(c => c.ProductComponent) },
        { path: 'categories', title: "Quản lý danh mục", loadChildren: () => import('./category/category.component').then(c => c.CategoryComponent)},
        { path: 'employees', loadChildren: () => import('./employee/employee.module').then(m => m.EmployeeModule)},
        { path: '**', redirectTo: '/notfound' }
    ])],
    exports: [RouterModule]
})
export class PagesRoutingModule { }
