import { HttpServices } from './http.service';
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Category } from "src/app/models/Category";
import { PaginationResponse } from 'src/app/models/PaginationResponse';
import { environment } from "src/environments/environment";

@Injectable({
    providedIn: 'root'
})
export class CategoryService {
    
    categoryApi = this.httpServices.getCategoryAdminApi();

    constructor(
        private http: HttpClient,
        private httpServices: HttpServices
    ){}

    getCategories(
        filter: string = "",
        sort: string = "",
        page: number = 0,
        pageSize: number = 10
    ):  Observable<PaginationResponse>{
        var params = this.httpServices.createPaginationParams(filter, sort, page, pageSize);
        var headers: HttpHeaders = this.httpServices.createAuthorizationHeader();
        headers.append('Content-Type', 'application/json');
        return this.http.get<PaginationResponse>(this.categoryApi, {
            params, headers
        });
    }
    getCategoryById(id: string): Observable<Category> {
        return this.http.get<Category>(this.categoryApi, {
            params: new HttpParams().set('id', id),
            headers: this.httpServices.createAuthorizationHeader()
        });
    }
    createCategory(form: any): Observable<Category> {
        return this.http.post<Category>(this.categoryApi, form, {
            headers: this.httpServices.createAuthorizationHeader()
        });
    }
    deleteCategory(id: any): Observable<any> {
        return this.http.delete(this.categoryApi + '/' + id, {
            headers: this.httpServices.createAuthorizationHeader()
        });
    }
    updateCategory(form: any, id: number) : Observable<Category> {
        return this.http.put<Category>(`${this.categoryApi}/${id}`, form,{
                headers: this.httpServices.createAuthorizationHeader()
            }
        );
    }
    getCategoriesContainProduct(productId: number): Observable<any> {
        return this.http.get(this.categoryApi, {
            params: new HttpParams()
                .set('pid', productId.toString())
        })
    }

}