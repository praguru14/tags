import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    constructor() { }

    isLoggedIn(): boolean {
        return !!localStorage.getItem('accessToken');
    }
    logout(): void {
        localStorage.removeItem('accessToken');
    }
}
