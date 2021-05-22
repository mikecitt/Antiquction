import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  constructor(private http: HttpClient) { }

  getItems() {
    return this.http.get<any[]>(`${environment.api_url}/items`)
  }

  removeItem(id) {
    return this.http.delete<any[]>(`${environment.api_url}/items/${id}`)
  }
}
