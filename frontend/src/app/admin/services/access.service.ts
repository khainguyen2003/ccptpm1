import { StorageService } from './storage.service';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Observable, map } from 'rxjs';

const AUTHAPI = 'http://localhost:8080/api/auth/admin';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({ providedIn: 'root' })
export class AccessServices {
    constructor(
        private http: HttpClient,
        private storageService: StorageService
    ) {}
    login(loginForm: FormGroup): Observable<any> {
        const headers = new HttpHeaders().set(
            'Content-Type',
            'application/json'
        );
        return this.http
            .post(AUTHAPI + '/login', JSON.stringify(loginForm), {
                headers,
                observe: 'response',
            });
    }

    register(signupRequest: any): Observable<any> {
        const headers = new HttpHeaders().set(
            'Content-Type',
            'application/json'
        );
        return this.http.post(AUTHAPI + '/signup', signupRequest, {headers,
            observe: 'response',});
        // return this.http.post(AUTHAPI + '/signup', signupRequest).pipe(
        //     map((data) => {
        //         // const token = res.headers.get('Authorization')?.substring(7);
        //         console.log(data.userDetails);
        //         // const token = res.body;
        //         console.log(JSON.stringify(res));

        //         // const user = res.body;
        //         // if (token) {
        //         //     this.storageService.setAccessToken(token);
        //         //     return true;
        //         // }

        //         return false;
        //     })
        // );
    }

    // private handleError(error: HttpErrorResponse) {
    //     if (error.status === 0) {
    //         // A client-side or network error occurred. Handle it accordingly.
    //         this.snackbarService.error('An error occurred:', error.error);
    //     } else {
    //         // The backend returned an unsuccessful response code.
    //         // The response body may contain clues as to what went wrong.
    //         this.snackbarService.error(
    //             `Backend returned code ${error.status}, body was: `,
    //             error.error
    //         );
    //     }
    //     // Return an observable with a user-facing error message.
    //     return throwError(
    //         () => new Error('Something bad happened; please try again later.')
    //     );
    // }
    // public createTokenPair = async (payload, publicKey, privateKey) => {
    //     try {
    //         const accessToken = await JWT.sign(payload, privateKey, {
    //             algorithm: 'RS256',
    //             expiresIn: '2 days',
    //         });
    //         const refreshToken = await JWT.sign(payload, privateKey, {
    //             algorithm: 'RS256',
    //             expiresIn: '7 days',
    //         });

    //         JWT.verify(accessToken, publicKey, (err, result) => {
    //             if (err) {
    //                 console.log('Error verfy: ', err);
    //             } else {
    //                 console.log('Decode verfy: ', result);
    //             }
    //         });

    //         return { accessToken, refreshToken };
    //     } catch (err) {}
    // };
}
