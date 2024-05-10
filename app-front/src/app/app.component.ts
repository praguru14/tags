import { Component } from '@angular/core';
import { Title } from '@angular/platform-browser';
import {Cloudinary} from '@cloudinary/url-gen'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  constructor(private titleService: Title) {
    this.titleService.setTitle("Tags");
  }
  // ngOnInit() {
  //   const cld = new Cloudinary({cloud: {cloudName: 'df0vf2kyi'}});
  // }
  title = 'app-front';
}
