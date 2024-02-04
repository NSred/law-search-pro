import { Injectable } from '@angular/core';
import {Filter} from "../../pages/contract-search/contract-search.component";

@Injectable({
  providedIn: 'root'
})
export class QueryParserService {

  constructor() { }

  parseFilters(filters: Filter[]): string {
    // Initialize the result string
    let result = '';

    // Iterate through the filters
    filters.forEach((filter, index) => {
      // Check if the filter has an operator and is not the first filter
      if (filter.id !== 0 && filter.operator) {
        result += ` ${filter.operator}`;
      }

      // Check if the filter is NOT
      if (filter.isNot) {
        result += ' NOT';
      }

      // Add the field and value
      result += ` ${filter.field}:${filter.isPhrase ? '"' + filter.value + '"' : filter.value}`;

      // Add a space unless it's the last filter
      if (index < filters.length - 1) {
        result += ' ';
      }
    });

    return result;
  }
}
