import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DocumentService {

  constructor(private httpClient: HttpClient) { }
  private readonly baseUrl: string = 'http://localhost:8080/api/documents'
  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

  upload(formData: FormData): Observable<unknown>{
    return this.httpClient.post<unknown>(`${this.baseUrl}/upload`, formData)
  }
}
