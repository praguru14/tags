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
  styleUrl: './verify.component.scss'
})
export class VerifyComponent {
  @Input() email: string = '';
  jwtTok = localStorage.getItem('accessToken');

  constructor(private http: HttpClient, private router: Router, private authService: AuthService, private dataService: DataService) { }

  onLogout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  ngOnInit(): void {
    this.dataService.currentEmail.subscribe(email => this.email = email);
  }

  submitOTP(otpValue: string) {
    // Retrieve the JWT token from wherever it's stored
    const jwtToken: string | null = localStorage.getItem('accessToken');
    console.log(jwtToken + " token");
    console.log(otpValue);

    if (jwtToken) {
      try {
        const decodedToken: any = jwtDecode(jwtToken);
        console.log('Decoded Token:', decodedToken);
        const email: string = decodedToken.sub;
        console.log('Decoded email:', email);

        let params = new HttpParams();
        params = params.append('email', email);
        params = params.append('otp', otpValue);

        // Make the HTTP GET request with query parameters
        this.http.get(BaseUrl.register + "verify-account", { params: params }).subscribe(
          (response) => {
            // Handle successful response
            console.log('Verification successful:', response);
          },
          (error) => {
            // Handle error response
            console.error('Verification failed:', error);
          }
        );
      } catch (error) {
        console.error('Error decoding JWT token:', error);
      }
    } else {
      console.error('JWT token not found in localStorage.');
    }
  }


  sendOTP() {
    // Retrieve the JWT token from localStorage
    const jwtToken: string | null = localStorage.getItem('accessToken');

    // Check if JWT token exists
    if (jwtToken !== null) {
      // Decode the JWT token to extract email
      const decodedToken: any = jwtDecode(jwtToken);
      console.log('Decoded Token:', decodedToken);
      const email: string = decodedToken.sub;
      console.log('Decoded email:', email);

      // Construct the URL with email as a query parameter
      const url = `${BaseUrl.register}regenerate-otp?email=${email}`;

      // Make a POST request to the constructed URL
      this.http.post(url, {}).subscribe(
        (response) => {
          console.log('OTP sent successfully:', response);
        },
        (error) => {
          console.error('Error sending OTP:', error);
        }
      );
    } else {
      console.error('JWT token not found in localStorage.');
    }
  }


}
