import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from './api';
import { Car } from './models';

@Injectable({ providedIn: 'root' })
export class CarService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${inject(API_BASE_URL)}/cars`;

  findAll(): Observable<Car[]> {
    return this.http.get<Car[]>(this.baseUrl);
  }

  findById(id: number): Observable<Car> {
    return this.http.get<Car>(`${this.baseUrl}/${id}`);
  }

  findByCategory(category: string): Observable<Car[]> {
    return this.http.get<Car[]>(`${this.baseUrl}/category/${category}`);
  }

  searchByModel(model: string): Observable<Car[]> {
    return this.http.get<Car[]>(`${this.baseUrl}/search`, { params: { model } });
  }
}
