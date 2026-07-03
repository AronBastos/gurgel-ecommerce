export type GurgelCategory =
  | 'CLASSIC' | 'SPORTS' | 'UTILITY' | 'COLLECTOR'
  | 'HISTORICAL' | 'PROTOTYPE' | 'ELECTRIC' | 'PICKUP' | 'OFF_ROAD';

export type OrderStatus = 'PENDING' | 'PAID' | 'SHIPPED' | 'DELIVERED' | 'CANCELLED';

export interface Car {
  id: number;
  model: string;
  year: string;
  description: string;
  price: number;
  stock: number;
  imageUrl: string;
  category: GurgelCategory;
  engineCapacity?: string;
  driveType?: string;
  vehicleType?: string;
  productionPeriod?: string;
  unitsProduced?: number;
  historicalFact?: string;
  active: boolean;
  createdAt: string;
}

export interface CartItem {
  itemId: number;
  carId: number;
  carModel: string;
  imageUrl: string;
  unitPrice: number;
  quantity: number;
  subtotal: number;
}

export interface Cart {
  id: number;
  items: CartItem[];
  total: number;
  checkedOut: boolean;
}

export interface OrderItem {
  carId: number;
  carModel: string;
  unitPrice: number;
  quantity: number;
  subtotal: number;
}

export interface Order {
  id: number;
  customerName: string;
  customerEmail: string;
  items: OrderItem[];
  totalAmount: number;
  status: OrderStatus;
  createdAt: string;
}

export interface AddCartItemRequest {
  carId: number;
  quantity: number;
}

export interface CheckoutRequest {
  cartId: number;
  customerName: string;
  customerEmail: string;
}
