import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/AuthService.service';
import { DataService } from '../services/DataService.service';
import { BaseUrl } from '../model/baseUrl.model';
import {jwtDecode} from "jwt-decode";

@Component({
  selector: 'app-verify',
  templateUrl: './verify.component.html',
  styleUrls: ['./verify.component.scss']
})
export class VerifyComponent {
  @Input() email: string = '';
  jwtToken: string | null = localStorage.getItem('accessToken');

  constructor(private http: HttpClient, private router: Router, private authService: AuthService, private dataService: DataService) { }

  onLogout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  ngOnInit(): void {
    this.dataService.currentEmail.subscribe(email => this.email = email);
  }

  submitOTP(otpValue: string) {
    const email = this.getEmailFromToken();
    if (email) {
      // Construct the URL with email and OTP as query parameters
      const url = `${BaseUrl.register}verify-account?email=${email}&otp=${otpValue}`;

      // Make a GET request to the constructed URL
      this.http.post(url,{},{responseType:'text'}).subscribe(
        (response) => {
          console.log('Verification successful:', response);
          this.router.navigate(['/profile']); // Navigate to profile on successful verification
        },
        (error) => {
          console.error('Verification failed:', error);
        }
      );
    }
  }

  sendOTP() {
    const email = this.getEmailFromToken();
    if (email) {
      const url = `${BaseUrl.register}regenerate-otp?email=${email}`;
      this.http.post(url, {},{responseType:'text'}).subscribe(
        (response) => {
          console.log('OTP sent successfully:', response);
          
        },
        (error) => {
          console.error('Error sending OTP:', error);
        }
      );
    }
  }

   getEmailFromToken(): string | null {
    if (this.jwtToken) {
      try {
        const decodedToken: any = jwtDecode(this.jwtToken);
        return decodedToken.sub;
      } catch (error) {
        console.error('Error decoding JWT token:', error);
        return null;
      }
    } else {
      console.error('JWT token not found in localStorage.');
      return null;
    }
  }
}
