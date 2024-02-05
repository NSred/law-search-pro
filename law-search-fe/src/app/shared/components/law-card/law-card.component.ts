import {Component, Input} from '@angular/core';
import {Law} from "../../services/search.service";
import {ButtonComponent} from "../button/button.component";

@Component({
  selector: 'app-law-card',
  standalone: true,
  imports: [
    ButtonComponent
  ],
  templateUrl: './law-card.component.html',
  styleUrl: './law-card.component.scss'
})
export class LawCardComponent {
  @Input() law: Law = {lawId: '', highlight: ''}
}
