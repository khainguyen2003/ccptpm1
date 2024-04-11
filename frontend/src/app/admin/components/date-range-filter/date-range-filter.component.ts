import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { CalendarModule } from 'primeng/calendar';
import { PanelModule } from 'primeng/panel';

@Component({
  selector: 'app-date-range-filter',
  standalone: true,
  imports: [PanelModule, CommonModule, CalendarModule, FormsModule, ButtonModule],
  templateUrl: './date-range-filter.component.html',
  styleUrl: './date-range-filter.component.scss'
})
export class DateRangeFilterComponent implements OnInit {
  @Input() rangeDates: Date[] | undefined;
  @Input() appendTo: any;
  @Input() title!: string;
  @Input() name: string = '';
  @Input() minDate: Date | null = null;
  @Input() maxDate: Date | null = null;
  @Output() filterSelected = new EventEmitter<any[]>();
  rangeChange: Array<any>;
  
  ngOnInit(): void {
      console.log(this.appendTo);
      
  }
  filterChanged(event): void {
    
  }
}
