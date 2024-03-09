import { LayoutService } from 'src/app/admin/layout/service/app.layout.service';
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { AccessServices } from 'src/app/admin/services/access.service';
import { MessageService } from 'primeng/api';
import { Router } from '@angular/router';
import { StorageService } from 'src/app/admin/services/storage.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styles: [`
        :host ::ng-deep .pi-eye,
        :host ::ng-deep .pi-eye-slash {
            transform:scale(1.6);
            margin-right: 1rem;
            color: var(--primary-color) !important;
        }
    `],
    providers: [MessageService]
})
export class LoginComponent implements OnInit {
    loading = false;
    isLoggedIn = false;
    isLoginFailed = false;
    loginForm: FormGroup;
    submited = false;

    constructor(
        public layoutService: LayoutService, 
        private accessService: AccessServices,
        private router: Router,
        private loginMess : MessageService,
        private storageService: StorageService
    ) { }
    ngOnInit(): void {
        // if(this.storageService.getAccessToken()) {
        //     this.isLoggedIn = true;
        //     this.router.navigateByUrl('/admin');
        // } else {
        this.loginForm = new FormGroup({ 
            email: new FormControl('', [Validators.required, Validators.email]),
            password: new FormControl('', Validators.required)
        });
        // }
    }

    /**
     * Phương thức lấy đối tượng FormControl của form thông qua formControlName
     * </br>form['email']
     */
    get form(): { [key: string]: AbstractControl } {
        return this.loginForm.controls;
    }

    login() {
        console.log(this.loginForm);
        
        if(this.loginForm.valid) {
            this.loading = true;
            this.submited = true;
            this.accessService.login(this.loginForm.value).subscribe(
                (response) => {
                //   if(this.storageService.isAdminLoggedIn()) {
                //     this.toastService.success('Đăng nhập thành công thành công', {
                //       dismissible: true
                //     });
                //     this.router.navigateByUrl('admin/dashboard');
                //   } else {
                //     this.toastService.error('Bạn không phải admin', {
                //       dismissible: true
                //     });
                //   }
                    this.loading = false;
                    this.storageService.setAccessToken(response.body.access_token);
                    this.storageService.setUser(response.body.userDetails);
                    this.loginMess.add({ severity: 'success', summary: 'Đăng nhập', detail: 'Đăng nhập thành công' })
                    this.router.navigateByUrl('/admin');
                },
                (error) => {
                    this.loading = false;
                    console.log(error);
                    this.loginMess.add({ severity: 'error', summary: 'Lỗi đăng nhập', detail: error.error.message });
                }
            );
        }
        
    }
}
