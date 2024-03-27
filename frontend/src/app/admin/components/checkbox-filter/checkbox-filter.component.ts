import { filter } from 'rxjs/operators';
import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { CheckboxModule } from 'primeng/checkbox';
import { PanelModule } from 'primeng/panel';

@Component({
  selector: 'app-checkbox-filter',
  standalone: true,
  imports: [PanelModule, CommonModule, CheckboxModule],
  templateUrl: './checkbox-filter.component.html',
  styleUrl: './checkbox-filter.component.scss'
})
export class CheckboxFilterComponent implements OnInit, OnChanges {
  @Input() filterList!: any;
  @Input() title!: string;
  @Input() name: string = '';
  @Output() filterSelected = new EventEmitter<any[]>();
  optSelecteds: Array<any>;
  
  ngOnInit(): void {
    this.updateOptSelecteds();
  }
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['filterList']) {
      this.updateOptSelecteds();
    }
    
  }
  updateOptSelecteds(): void {
    console.log([0]);
    
    this.filterList.items.map(item => {
      console.log(item);
      
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
