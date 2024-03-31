import { CommonModule } from '@angular/common';
import { PanelModule } from 'primeng/panel';
import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { RadioButtonModule } from 'primeng/radiobutton';
import { FormsModule } from '@angular/forms';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';

@Component({
  selector: 'app-radio-filter',
  standalone: true,
  imports: [PanelModule, CommonModule, RadioButtonModule, FormsModule, DropdownModule, InputTextModule],
  templateUrl: './radio-filter.component.html',
  styleUrl: './radio-filter.component.scss'
})
export class RadioFilterComponent implements OnInit {
  @Input() filterList!: any;
  @Input() title!: string;
  @Input() name: string = '';
  @Input() hasOtherOpt: boolean = false;
  @Output() changeEvent = new EventEmitter<string[]>();
  operators: string[] = ["=", '<', '>', '<=', '>=', '!='];
  operator: string = '=';
  value: string = '';
  
  filterSelected: string = null; 
  filterValue: string[];

  ngOnInit(): void {
    this.filterList.items.forEach(item => {
      if(item?.checked) {
        this.filterSelected = item.value;
        return;
      }
    });
    this.filterValue = [this.filterSelected, '']
    if(this.value !== '') {
      this.filterValue[1] = this.operator + ':' + this.value;
    }
    this.changeEvent.emit(this.filterValue);
  }
  filterChanged(event): void {
    this.changeEvent.emit(this.filterValue);
  }
}
