import { Component } from '@angular/core';
import { GenericApiService } from '../services/generic-api.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent {

  userObj!: any;

  constructor(private genericApiService: GenericApiService){}

  ngOnInit(){
    this.fetchUserDetails(1);
  }

  fetchUserDetails(id: number) {
    this.genericApiService.getUserDetails(id)
    .subscribe(
      (response) => {
        this.userObj = response;
        console.log(response);
      },
      (myError) => {
        console.log(myError);
      }
    );
  }
}
