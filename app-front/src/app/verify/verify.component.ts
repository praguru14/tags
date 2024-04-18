import { HttpClient } from '@angular/common/http';
import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/AuthService.service';
import { DataService } from '../services/DataService.service';

@Component({
  selector: 'app-verify',
  templateUrl: './verify.component.html',
  styleUrl: './verify.component.scss'
})
export class VerifyComponent {
  @Input() email: string = '';

  constructor(private http: HttpClient, private router: Router, private authService: AuthService,private dataService:DataService) { }
  onLogout() {
    this.authService.logout();
    this.router.navigate(['/login'])
  }
  ngOnInit(): void {
    this.dataService.currentEmail.subscribe(email => this.email = email);
    console.log(this.email);
    
  }
}
