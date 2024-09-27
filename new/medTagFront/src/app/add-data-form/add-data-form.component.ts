import { Component } from '@angular/core';

@Component({
  selector: 'app-add-data-form',
  templateUrl: './add-data-form.component.html',
  styleUrl: './add-data-form.component.css'
})
export class AddDataFormComponent {
userDetails = {
    name: '',
    email: '',
    address: '',
    mobile: '',
    age: null,
    gender: ''
  };

  submitForm(form: any): void {
    if (form.valid) {
      console.log('Form data:', this.userDetails);
    }
  }

  selectedFile: File | null = null;

  onImageSelected(event: any): void {
    if (event.target.files && event.target.files[0]) {
      this.selectedFile = event.target.files[0];
      console.log('Image selected:', this.selectedFile);
      // You can handle the image file here, e.g., send it to a backend
    }
  }

 
  
}
