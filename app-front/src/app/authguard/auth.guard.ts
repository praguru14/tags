import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/AuthService.service';

@Injectable({
    providedIn: 'root'
})
export class AuthGuard implements CanActivate {

    constructor(private authService: AuthService, private router: Router) { }

    canActivate(): boolean {
        console.log('AuthGuard canActivate called');
        if (this.authService.isLoggedIn()) {
            console.log('User is logged in');
            return true;
        } else {
            console.log('User is not logged in');
            this.router.navigate(['/login']); 
            return false; 
        }
    }
}
