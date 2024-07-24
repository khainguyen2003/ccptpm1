import { Route } from "@angular/router";
import { DashboardComponent } from "./pages/dashboard/dashboard.component";

export const ADMIN_ROUTES: Route[]= [
    { path: '', component :DashboardComponent },
        { path: 'empty', loadChildren: () => import('./pages/empty/emptydemo.module').then(m => m.EmptyDemoModule) },
        { path: 'timeline', loadChildren: () => import('./pages/timeline/timelinedemo.module').then(m => m.TimelineDemoModule) },
        { path: 'products', loadChildren: () => import('./pages/product/product.component').then(c => c.ProductComponent) },
        { path: 'categories', loadChildren: () => import('./pages/category/category.component').then(c => c.CategoryComponent)},
        { path: 'employees', loadChildren: () => import('./pages/employee/employee.module').then(m => m.EmployeeModule)},
        { path: '**', redirectTo: '/notfound' }
]