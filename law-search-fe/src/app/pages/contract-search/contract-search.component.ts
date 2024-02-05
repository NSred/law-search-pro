import {Component, inject, QueryList, ViewChildren} from '@angular/core';
import {
  AdvancedFilterComponent
} from "../../shared/components/advanced/advanced-filter/advanced-filter.component";
import {AsyncPipe, NgForOf, NgIf} from "@angular/common";
import {ButtonComponent} from "../../shared/components/button/button.component";
import {QueryParserService} from "../../shared/parser/query-parser.service";
import {ContractsResponse, SearchService} from "../../shared/services/search.service";
import {Observable} from "rxjs";
import {ContractCardComponent} from "../../shared/components/contract-card/contract-card.component";
import {DownloadService} from "../../shared/services/download.service";

export interface Filter {
  operator: string,
  field: string,
  value: string,
  isPhrase: boolean,
  isNot: boolean,
  id: number
}

@Component({
  selector: 'app-contract-search',
  standalone: true,
  imports: [
    AdvancedFilterComponent,
    NgForOf,
    NgIf,
    ButtonComponent,
    AsyncPipe,
    ContractCardComponent
  ],
  templateUrl: './contract-search.component.html',
  styleUrl: './contract-search.component.scss'
})
export class ContractSearchComponent {
  parser = inject(QueryParserService)
  searchService = inject(SearchService)
  downloadService = inject(DownloadService)
  searchResult$: Observable<ContractsResponse> = new Observable<ContractsResponse>()
  @ViewChildren(AdvancedFilterComponent) advancedFilters: QueryList<AdvancedFilterComponent> = new QueryList<AdvancedFilterComponent>();
  filters: Filter[] =
    [{ operator: '', field: '', value: '', isPhrase: false, isNot: false, id: 0 }];
  lastFilterId = 1;
  onAndClick() {
    this.addFilter('AND');
  }

  onOrClick() {
    this.addFilter('OR');
  }

  addFilter(operator: string) {
    this.filters.push({ operator: operator, field: '', value: '', isPhrase: false, isNot: false,  id: this.lastFilterId++ });
  }

  search() {
    this.advancedFilters.forEach((filterComponent, index) => {
      const filterValues = filterComponent.getCurrentValues();
      const filterToUpdate = this.filters.find(filter => filter.id === index);

      if (filterToUpdate) {
        // Update the found filter with the new values
        filterToUpdate.field = filterValues.currentField;
        filterToUpdate.value = filterValues.currentValue;
        filterToUpdate.isPhrase = filterValues.isPhrase;
        filterToUpdate.isNot = filterValues.isNotOperator;
        // Assuming 'operator' should remain unchanged or updated elsewhere
      } else {
        console.warn(`No filter found with id ${index}`);
      }
    });
    let query = this.parser.parseFilters(this.filters)
    this.searchResult$ = this.searchService.searchAdvanced(query)
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
