import { Component, ElementRef, ViewChild, Input, OnDestroy } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { BaseUrl } from '../model/baseUrl.model';
import { Output, EventEmitter } from '@angular/core';
import { Observable, of, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-image-upload',
  templateUrl: './image-upload.component.html',
  styleUrls: ['./image-upload.component.scss']
})
export class ImageUploadComponent implements OnDestroy {
  @Output() imageupload = new EventEmitter<boolean>();
  @Input() idData: any; 
  imageUrl: string = 'https://e7.pngegg.com/pngimages/799/987/png-clipart-computer-icons-avatar-icon-design-avatar-heroes-computer-wallpaper-thumbnail.png';
  jwtToken: string | null = localStorage.getItem('accessToken');
  @ViewChild('fileInput') fileInput!: ElementRef;
  email: string = '';
  decodedToken: any;
  private unsubscribe$ = new Subject<void>();

  constructor(private http: HttpClient) { }

  selectImage() {
    this.fileInput.nativeElement.click();
  }
  ngOnInit(){
    this.getImage()
  }
  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  handleFileInput(event: any) {
    const file = event.target.files[0];
    const reader = new FileReader();
    reader.onload = (e: any) => {
      this.imageUrl = e.target.result;
      this.uploadImage(file);
    };
    reader.readAsDataURL(file);
  }

  uploadImage(imageFile: File) {
    const formData = new FormData();
    formData.append('image', imageFile);
    const params = new HttpParams().set('id', this.idData);
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.jwtToken}`);

    this.http.post(`${BaseUrl.baseUrl}${this.idData}/profile-photo`, formData, { params, headers, responseType: 'blob' })
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(
        (response: any) => {
          console.log('Image upload response:', response);
          this.getImage();
        },
        error => {
          console.error('Error uploading image:', error);
        }
      );
  }

  getImage() {
    if (!this.jwtToken) {
      return;
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.jwtToken}`);
    this.http.get(`${BaseUrl.baseUrl}${this.idData}/profile-photo`, { headers, responseType: 'blob' })
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(
        (response: Blob) => {
          const reader = new FileReader();
          reader.onload = () => {
            const dataURL = reader.result as string;
            console.log('Data URL:', dataURL);
            this.imageUrl = dataURL;
          };
          reader.readAsDataURL(response);
        },
        (error: any) => {
          console.error('Error fetching image:', error);
        }
      );
  }
}
