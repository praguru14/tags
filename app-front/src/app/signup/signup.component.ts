import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent {

  constructor(private router: Router) { }

  signup(email: string, password: string, confirmPassword: string): void {
    // Implement your signup logic here
    // Example: Make HTTP request to backend for user registration

    // For demonstration, assume signup is successful and navigate to login page
    this.router.navigate(['/login']);
  }
}
