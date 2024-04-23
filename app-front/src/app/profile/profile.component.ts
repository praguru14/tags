import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router, NavigationEnd } from '@angular/router';
import { filter, takeUntil } from 'rxjs/operators';
import { GenericApiService } from '../services/generic-api.service';
import { User } from '../model/user.model';
import { AuthService } from '../services/AuthService.service';
import { HttpHeaders } from '@angular/common/http';
import { Utility } from '../utility/utility';
import { jwtDecode } from "jwt-decode";
import { Observable, Subject } from 'rxjs';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  jwtToken: string | null = localStorage.getItem('accessToken');
  submitted = false;
  isReadOnly = true;

  userForm = this.formBuilder.group({
    firstName: ['', [Validators.required, Validators.minLength(3)]],
    dob: [''],
    lastName: ['', [Validators.required, Validators.minLength(3)]],
    phone: [0, [Validators.required, Validators.minLength(10), Validators.maxLength(10)]],
    email: [{ value: '', disabled: true }, [Validators.required, Validators.email]],
    bloodGroup: ['', [Validators.required, Utility.bloodGroupValidator()]]
  });
  // // To enable the email form control
  // this.userForm.get('email')?.enable(); 
  // { value: '', disabled: true }

  // // To disable the email form control
  // this.userForm.get('email')?.disable();

  userObj: User = new User();
  userObj1: any;
  dobDate: Date | null = null;
  id = localStorage.getItem('id');

  private destroy$: Subject<void> = new Subject<void>();

  constructor(
    private genericApiService: GenericApiService,
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) { }


  ngOnInit() {
    this.fetchUserDetails(this.id);
    this.router.events
      .pipe(
        filter(event => event instanceof NavigationEnd),
        takeUntil(this.destroy$)
      )
      .subscribe(() => {
        this.fetchUserDetails(this.id);
      });
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  fetchUserDetails(id: any) {
    const headers = this.createAuthHeaders();

    if (!headers) {
      return;
    }

    this.genericApiService.getUserDetails(id, headers)
      .subscribe({
        next: (value) => {
          console.log('Response from server:', value);
          this.userObj1 = value;
          this.userObj = this.userObj1.data;
          this.setUserFormData();
        },
        error(err) {
          console.log(err);
        },
      });
  }

  private setUserFormData() {
    const fullName = this.userObj.name;
    const parts = fullName.split(' ', 2);

    this.dobDate = this.userObj.dob ? new Date(this.userObj.dob) : null;
    const timezoneOffset = this.dobDate ? this.dobDate.getTimezoneOffset() * 60000 : 0;
    const adjustedDob = this.dobDate ? new Date(this.dobDate.getTime() - timezoneOffset) : null;


    const formValue: any = {
      firstName: parts[0],
      lastName: parts.length < 2 ? '' : parts[1],
      phone: this.userObj.phone,
      email: this.userObj.email,
      bloodGroup: this.userObj.bloodGroup,
      dob: adjustedDob ? adjustedDob.toISOString().substring(0, 10) : null
    };
    this.userForm.setValue(formValue);

  }
  getAge(): string {
    if (!this.userObj || !this.userObj.dob) {
      return '';
    }

    const today = new Date();
    const birthDate = new Date(this.userObj.dob);

    // Calculate age in years
    let ageYears = today.getFullYear() - birthDate.getFullYear();
    const birthMonth = birthDate.getMonth();
    const todayMonth = today.getMonth();
    const birthDay = birthDate.getDate();
    const todayDay = today.getDate();

    // Adjust age if the current month and day are before the birth month and day
    if (todayMonth < birthMonth || (todayMonth === birthMonth && todayDay < birthDay)) {
      ageYears--;
    }

    // Calculate the number of days since the last birthday
    const lastBirthday = new Date(today.getFullYear(), birthMonth, birthDay);
    const diffMilliseconds = today.getTime() - lastBirthday.getTime();
    const daysSinceLastBirthday = Math.floor(diffMilliseconds / (1000 * 60 * 60 * 24));

    return `${ageYears} years and ${daysSinceLastBirthday} days old`;
  }




  get email() {
    return this.userForm.get('email');
  }

  onLogout() {
    this.authService.logout();
  
  }

  onSubmit() {
    this.submitted = true;
    const dobValue = this.userForm.value.dob;
    const headers = this.createAuthHeaders();

    if (!headers) {
      return;
    }
    this.userObj.name = (this.userForm.value.firstName || '') + " " + (this.userForm.value.lastName || '');


    this.userObj.phone = this.userForm.value.phone!;
    this.userObj.email = this.getEmailFromToken() || '';
    this.userObj.bloodGroup = this.userForm.value.bloodGroup!;
    this.userObj.dob = dobValue ? new Date(dobValue) : undefined;

    console.log(this.userObj);

    this.genericApiService.addUpdateUserDetails(this.userObj, headers).subscribe(
      (response: any) => {
        console.log('Response from server:', response);
      },
      (error: any) => {
        console.error('Error from server:', error);
      }
    );
  }

  createAuthHeaders(): HttpHeaders {
    const accessToken = localStorage.getItem('accessToken');
    console.log(accessToken);


    if (!accessToken) {
      console.error('Access token not found in localStorage');
      return new HttpHeaders();
    }

    return new HttpHeaders({
      'Authorization': `Bearer ${accessToken}`
    });
  }
  private getEmailFromToken(): string | null {
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


