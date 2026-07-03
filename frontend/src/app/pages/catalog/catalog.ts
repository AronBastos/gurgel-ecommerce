import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CarService } from '../../core/car.service';
import { CartService } from '../../core/cart.service';
import { Car } from '../../core/models';

const CATEGORY_LABELS: Record<string, string> = {
  CLASSIC: 'Clássico',
  SPORTS: 'Esportivo',
  UTILITY: 'Utilitário',
  COLLECTOR: 'Colecionador',
  HISTORICAL: 'Histórico',
  PROTOTYPE: 'Protótipo',
  ELECTRIC: 'Elétrico',
  PICKUP: 'Picape',
  OFF_ROAD: 'Off-road'
};

@Component({
  selector: 'app-catalog',
  imports: [CommonModule],
  templateUrl: './catalog.html',
  styleUrl: './catalog.scss'
})
export class Catalog implements OnInit {
  private readonly carService = inject(CarService);
  private readonly cartService = inject(CartService);

  readonly cars = signal<Car[]>([]);
  readonly loading = signal(true);
  readonly error = signal<string | null>(null);
  readonly addedId = signal<number | null>(null);

  ngOnInit(): void {
    this.carService.findAll().subscribe({
      next: (cars) => {
        this.cars.set(cars);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('Não foi possível carregar o catálogo. A API está no ar?');
        this.loading.set(false);
      }
    });
  }

  addToCart(car: Car): void {
    this.cartService.addItem({ carId: car.id, quantity: 1 }).subscribe({
      next: () => {
        this.addedId.set(car.id);
        setTimeout(() => this.addedId.set(null), 1500);
      },
      error: (err) => alert(err?.error?.message ?? 'Erro ao adicionar ao carrinho')
    });
  }

  categoryLabel(category: string): string {
    return CATEGORY_LABELS[category] ?? category;
  }

  onImgError(event: Event): void {
    const img = event.target as HTMLImageElement;
    if (!img.src.endsWith('_placeholder.svg')) {
      img.src = '/cars/_placeholder.svg';
    }
  }
}
