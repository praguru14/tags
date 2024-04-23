import { Component } from '@angular/core';
import { LoadingService } from '../services/loading.service';
import { delay } from 'rxjs';
import { Utility } from '../utility/utility';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.scss'
})
export class HomepageComponent {
  isLoggedIn = true;
  loading: boolean = false;
  email = Utility.getEmailFromToken();
  constructor(
    private _loading: LoadingService
  ){}

  ngOnInit() {
    this.listenToLoading();
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


}
