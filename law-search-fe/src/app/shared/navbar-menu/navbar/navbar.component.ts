import {Component, inject} from '@angular/core';
import {MatToolbar} from "@angular/material/toolbar";
import {NgIf, NgOptimizedImage} from "@angular/common";
import {NavbarButtonComponent} from "../navbar-button/navbar-button.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    MatToolbar,
    NgOptimizedImage,
    NavbarButtonComponent,
    NgIf
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {
  router = inject(Router)

  gotoSearchLaws() {
    this.router.navigate(['laws']).then();
  }

  gotoSearchContracts() {
    this.router.navigate(['']).then();
  }

  gotoSearchLocation() {
    this.router.navigate(['location']).then();
  }

  gotoUpload() {
    this.router.navigate(['upload']).then();
  }
}
