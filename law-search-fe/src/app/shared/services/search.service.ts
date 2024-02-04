import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

export interface Contract {
  contractId : string
  governmentName : string
  governmentType : string
  signatoryPersonName : string
  signatoryPersonSurname : string
  highlight : string
  fileName : string
}

export interface ContractsResponse {
  numberOfResults: number
  results: Contract[]
}

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor(private httpClient: HttpClient) { }
  private readonly baseUrl: string = 'http://localhost:8080/api/search'
  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

  searchAdvanced(query: string): Observable<ContractsResponse>{
    return this.httpClient.post<ContractsResponse>(`${this.baseUrl}/advanced`, query, {headers: this.headers})
  }
}
