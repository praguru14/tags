import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BaseUrl } from '../model/baseUrl.model';

@Injectable({
  providedIn: 'root'
})
export class GenericApiService {

  constructor(private http: HttpClient) { }

  public getUserDetails(id: number){

    return this.http.get(`${BaseUrl.baseUrl+'fetch-user-by-id?userId='}${id}`);

  }
}
