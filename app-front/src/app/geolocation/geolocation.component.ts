import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-geolocation',
  templateUrl: './geolocation.component.html',
  styleUrls: ['./geolocation.component.scss']
})
export class GeolocationComponent implements OnInit {
  title = 'Emergency App';
  latitude: number | undefined;
  longitude: number | undefined;
  error: any | undefined;

  constructor(private http: HttpClient) { }

  ngOnInit() {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        position => {
          this.latitude = position.coords.latitude;
          this.longitude = position.coords.longitude;
          this.sendLoc(this.latitude, this.longitude);
        },
        error => {
          console.error('Error getting user location:', error);
          this.sendLocError(null, null);
        }
      );
    } else {
      console.error('Geolocation is not supported by this browser.');
    }
  }

  sendLoc(latitude: number | null, longitude: number | null) {
    const payload = { latitude, longitude };
    console.log('Payload:', payload);
    this.http.post("http://localhost:8400/tag/loc", payload, { responseType: 'text' }).subscribe(
      (response: any) => {
        console.log('Response from server:', response);
      },
      (error: any) => {
        console.error('Error from server:', error);
      }
    );
  }

  sendLocError(latitude: number | null, longitude: number | null) {
 
    const payload = { latitude, longitude };
    console.log('Payload (Error):', payload);
    this.http.post("http://localhost:8400/tag/loc", payload, { responseType: 'text' }).subscribe(
      (response: any) => {
        console.log('Response from server:', response);
      },
      (error: any) => {
        console.error('Error from server:', error);
      }
    );
  }
}
