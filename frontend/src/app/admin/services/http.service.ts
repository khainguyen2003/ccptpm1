import { HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class HttpServices {
  private backend_url = 'http://localhost:8080';
  private base_api = this.backend_url + '/api';
  private PRODUCT_API = this.base_api + "/products";
  private CATEGORY_API = this.base_api + "/categories";

  private PRODUCT_ADMIN_API = this.base_api + "/admin/products";
  private CATEGORY_ADMIN_API = this.base_api + "/admin/categories";


  constructor(private storageService: StorageService) { }

  /**
   * Dùng khi cần thêm Authorization vào header
   * @returns Bearer token
   */
  createAuthorizationHeader() : HttpHeaders {
    return new HttpHeaders()
            .set('Content-Type',  'application/json')
            .set("Authorization", "Bearer " + this.storageService.getAccessToken())
            .set('x-client-id', this.storageService.getClientId());
  }

  createPaginationParams(
    filter: string = "",
    sort: string = "",
    page: number = 0,
    pageSize: number = 10
  ) : HttpParams {
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

    return params;
  }

  getSort(sortField: string, sortOrder?: number) : string {
    var sort: string = sortField + ":";
    if(sortOrder === -1) {
        sort += 'desc';
    } else {
        sort += 'asc';
    }
    return sort;
  }

  getBaseApi(): string {
    return this.base_api;
  }

  getProductAdminApi(): string {
    return this.PRODUCT_ADMIN_API;
  }

  getCategoryAdminApi(): string {
    return this.CATEGORY_ADMIN_API;
  }

}
