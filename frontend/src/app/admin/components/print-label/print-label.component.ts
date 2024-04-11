import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { NgxBarcode6Module } from 'ngx-barcode6';
import {NgxPrintModule} from 'ngx-print';

@Component({
  selector: 'app-print-label',
  standalone: true,
  imports: [NgxBarcode6Module, NgxPrintModule, CommonModule],
  templateUrl: './print-label.component.html',
  styleUrl: './print-label.component.scss'
})
export class PrintLabelComponent {
  @Input() data: BarcodeData[];

  generateBarcodeArray(product): any[] {
    let barcodeArray = [];
    for (let i = 0; i < product.quantity; i++) {
      barcodeArray.push(product);
    }
    return barcodeArray;
  }
}

export interface BarcodeData {
  code: string;
  creator?: string;
  title?: string;
  quantity: number; 
}