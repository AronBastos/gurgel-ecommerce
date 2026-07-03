import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { CartService } from '../../core/cart.service';
import { OrderService } from '../../core/order.service';
import { Order } from '../../core/models';

@Component({
  selector: 'app-cart',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './cart.html',
  styleUrl: './cart.scss'
})
export class CartPage {
  protected readonly cartService = inject(CartService);
  private readonly orderService = inject(OrderService);

  readonly customerName = signal('');
  readonly customerEmail = signal('');
  readonly submitting = signal(false);
  readonly placedOrder = signal<Order | null>(null);

  changeQty(itemId: number, quantity: number): void {
    if (quantity < 1) {
      this.remove(itemId);
      return;
    }
    this.cartService.updateItem(itemId, quantity).subscribe({
      error: (err) => alert(err?.error?.message ?? 'Erro ao atualizar item')
    });
  }

  remove(itemId: number): void {
    this.cartService.removeItem(itemId).subscribe({
      error: (err) => alert(err?.error?.message ?? 'Erro ao remover item')
    });
  }

  checkout(): void {
    const cart = this.cartService.cart();
    if (!cart) {
      return;
    }
    this.submitting.set(true);
    this.orderService
      .checkout({
        cartId: cart.id,
        customerName: this.customerName(),
        customerEmail: this.customerEmail()
      })
      .subscribe({
        next: (order) => {
          this.placedOrder.set(order);
          this.cartService.forget();
          this.submitting.set(false);
        },
        error: (err) => {
          alert(err?.error?.message ?? 'Erro ao finalizar o pedido');
          this.submitting.set(false);
        }
      });
  }

  formValid(): boolean {
    return this.customerName().trim().length >= 2 && /.+@.+\..+/.test(this.customerEmail());
  }

  onImgError(event: Event): void {
    const img = event.target as HTMLImageElement;
    if (!img.src.endsWith('_placeholder.svg')) {
      img.src = '/cars/_placeholder.svg';
    }
  }
}
