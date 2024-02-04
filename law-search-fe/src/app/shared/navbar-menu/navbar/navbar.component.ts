import { Component } from '@angular/core';
import {MatToolbar} from "@angular/material/toolbar";
import {NgIf, NgOptimizedImage} from "@angular/common";
import {NavbarButtonComponent} from "../navbar-button/navbar-button.component";

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

  gotoSearchLaws() {

  }

  gotoSearchContracts() {

  }
}
