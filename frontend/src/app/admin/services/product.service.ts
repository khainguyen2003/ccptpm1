import { HttpServices } from './http.service';
import { CategoryService } from './category.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from '../../models/Product';
import { Observable, map } from 'rxjs';
import { Category } from 'src/app/models/Category';
import { environment } from 'src/environments/environment';
import { PaginationResponse } from 'src/app/models/PaginationResponse';
@Injectable({
    providedIn: 'root'
})
export class ProductService {
    private productApi = this.httpServices.getProductAdminApi();
    constructor(
        private http: HttpClient,
        private catService: CategoryService,
        private httpServices: HttpServices
    ) {}

    getProducts(
        filter: string = "",
        sort: string = "",
        page: number = 0,
        pageSize: number = 10
    ): Observable<PaginationResponse> {
        var params = this.httpServices.createPaginationParams(filter, sort, page, pageSize);
        var headers: HttpHeaders = this.httpServices.createAuthorizationHeader();
        headers.append('Content-Type', 'application/json');
        console.log(this.productApi);
        console.log(headers);  

        return this.http
            .get<PaginationResponse>(this.productApi, {
                params, headers
            });
    }

    getProductById(productId: number): Observable<Product> {
        return this.http.get<Product>(this.productApi + '/' + productId);
    }

    createProduct(form: any): Observable<Product> {
        return this.http.post<Product>(this.productApi, form);
    }
    updateProduct(productId: number, form: any): Observable<Product> {
        return this.http.put<Product>(this.productApi + '/' + productId, form);
    }
    deleteProduct(productId: number): Observable<string> {
        return this.http.delete<string>(this.productApi + '/' + productId);
    }
}
