import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class HeaderService {

  constructor(private storageService: StorageService) { }

  /**
   * Dùng khi cần thêm Authorization vào header
   * @returns Bearer token
   */
  createAuthorizationHeader() : HttpHeaders {
    return new HttpHeaders()
            .set('Content-Type',  'application/json')
            .set('Authorization', this.storageService.getAccessToken());
  }

}
