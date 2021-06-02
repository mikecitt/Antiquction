import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

const httpOptions = {
	headers: new HttpHeaders({'Content-Type': 'application/json'})
}

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  constructor(private http: HttpClient) { }

  getItems(text='', currentPage: number, sortBy='name', direction='ASC') {
    return this.http.get<any[]>(`${environment.api_url}/items/?text=${text}&pageNo=${currentPage}&sortBy=${sortBy}&direction=${direction}`)
  }

  getMyBiddingItems() {
    return this.http.get<any[]>(`${environment.api_url}/items/my`)
  }

  getBill(id: number) {
    return this.http.get<any[]>(`${environment.api_url}/items/${id}/bill`)
  }

  getItem(id: number) {
    return this.http.get<any[]>(`${environment.api_url}/items/${id}/`)
  }

  addItem(payload) {
  	return this.http.post<any>(`${environment.api_url}/items`, payload, httpOptions)
  }

  removeItem(id: number) {
    return this.http.delete<any[]>(`${environment.api_url}/items/${id}/`)
  }

  updateItem(id: number, payload) {
    return this.http.put(`${environment.api_url}/items/${id}`, payload);
  }

  bidItem(id: number, bidPrice: number) {
  	return this.http.post<any>(`${environment.api_url}/items/${id}/bid?bidPrice=${bidPrice}`, httpOptions)
  }

  autoBidItem(id: number, autoBidPrice: number) {
  	return this.http.post<any>(`${environment.api_url}/items/${id}/autobid?maxBidPrice=${autoBidPrice}`, httpOptions)
  }

  autoBidItemCancel(id: number) {
  	return this.http.delete<any>(`${environment.api_url}/items/${id}/autobid`, httpOptions)
  }
}
