import { HttpServices } from './http.service';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Employee, EmployeeResponse } from 'src/app/models/employee';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  apiUrl: string = environment.testApiUrl + "/admin/employees";

  constructor(
    private http: HttpClient,
    private HttpServices: HttpServices,
  ) { }

  getEmployees({page, size, sort}: EmployeeParams): Observable<EmployeeResponse> {
    const params = new HttpParams();
    if(page) params.append('page', page);
    if(size) params.append('size', size);
    if(sort) params.append('sort', sort);
    return this.http.get<EmployeeResponse>(`${this.apiUrl}`, {
      //headers: this.headerService.createAuthorizationHeader()
    });
  }

  updateEmployee(data: Employee): Observable<any> {
    return this.http.put<Employee>(`${this.apiUrl}/${data.id}`, data, {
      //headers: this.headerService.createAuthorizationHeader()
    });
  }

  createEmployee(data: Employee): Observable<any> {
    return this.http.post<Employee>(`${this.apiUrl}`, data, {
      //headers: this.headerService.createAuthorizationHeader()
    });
  }

  deleteEmployee(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, {
      //headers: this.headerService.createAuthorizationHeader()
    });
  }

  updateStatus(id: number, status: boolean): Observable<any> {
    return this.http.post(`${this.apiUrl}/status/${id}`, status, {
      //headers: this.headerService.createAuthorizationHeader()
    });
  }

}

type EmployeeParams = {
  search?: string;
  position?: number;
  departmant?: number;
  page?: number;
  size?: number;
  sort?: string;
}