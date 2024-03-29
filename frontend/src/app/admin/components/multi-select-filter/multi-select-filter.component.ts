import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { MultiSelectModule } from 'primeng/multiselect';
import { PanelModule } from 'primeng/panel';

@Component({
  selector: 'app-multi-select-filter',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    PanelModule,
    MultiSelectModule,
    ButtonModule,
  ],
  templateUrl: './multi-select-filter.component.html',
  styleUrl: './multi-select-filter.component.scss'
})
export class MultiSelectFilterComponent implements OnInit {

  @Input() label: string;
  @Input() placeholder: string;
  @Input() options: any[];
  @Output() changeEvent = new EventEmitter<any>();
  optSelecteds: any[] = [];

  ngOnInit(): void {
    this.updateOptSelecteds();
  }
  updateOptSelecteds(): void {
    this.options.map(item => {
      if(item?.checked) {
        this.optSelecteds.push(item);
      }
    });
    
    this.changeEvent.emit(this.optSelecteds);
  }

  handleSelected(event: any) {
    this.changeEvent.emit(this.optSelecteds);
  }

  openEditDialog(event: any, value: any) {
    event.stopPropagation();
    console.log("Item to edit: ", value);
  }

}
