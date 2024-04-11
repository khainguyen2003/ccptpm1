import { Route } from "@angular/router";
import { DashboardComponent } from "./pages/dashboard/dashboard.component";

export const ADMIN_ROUTES: Route[]= [
    {
        path: '',
        component: DashboardComponent
    }
]