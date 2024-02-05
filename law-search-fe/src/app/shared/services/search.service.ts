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

export interface Law {
  highlight : string
  lawId: string
}

export interface LawsResponse {
  numberOfResults: number
  results: Law[]
}

export interface LocationDto {
  address: string
  radius: number
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

  searchLaws(query: string): Observable<LawsResponse>{
    return this.httpClient.post<LawsResponse>(`${this.baseUrl}/laws`, query, {headers: this.headers})
  }

  searchLocation(dto: LocationDto): Observable<ContractsResponse>{
    return this.httpClient.post<ContractsResponse>(`${this.baseUrl}/location`, dto, {headers: this.headers})
  }
}
