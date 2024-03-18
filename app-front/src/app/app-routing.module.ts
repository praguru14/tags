import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomepageComponent } from './homepage/homepage.component';
import { ProfileComponent } from './profile/profile.component';
import { LoginComponent } from './login/login.component';
import { BaseUrl } from './model/baseUrl.model';
import { HttpClient } from '@angular/common/http';
import { AuthGuard } from './authguard/auth.guard';

const routes: Routes = [
  {path: '', component: HomepageComponent},
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },
    { path: 'login', component: LoginComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { 
  constructor(private http: HttpClient) { }

  // public getUserDetails(id: number) {

  //   return this.http.get(`${BaseUrl.baseUrl + 'fetch-user-by-id?userId='}${id}`);

  //}
}
