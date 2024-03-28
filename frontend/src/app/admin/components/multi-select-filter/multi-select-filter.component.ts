import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { MultiSelectModule } from 'primeng/multiselect';
import { PanelModule } from 'primeng/panel';

@Component({
  selector: 'app-multi-select-filter',
  standalone: true,
  imports: [
    CommonModule,
    PanelModule,
    MultiSelectModule,
    ButtonModule,
  ],
  templateUrl: './multi-select-filter.component.html',
  styleUrl: './multi-select-filter.component.scss'
})
export class MultiSelectFilterComponent {

  @Input() label: string;
  @Input() placeholder: string;
  @Input() options: any[];
  @Output() select = new EventEmitter<any>();

  handleSelected(event: any) {
    this.select.emit(event.value);
  }

  openEditDialog(event: any, value: any) {
    event.stopPropagation();
    console.log("Item to edit: ", value);
  }

}
