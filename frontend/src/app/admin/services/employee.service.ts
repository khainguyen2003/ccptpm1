import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HeaderService } from './header.service';
import { environment } from 'src/environments/environment';
import { Employee } from 'src/app/models/employee';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  apiUrl: string = environment.testApiUrl;

  constructor(
    private http: HttpClient,
    private headerService: HeaderService,
  ) { }

  getEmployees(): Observable<any> {
    return this.http.get(`${this.apiUrl}/employees`, {
      headers: this.headerService.createAuthorizationHeader()
    });
  }

  updateEmployee(data: Employee): Observable<any> {
    return this.http.put<Employee>(`${this.apiUrl}/employees/${data.id}`, data, {
      headers: this.headerService.createAuthorizationHeader()
    })
  }

  createEmployee(data: Employee): Observable<any> {
    return this.http.post<Employee>(`${this.apiUrl}/employees`, data, {
      headers: this.headerService.createAuthorizationHeader()
    })
  }

  deleteEmployee(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/employees/${id}`, {
      headers: this.headerService.createAuthorizationHeader()
    })
  } 

}
