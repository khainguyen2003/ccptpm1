import { CommonModule } from '@angular/common';
import { PanelModule } from 'primeng/panel';
import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { RadioButtonModule } from 'primeng/radiobutton';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-radio-filter',
  standalone: true,
  imports: [PanelModule, CommonModule, RadioButtonModule, FormsModule],
  templateUrl: './radio-filter.component.html',
  styleUrl: './radio-filter.component.scss'
})
export class RadioFilterComponent implements OnInit {
  @Input() filterList!: any;
  @Input() title!: string;
  @Input() name: string = '';
  @Output() changeEvent = new EventEmitter<string>();
  filterSelected: string = null; 

  ngOnInit(): void {
    this.filterList.items.forEach(item => {
      if(item?.checked) {
        this.filterSelected = item.value;
        return;
      }
    });
    console.log(this.filterSelected);
    
  }
  filterChanged(event): void {
    this.changeEvent.emit(this.filterSelected);
  }
}
