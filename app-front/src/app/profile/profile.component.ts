import { Component } from '@angular/core';
import { GenericApiService } from '../services/generic-api.service';
import { User } from '../model/user.model';
import { FormBuilder, Validators } from '@angular/forms';
import { AuthService } from '../services/AuthService.service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent {

  userForm = this.formBuilder.group({
    firstName: [''],
    middleName: [''],
    lastName: [''],
    phone: [0, [Validators.required, Validators.minLength(10), Validators.maxLength(10)]],
    email: ['', [Validators.required, Validators.email]],
    bloodGroup: ['']
  });
  userObj: User = new User;
  userObj1!: any;

  constructor(private genericApiService: GenericApiService, private formBuilder: FormBuilder, private authService: AuthService, private http: HttpClient, private router: Router){}

  ngOnInit(){
    this.fetchUserDetails(1);
  }

  fetchUserDetails(id: number) {
    this.genericApiService.getUserDetails(id)
    .subscribe(
      {
        next: (value) =>{
          this.userObj1 = value;
          this.userObj = this.userObj1.data;
          this.setUserFormData();
          console.log(this.userObj);
        },
        error(err) {
          console.log(err);
        },
      }
    );
  }

  private setUserFormData() {
    this.userForm.setValue({
      firstName: this.userObj.firstName,
      middleName: this.userObj.middleName,
      lastName: this.userObj.lastName,
      phone: this.userObj.phone,
      email: this.userObj.email,
      bloodGroup: this.userObj.bloodGroup
    });
  }

  get email() {
    return this.userForm.get('email');
  }
  onLogout() {
    this.authService.logout();
    this.router.navigate(['/login'])
  }
  onSubmit(){
    this.userObj.firstName = this.userForm.value.firstName!;
    this.userObj.middleName = this.userForm.value.middleName!;
    this.userObj.lastName = this.userForm.value.lastName!;
    this.userObj.phone = this.userForm.value.phone!;
    this.userObj.email = this.userForm.value.email!;
    this.userObj.bloodGroup = this.userForm.value.bloodGroup!;
    console.log(this.userObj);
    this.genericApiService.addUpdateUserDetails(this.userObj).subscribe(
      (response: any) => {
        console.log('Response from server:', response);
      },
      (error: any) => {
        console.error('Error from server:', error);
      }
    );;
  }
}
