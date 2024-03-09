import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { LayoutService } from "./service/app.layout.service";

@Component({
    selector: 'app-topbar',
    templateUrl: './app.topbar.component.html',
    styleUrl: './app.topbar.scss'
})
export class AppTopBarComponent implements OnInit {

    items!: MenuItem[];
    userMenus: MenuItem[] | undefined;
    mainFunctions: MenuItem[] | undefined;

    @ViewChild('menubutton') menuButton!: ElementRef;

    @ViewChild('topbarmenubutton') topbarMenuButton!: ElementRef;

    @ViewChild('topbarmenu') menu!: ElementRef;

    ngOnInit() { 
        this.userMenus = [
            {
                label: 'Đăng nhập',
                icon: 'pi pi-fw pi-sign-in mr-2',
                routerLink: '/auth/login'
            },
        ];
        this.mainFunctions = [
            {
                label: 'Tổng quan',
                icon: 'pi pi-fw pi-eye',
            },
            {
                label: 'Hàng hóa',
                icon: 'pi pi-fw pi-box',
                items: [
                    {
                        label: 'Danh mục',
                        icon: 'pi pi-fw pi-table',
                        routerLink: "/admin/products"
                    },
                    {
                        label: 'Thiết lập giá',
                        icon: 'pi pi-fw pi-tags',
                        routerLink: "/admin/pricebook"
                    }
                ]
            },
            {
                label: 'Nhân viên',
                icon: 'pi pi-fw pi-users',
                items: [
                    {
                        label: 'Nhân viên',
                        icon: 'pi pi-fw pi-users',
                        routerLink: "/admin/employees"
                    },
                    {
                        label: 'Bảng chấm công',
                        icon: 'pi pi-fw pi-table',
                        routerLink: "/admin/timesheet"
                    },
                    {
                        label: 'Bảng tính lương',
                        icon: 'pi pi-fw pi-money-bill',
                        routerLink: "/admin/paysheet"
                    }
                ]
            },
            {
                label: 'Sổ quỹ',
                icon: 'pi pi-fw pi-bitcoin',
                routerLink: "/admin/cashflow"
            },
            {
                label: 'Báo cáo',
                icon: 'pi pi-fw pi-chart-bar',
                items: [
                    {
                        label: 'Cuối ngày',
                        icon: 'pi pi-fw pi-chart-pie',
                        routerLink: "/admin/endOfDayReport"
                    },
                    {
                        label: 'Bán hàng',
                        icon: 'pi pi-fw pi-copy',
                        routerLink: "/admin/sellReport"
                    }, 
                    {
                        label: 'Nhân viên',
                        icon: 'pi pi-fw pi-users',
                        routerLink: "/admin/employeeReport"
                    }
                ]
            }
        ];
    }

    constructor(public layoutService: LayoutService) { }
}
