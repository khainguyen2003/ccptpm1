import { HeaderService } from './header.service';
import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Category } from "src/app/models/category";
import { environment } from "src/environments/environment";

@Injectable({
    providedIn: 'root'
})
export class CategoryService {
    
    categoryApi = environment.testApiUrl + '/admin/categories';

    constructor(
        private http: HttpClient,
        private headerService: HeaderService
    ){}

    getCategories(value: string = ""):  Observable<any>{
        var params = new HttpParams();
        if(value != null) {
            params.set('filter', value);
        }
        return this.http.get(this.categoryApi, {
            params
        });
    }
    getCategoryById(id: string): Observable<any> {
        return this.http.get(this.categoryApi, {
            params: new HttpParams().set('id', id),
        });
    }
    createCategory(form: any): Observable<any> {
        console.log(this.categoryApi);
        
        return this.http.post(this.categoryApi, form);
    }
    deleteCategory(id: any): Observable<any> {
        return this.http.delete(this.categoryApi + '/' + id);
    }

    updateCategory(category: Category) : Observable<any> {
        return this.http.put(`${this.categoryApi}/${category.id}`, category,
            {
                //headers: this.headerService.createAuthorizationHeader()
            }
        );
    }

}