import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const TOKEN = 'access_token';
const USER = 'user';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  public getAccessToken(): string {
    return window.localStorage.getItem(TOKEN);
  }
  public setAccessToken(value: string): void {
    window.localStorage.setItem(TOKEN, value);
  }
  public delAccessToken(): void {
    window.localStorage.removeItem(TOKEN);
  }

  public getUser(): User {
    return JSON.parse(window.localStorage.getItem(USER));
  } 
  public setUser(user: User) : void {
    window.localStorage.setItem(USER, JSON.stringify(user));
  }
  public removeUser() : void {
    window.localStorage.removeItem(USER);
  }
  public isLoggedIn() : boolean {
    if(this.getAccessToken() == null && this.getUser() == null) {
      return true;
    }
    return false;
  }

  public isAdminLoggedIn() : boolean {
    return this.isLoggedIn && this.getUser().role === 'ADMIN';
  }
  public isUserLoggedIn() : boolean {
    return this.isLoggedIn && this.getUser().role === 'USER';
  }
  public logout() : void {
    this.delAccessToken();
    this.removeUser();
  }
}

interface User{
  id: string;
  role: string;
}
