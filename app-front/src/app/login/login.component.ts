import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { BaseUrl } from '../model/baseUrl.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit {

  constructor(private http: HttpClient, private router: Router) { }

  url = `${BaseUrl.register}`
  accessToken: string='';
 
  signupObj:any={
    firstName:'',
    lastName:'',
    email:'',
    password:'',
    phone:'',
    bloodGroup:''
  }
  loginObj: any = {
    email: '',
    password: ''
  }
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }
  onSignUp(){

    this.http.post(this.url+'register', this.signupObj).subscribe(
      (response) => {
        console.log("success", response);
      },
      (error) => {
        console.error(this.signupObj,error);
      }
    );


  
  }
  onLogin(){

    this.http.post<any>(this.url+'login',this.loginObj).subscribe(
      (response) => {
        console.log("logged in" + this.url, response);
        const accessToken = response.accessToken;
        this.storeToken(accessToken);
        this.router.navigate(['/profile']);
      },
      (error) => {
        console.error(this.signupObj, error);
      }
    );

  }

  storeToken(token: string) {
    localStorage.setItem('accessToken', token);
    return token;
  }
  getToken() {
    console.log(localStorage.getItem('accessToken'));
    return localStorage.getItem('accessToken');
  }
}
