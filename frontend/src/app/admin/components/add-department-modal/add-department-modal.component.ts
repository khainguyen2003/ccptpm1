import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { RadioButtonModule } from 'primeng/radiobutton';

@Component({
  selector: 'add-department-modal',
  standalone: true,
  imports: [DialogModule, ButtonModule, InputTextModule, RadioButtonModule],
  templateUrl: './add-department-modal.component.html',
  styleUrl: './add-department-modal.component.scss'
})
export class AddDepartmentModalComponent implements OnInit {
  visible: boolean = false;
  addDepartmentForm!: FormGroup;
  
  ngOnInit(): void {
    this.addDepartmentForm = new FormGroup({
      name: new FormControl('', Validators.required),
      desc: new FormControl(''),
      status: new FormControl(1)
    })
  }
  showDialog() {
      this.visible = true;
  }
  // Thêm department
  save() {
    console.log(this.addDepartmentForm.value);
  }

  // đóng dialog
  hideDialog() {
    this.visible = false;
  }
}
