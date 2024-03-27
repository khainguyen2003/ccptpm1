import { HttpClient, HttpParams } from '@angular/common/http';
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
    constructor(private http: HttpClient) {}

    getProducts(
        filter = '',
        sortOrder = 1,
        pageNumber = 0,
        pageSize = 3,
        sortField = 'id'
    ): Observable<any> {
        return this.http
            .get(this.productApi, {
                params: new HttpParams()
                    // .set('f', filter)
                    .set('so', sortOrder)
                    .set('p', pageNumber.toString())
                    .set('ps', pageSize.toString())
                    .set('sort', sortField),
            })
            .pipe(map((res) => res['payload']));
    }

    findProductById(productId: number): Observable<Product> {
        return this.http.get<Product>(this.productApi + '/' + productId);
    }
  
    getAllProducts(): Observable<any> {
        return this.http.get(this.productApi)
            .pipe(
                map(res => res['payload'])
            );
    }
  
    findAllProductCategories(catId:number): Observable<Category[]> {
        return this.http.get('/api/categories', {
            params: new HttpParams()
                .set('cid', catId.toString())
                .set('p', "0")
                .set('ps', "1000")
        }).pipe(
            map(res =>  res["payload"])
        );
    }
    // sortOrder = 'asc', pageNumber = 0, pageSize = 3, sortColumn = 'seqNo'
    findProducts(sortOrder = 'asc', pageNumber = 0, pageSize = 3, sortColumn = 'seqNo'):  Observable<any> {       
        return this.http.get(this.productApi, {
            params: new HttpParams()
                .set('o', sortOrder)
                .set('p', pageNumber)
                .set('ps', pageSize)
                .set('s', sortColumn)
        });
    }
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
