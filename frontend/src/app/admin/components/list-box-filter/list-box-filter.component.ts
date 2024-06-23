import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { PanelModule } from 'primeng/panel';

@Component({
  selector: 'app-list-box-filter',
  standalone: true,
  imports: [
    CommonModule,
    PanelModule,
  ],
  templateUrl: './list-box-filter.component.html',
  styleUrl: './list-box-filter.component.scss'
})
export class ListBoxFilterComponent {

  @Input() label: string;
  @Input() placeholder: string;
  @Input() options: any[];
  @Output() select = new EventEmitter<any>();

  handleSelected(event: any) {
    
  }

}
