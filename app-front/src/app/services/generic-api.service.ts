import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BaseUrl } from '../model/baseUrl.model';
import { Observable } from 'rxjs';
import { User } from '../model/user.model';

@Injectable({
  providedIn: 'root'
})
export class GenericApiService {

  constructor(private http: HttpClient) { }

  public getUserDetails(id: any, headers?: HttpHeaders) {
    const requestOptions = {
      headers: headers ? headers : new HttpHeaders()
    };
    return this.http.get(`${BaseUrl.baseUrl}fetch-user-by-id?userId=${id}`, requestOptions);
  }

  public addUpdateUserDetails(user: User, headers?: HttpHeaders): Observable<any> {
    console.log(headers);
    
    return this.http.post(`${BaseUrl.baseUrl}add-user`, user, { headers, responseType: 'text' });
  }
}
