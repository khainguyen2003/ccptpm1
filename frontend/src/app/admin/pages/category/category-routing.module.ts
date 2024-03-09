import { Product } from 'src/app/demo/api/product';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ProductComponent } from './category.component';

@NgModule({
	imports: [RouterModule.forChild([
		{ path: '', component: ProductComponent }
	])],
	exports: [RouterModule]
})
export class ProductRoutingModule { }
