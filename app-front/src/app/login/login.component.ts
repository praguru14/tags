import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  constructor(private router: Router) { }

  login(email: string, password: string): void {
    // Implement your login logic here
    // Example: Make HTTP request to backend for authentication

    // For demonstration, assume login is successful and navigate to home page
    this.router.navigate(['/home']);
  }
}
