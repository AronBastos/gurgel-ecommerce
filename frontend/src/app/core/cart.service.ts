import { Injectable, computed, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of, tap } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { API_BASE_URL } from './api';
import { AddCartItemRequest, Cart } from './models';

const CART_ID_KEY = 'gurgel_cart_id';

@Injectable({ providedIn: 'root' })
export class CartService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${inject(API_BASE_URL)}/carts`;

  /** Carrinho atual (reativo). */
  readonly cart = signal<Cart | null>(null);

  /** Total de itens, usado no badge do cabeçalho. */
  readonly itemCount = computed(() =>
    (this.cart()?.items ?? []).reduce((sum, i) => sum + i.quantity, 0));

  /** Recupera o carrinho persistido ao iniciar a app, se existir. */
  init(): void {
    const id = this.storedCartId();
    if (id) {
      this.http.get<Cart>(`${this.baseUrl}/${id}`).subscribe({
        next: (cart) => this.cart.set(cart),
        error: () => this.forget()
      });
    }
  }

  addItem(request: AddCartItemRequest): Observable<Cart> {
    return this.ensureCart().pipe(
      switchMap((cartId) =>
        this.http.post<Cart>(`${this.baseUrl}/${cartId}/items`, request)),
      tap((cart) => this.cart.set(cart))
    );
  }

  updateItem(itemId: number, quantity: number): Observable<Cart> {
    return this.http
      .put<Cart>(`${this.baseUrl}/${this.currentId()}/items/${itemId}`, { quantity })
      .pipe(tap((cart) => this.cart.set(cart)));
  }

  removeItem(itemId: number): Observable<Cart> {
    return this.http
      .delete<Cart>(`${this.baseUrl}/${this.currentId()}/items/${itemId}`)
      .pipe(tap((cart) => this.cart.set(cart)));
  }

  /** Limpa o carrinho local após um checkout bem-sucedido. */
  forget(): void {
    localStorage.removeItem(CART_ID_KEY);
    this.cart.set(null);
  }

  private ensureCart(): Observable<number> {
    const id = this.storedCartId();
    if (id) {
      return of(id);
    }
    return this.http.post<Cart>(this.baseUrl, {}).pipe(
      tap((cart) => {
        localStorage.setItem(CART_ID_KEY, String(cart.id));
        this.cart.set(cart);
      }),
      switchMap((cart) => of(cart.id))
    );
  }

  private currentId(): number {
    const id = this.storedCartId();
    if (!id) {
      throw new Error('Nenhum carrinho ativo');
    }
    return id;
  }

  private storedCartId(): number | null {
    const raw = localStorage.getItem(CART_ID_KEY);
    return raw ? Number(raw) : null;
  }
}
