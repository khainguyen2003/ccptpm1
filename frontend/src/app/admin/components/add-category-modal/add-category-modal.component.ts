import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { RadioButtonModule } from 'primeng/radiobutton';
import { MultiSelectModule } from 'primeng/multiselect';

@Component({
  selector: 'add-category-modal',
  standalone: true,
  imports: [DialogModule, ButtonModule, InputTextModule, RadioButtonModule, MultiSelectModule],
  templateUrl: './add-category-modal.component.html',
  styleUrl: './add-category-modal.component.scss'
})
export class AddCategoryModalComponent implements OnInit {
  visible: boolean = false;
  addForm!: FormGroup;
  categories: any[] = ['abc', 'cde'];
  cities!: any[];
  @ViewChild('btnSubmit') btnSubmit: ElementRef;
  
  ngOnInit(): void {
    this.addForm = new FormGroup({
      name: new FormControl('', Validators.required),
      parentCates: new FormControl([])
    })
      this.cities = [
        { name: 'New York', code: 'NY' },
        { name: 'Rome', code: 'RM' },
        { name: 'London', code: 'LDN' },
        { name: 'Istanbul', code: 'IST' },
        { name: 'Paris', code: 'PRS' }
    ];
  }
  showDialog() {
      this.visible = true;
  }
  // Thêm department
  save() {
    console.log(this.addForm.value);
  }

  // đóng dialog
  hideDialog() {
    this.visible = false;
  }

  handleSaveButtonClick(){
    this.btnSubmit.nativeElement.click();
  }
}
