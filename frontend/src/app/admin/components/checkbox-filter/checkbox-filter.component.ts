import { filter } from 'rxjs/operators';
import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { CheckboxModule } from 'primeng/checkbox';
import { PanelModule } from 'primeng/panel';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-checkbox-filter',
  standalone: true,
  imports: [PanelModule, CommonModule, FormsModule, CheckboxModule],
  templateUrl: './checkbox-filter.component.html',
  styleUrl: './checkbox-filter.component.scss'
})
export class CheckboxFilterComponent implements OnInit {
  @Input() filterList!: any;
  @Input() title!: string;
  @Input() name: string = '';
  @Output() filterSelected = new EventEmitter<any[]>();
  optSelecteds: string[] = [];
  
  ngOnInit(): void {
    this.updateOptSelecteds();
  }
  updateOptSelecteds(): void {
    this.filterList.items.map(item => {
      if(item?.checked) {
        this.optSelecteds.push(item?.value);
      }
    });
    
    this.filterSelected.emit(this.optSelecteds);
  }

  filterChanged(event): void {
    if(event.target.checked) {
      this.optSelecteds.push(event.target.value);
    } else {
      this.optSelecteds = this.optSelecteds.filter(item => item !== event.target.value)
    }
    this.filterSelected.emit(this.optSelecteds);
  }
}
