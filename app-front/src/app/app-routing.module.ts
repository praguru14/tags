import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './header/home/home.component';
import { AboutComponent } from './header/about/about.component';
import { ContactUsComponent } from './header/contact-us/contact-us.component';
import { ProductInfoComponent } from './header/product-info/product-info.component';
import { SetupComponent } from './header/setup/setup.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'about', component: AboutComponent },
  { path: 'contact-us', component: ContactUsComponent },
  { path: 'product-info', component: ProductInfoComponent },
  { path: 'setup', component: SetupComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
