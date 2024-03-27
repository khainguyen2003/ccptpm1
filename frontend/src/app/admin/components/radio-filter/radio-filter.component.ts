import { CommonModule } from '@angular/common';
import { PanelModule } from 'primeng/panel';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { RadioButtonModule } from 'primeng/radiobutton';

@Component({
  selector: 'app-radio-filter',
  standalone: true,
  imports: [PanelModule, CommonModule, RadioButtonModule],
  templateUrl: './radio-filter.component.html',
  styleUrl: './radio-filter.component.scss'
})
export class RadioFilterComponent {
  @Input() filterList!: any;
  @Input() title!: string;
  @Input() name: string = '';
  @Output() filterSelected = new EventEmitter<any>();

  filterChanged(event): void {
    if(event.target.checked) {
      this.filterSelected.emit(event.target.value);
    }
  }
}
