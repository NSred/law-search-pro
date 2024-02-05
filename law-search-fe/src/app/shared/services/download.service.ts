import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DownloadService {
  private readonly baseUrl: string = 'http://localhost:8080/api/documents/download'
  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
  constructor(private http: HttpClient) { }

  public downloadContract(fileName: string): Observable<Blob> {
    return this.http.get(`${this.baseUrl}/${fileName}`, {responseType: 'blob'});
  }

  public saveFile(response: Blob, filename: string): void {
    const blob = new Blob([response], { type: 'application/pdf' });

    const a = document.createElement('a');
    const objectUrl = URL.createObjectURL(blob);

    a.href = objectUrl;
    a.download = filename;
    document.body.appendChild(a);
    a.click();

    URL.revokeObjectURL(objectUrl);
    document.body.removeChild(a);
  }
}
