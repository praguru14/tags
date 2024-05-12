import { Component } from '@angular/core';
import { LoadingService } from '../services/loading.service';
import { delay } from 'rxjs';
import { Utility } from '../utility/utility';
import { jwtDecode } from "jwt-decode";
import { AuthService } from '../services/AuthService.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.scss'
})
export class HomepageComponent {
  loading: boolean = false;
  email = Utility.getEmailFromToken();
  constructor(
    private _loading: LoadingService,
    public authService:AuthService,
    private router: Router
  ){}

  ngOnInit() {
    this.listenToLoading();
    console.log(this.authService.isLoggedIn());
    console.log(localStorage.getItem('accessToken'));
    
    
  }

  
  
  /**
   * Listen to the loadingSub property in the LoadingService class. This drives the
   * display of the loading spinner.
   */
  listenToLoading(): void {
    this._loading.loadingSub
      .pipe(delay(0)) // This prevents a ExpressionChangedAfterItHasBeenCheckedError for subsequent requests
      .subscribe((loading) => {
        this.loading = loading;
      });
  }
  onLogout() {
      setTimeout(()=>{
        this.router.navigate(['/'])
      this.authService.logout();
    },2000)
    
  
  }

}
