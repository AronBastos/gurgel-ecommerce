import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from './api';
import { CheckoutRequest, Order } from './models';

@Injectable({ providedIn: 'root' })
export class OrderService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${inject(API_BASE_URL)}/orders`;

  checkout(request: CheckoutRequest): Observable<Order> {
    return this.http.post<Order>(`${this.baseUrl}/checkout`, request);
  }

  findAll(): Observable<Order[]> {
    return this.http.get<Order[]>(this.baseUrl);
  }

  findById(id: number): Observable<Order> {
    return this.http.get<Order>(`${this.baseUrl}/${id}`);
  }

  cancel(id: number): Observable<Order> {
    return this.http.post<Order>(`${this.baseUrl}/${id}/cancel`, {});
  }
}
