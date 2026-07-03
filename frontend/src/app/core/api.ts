import { InjectionToken } from '@angular/core';

/**
 * URL base da API Gurgel E-commerce.
 *
 * Relativa de propósito: em desenvolvimento o `ng serve` encaminha `/api` para
 * o backend via proxy.conf.json; em produção o nginx faz o mesmo proxy. Assim
 * o frontend não depende de host/porta fixos.
 */
export const API_BASE_URL = new InjectionToken<string>('API_BASE_URL', {
  providedIn: 'root',
  factory: () => '/api'
});
