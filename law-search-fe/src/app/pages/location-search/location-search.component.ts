import {Component, inject} from '@angular/core';
import {AsyncPipe, NgForOf, NgIf} from "@angular/common";
import {ButtonComponent} from "../../shared/components/button/button.component";
import {InputFieldComponent} from "../../shared/components/inputs/input-field/input-field.component";
import {LawCardComponent} from "../../shared/components/law-card/law-card.component";
import {Observable} from "rxjs";
import {ContractsResponse, LawsResponse, LocationDto, SearchService} from "../../shared/services/search.service";
import {FormControl} from "@angular/forms";
import {ContractCardComponent} from "../../shared/components/contract-card/contract-card.component";
import {DownloadService} from "../../shared/services/download.service";

@Component({
  selector: 'app-location-search',
  standalone: true,
  imports: [
    AsyncPipe,
    ButtonComponent,
    InputFieldComponent,
    LawCardComponent,
    NgForOf,
    NgIf,
    ContractCardComponent
  ],
  templateUrl: './location-search.component.html',
  styleUrl: './location-search.component.scss'
})
export class LocationSearchComponent {
  downloadService = inject(DownloadService)
  searchService = inject(SearchService)
  formControlAddress: FormControl<string> = new FormControl();
  response$: Observable<ContractsResponse> = new Observable<ContractsResponse>();
  formControlRadius: FormControl<number> = new FormControl();

  search() {
    let dto: LocationDto = {
      address: this.formControlAddress.value,
      radius: this.formControlRadius.value
    }
    this.response$ = this.searchService.searchLocation(dto)
  }

  downloadFile(fileName: string) {
    this.downloadService.downloadContract(fileName).subscribe({
      next: res => {
        this.downloadService.saveFile(res, fileName)
      },
      error: err => {
        console.log('Error occurred ' + err)
      }
    })
  }
}
