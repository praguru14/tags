import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Utility } from '../utility/utility';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    constructor(private http: HttpClient,private router: Router) {  }
    isLoggedIn(): boolean {
        return !!localStorage.getItem('accessToken');
    }
   logout(): void {
    const jwtToken = localStorage.getItem('accessToken');
    console.log("JWT Token:", jwtToken);
    localStorage.removeItem('accessToken');
    this.router.navigate(['/']);
}
}
