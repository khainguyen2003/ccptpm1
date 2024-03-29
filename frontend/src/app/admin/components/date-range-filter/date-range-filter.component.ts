import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges } from '@angular/core';
import { CalendarModule } from 'primeng/calendar';
import { PanelModule } from 'primeng/panel';

@Component({
  selector: 'app-date-range-filter',
  standalone: true,
  imports: [PanelModule, CommonModule, CalendarModule],
  templateUrl: './date-range-filter.component.html',
  styleUrl: './date-range-filter.component.scss'
})
export class DateRangeFilterComponent implements OnInit  {
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }
  @Input() rangeDates: Date[] | undefined;
  @Input() title!: string;
  @Input() name: string = '';
  @Input() minDate: Date | null;
  @Input() maxDate: Date | null;
  @Output() filterSelected = new EventEmitter<any[]>();
  rangeChange: Array<any>;

  
  
  filterChanged(event): void {
    
  }
}
