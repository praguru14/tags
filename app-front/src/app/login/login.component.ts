import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { BaseUrl } from '../model/baseUrl.model';
import { Router } from '@angular/router';
import { AuthService } from '../services/AuthService.service';
import { VerifyComponent } from '../verify/verify.component';
import { DataService } from '../services/DataService.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit {

  constructor(private http: HttpClient, private router: Router, private authService: AuthService, private dataService: DataService) { }
  errorMessage: string = '';
  emailExistsBool:Boolean = false
  url = `${BaseUrl.register}`
  accessToken: string='';
  response =''
  signupObj:any={
    name:'',
    email:'',
    password:'',
    phone:'',
  }
  loginObj: any = {
    email: '',
    password: ''
  }

  email = ''
  emailCheck:any=''
  ngOnInit(): void {
    // throw new Error('Method not implemented.');
  }
  onSignUp() {
    this.http.post(this.url + 'register', this.signupObj).subscribe(
      (response: any) => {
        console.log(response);
        this.dataService.getEmail(this.signupObj.email)
        this.response = response;
        const accessToken = response?.accessToken; // Access accessToken property
        if (accessToken) {
          this.storeToken(accessToken);
          this.router.navigate(['/verify']);
        } else {
          console.error("Access token not found in response:", response);
        }
      },
      (error) => {
        console.error(this.signupObj, error);
      }
    );
  }

  onLogin() {
    
    this.http.post<any>(this.url + 'login', this.loginObj).subscribe(
      (response) => {
        console.log("logged in" + this.url, response);
        this.response = response;
        const accessToken = response.accessToken;
        this.storeToken(accessToken);
        if (response.message === "Verify yourself") {
          this.dataService.getEmail(this.loginObj.email)
          this.router.navigate(['/verify']).then(() => {
          }).catch(err => {
            console.error("Navigation to '/verify' failed:", err);
          });
        } else {
          this.router.navigate(['/profile']).then(() => {
          }).catch(err => {
            console.error("Navigation to '/profile' failed:", err);
          });
        }
      },
      (error) => {
        console.error(this.loginObj, error);
        this.errorMessage = "Wrong details , please try again"        
      }
    );
  }

  getErrorMessage(emailCheck: string) {
    const url = `${BaseUrl.baseUrl}user-exists-email`;
    console.log(url);
    const queryParams = { email: emailCheck };

    this.http.get<any>(url, { params: queryParams }).subscribe(
      (response) => {
        console.log(response);
        if(response.data){
          this.emailExistsBool= true;
          console.log(this.emailExistsBool);
          
        }
      },
      (error) => {
        console.error('Error:', error);
      }
  
    );
    return this.emailExistsBool;
  }

  storeToken(token: string) {
    localStorage.setItem('accessToken', token);
    return token;
  }
  getToken() {
    console.log(localStorage.getItem('accessToken'));
    return localStorage.getItem('accessToken');
  }

  onLogout(){
    this.authService.logout();
  }

}
