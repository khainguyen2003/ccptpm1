import { CategoryService } from './category.service';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from '../../models/product';
import { Observable, map } from 'rxjs';
import { Category } from 'src/app/models/category';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class ProductService {
    private productApi = environment.testApiUrl + '/admin/products';
    constructor(
        private http: HttpClient,
        private catService: CategoryService
    ) {}

    getProducts(
        filter: string,
        sort: string,
        page: number,
        pageSize: number
    ): Observable<any> {
        var params = new HttpParams();
        if(filter && filter.trim() !== '') {
            params.append('filter', filter);
        }
        if(sort && sort.trim() !== '') {
            params.append('sort', sort);
        }
        if(page) {
            params.append('page', page);
        }
        if(pageSize) {
            params.append('size', pageSize);
        }
        var headers: HttpHeaders = new HttpHeaders({
            'Content-Type':  'application/json',
            Authorization: 'my-auth-token'
        })

        return this.http
            .get(this.productApi, {
                params, headers
            })
            .pipe(map((res) => res['payload']));
    }

    getProductById(productId: number): Observable<Product> {
        return this.http.get<Product>(this.productApi + '/' + productId);
    }
  
    // findCategoriesContain(productId:number): Observable<any> {
    //     return this.catService.
    // }
    createProduct(form: any): Observable<any> {
        return this.http.post(this.productApi, form);
    }

    fakeData(
    ) {
        return this.http.get<any>('assets/demo/data/products.json')
            .toPromise()
            .then(res => res.data)
    }
}
