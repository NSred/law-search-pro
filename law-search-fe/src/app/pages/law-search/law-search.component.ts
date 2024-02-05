import {Component, inject} from '@angular/core';
import {ButtonComponent} from "../../shared/components/button/button.component";
import {FileInputComponent} from "../../shared/components/inputs/file-input/file-input.component";
import {
    MultipleFileInputComponent
} from "../../shared/components/inputs/multiple-file-input/multiple-file-input.component";
import {InputFieldComponent} from "../../shared/components/inputs/input-field/input-field.component";
import {FormControl, Validators} from "@angular/forms";
import {LawsResponse, SearchService} from "../../shared/services/search.service";
import {Observable} from "rxjs";
import {AsyncPipe, NgForOf, NgIf} from "@angular/common";
import {ContractCardComponent} from "../../shared/components/contract-card/contract-card.component";
import {LawCardComponent} from "../../shared/components/law-card/law-card.component";

@Component({
  selector: 'app-law-search',
  standalone: true,
  imports: [
    ButtonComponent,
    FileInputComponent,
    MultipleFileInputComponent,
    InputFieldComponent,
    AsyncPipe,
    ContractCardComponent,
    NgForOf,
    NgIf,
    LawCardComponent
  ],
  templateUrl: './law-search.component.html',
  styleUrl: './law-search.component.scss'
})
export class LawSearchComponent {
  searchService = inject(SearchService)
  formControl: FormControl<string> = new FormControl();
  response$: Observable<LawsResponse> = new Observable<LawsResponse>();

  search() {
    this.response$ = this.searchService.searchLaws(this.formControl.value);
  }
}
