import { AccessServices } from 'src/app/admin/services/access.service';
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { CheckboxModule } from 'primeng/checkbox';
import { FormsModule } from '@angular/forms';
import { PasswordModule } from 'primeng/password';
import { InputTextModule } from 'primeng/inputtext';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
  providers: [MessageService]
})
export class RegisterComponent implements OnInit{
  registerForm!: FormGroup;
  
  loading = false;
  submited = false;
  constructor(
    private router: Router,
    private accessService: AccessServices,
    private registerMess: MessageService
  ) {}
  ngOnInit(): void {
    this.registerForm = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.maxLength(40)]),
      rePass: new FormControl('', [Validators.required]),
      acceptTerms: new FormControl(false, Validators.requiredTrue)
    });
  }

  get form(): { [key: string]: AbstractControl } {
    return this.registerForm.controls;
  }

  match(controlName: string, checkControlName: string): ValidatorFn {
    return (controls: AbstractControl) => {
      const control = controls.get(controlName);
      const checkControl = controls.get(checkControlName);

      if (checkControl?.errors && !checkControl.errors['matching']) {
        return null;
      }

      if (control?.value !== checkControl?.value) {
        controls.get(checkControlName)?.setErrors({ matching: true });
        return { matching: true };
      } else {
        return null;
      }
    };
  }
  
  register() {
    if(!this.registerForm.dirty) {
      this.submited = true;
      var password = this.registerForm.get('password')?.value;
      var rePass = this.registerForm.get('rePass')?.value;
      if(password !== rePass) {
        this.registerMess.add({severity: 'error', detail: "Mật khẩu xác nhận không trùng khớp"})
      } else {
        this.loading = true;
        this.accessService.register(this.registerForm.value).subscribe(
            (response) => {
                this.loading = false;
                this.registerMess.add({ severity: 'success', summary: 'Đăng ký', detail: 'Đăng ký thành công' })
                
                this.router.navigateByUrl('/auth/login');
            },
            (error) => {
                this.loading = false;
                console.log(error);
                this.registerMess.add({ severity: 'error', summary: 'Lỗi đăng ký', detail: 'Đăng ký thất bại' })
            }
        );
      }
    }
  }
}
