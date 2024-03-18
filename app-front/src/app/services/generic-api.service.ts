import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BaseUrl } from '../model/baseUrl.model';
import { Observable } from 'rxjs';
import { User } from '../model/user.model';

@Injectable({
  providedIn: 'root'
})
export class GenericApiService {

  constructor(private http: HttpClient) { }

  public getUserDetails(id: number){

    return this.http.get(`${BaseUrl.baseUrl+'fetch-user-by-id?userId='}${id}`);

  }

  public addUpdateUserDetails(user: User){
    return this.http.post(`${BaseUrl.baseUrl+'add-user'}`, user, { responseType: 'text' });
  }
}
