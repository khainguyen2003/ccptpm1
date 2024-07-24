import { Routes } from '@angular/router';
import { AppLayoutComponent } from './admin/layout/app.layout.component';
import { NotfoundComponent } from './admin/pages/notfound/notfound.component';

export const routes: Routes = [
  {
    path: '', component: AppLayoutComponent,
    children: [
        {path: 'admin', loadChildren: () => import('./admin/pages/pages.module').then(m => m.PagesModule)},
    ]
  },
  { 
    path: 'auth', 
    loadChildren: () => import('./admin/pages/auth/auth.module').then(m => m.AuthModule) 
  },
  { path: 'notfound', component: NotfoundComponent },
  { path: '**', redirectTo: '/notfound' },
];
