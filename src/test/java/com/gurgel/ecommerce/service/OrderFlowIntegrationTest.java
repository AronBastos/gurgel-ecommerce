package com.gurgel.ecommerce.service;

import com.gurgel.ecommerce.exception.BusinessException;
import com.gurgel.ecommerce.model.dto.AddCartItemRequest;
import com.gurgel.ecommerce.model.dto.CarRequestDTO;
import com.gurgel.ecommerce.model.dto.CarResponseDTO;
import com.gurgel.ecommerce.model.dto.CartResponseDTO;
import com.gurgel.ecommerce.model.dto.CheckoutRequest;
import com.gurgel.ecommerce.model.dto.OrderResponseDTO;
import com.gurgel.ecommerce.model.enums.GurgelCategory;
import com.gurgel.ecommerce.model.enums.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Exercita o fluxo completo contra o banco H2: cadastro de carro, carrinho,
 * checkout com baixa de estoque e cancelamento com devolução de estoque.
 */
@SpringBootTest
@ActiveProfiles("test")
class OrderFlowIntegrationTest {

    @Autowired
    private CarService carService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    private CarResponseDTO createCar(String model, String price, int stock) {
        CarRequestDTO dto = new CarRequestDTO();
        dto.setModel(model);
        dto.setYear("1988");
        dto.setCategory(GurgelCategory.CLASSIC);
        dto.setPrice(new BigDecimal(price));
        dto.setStock(stock);
        return carService.create(dto);
    }

    @Test
    void fullCheckoutFlowReducesStockAndBuildsOrder() {
        CarResponseDTO car = createCar("BR-800 " + System.nanoTime(), "38000.00", 5);

        CartResponseDTO cart = cartService.createCart();
        cartService.addItem(cart.id(), new AddCartItemRequest(car.getId(), 2));

        OrderResponseDTO order = orderService.checkout(
                new CheckoutRequest(cart.id(), "Aron Lopes", "aron@example.com"));

        assertThat(order.status()).isEqualTo(OrderStatus.PENDING);
        assertThat(order.totalAmount()).isEqualByComparingTo("76000.00");
        assertThat(order.items()).hasSize(1);

        // Estoque baixou de 5 para 3.
        assertThat(carService.findById(car.getId()).getStock()).isEqualTo(3);
    }

    @Test
    void checkoutFailsWhenStockInsufficient() {
        CarResponseDTO car = createCar("Cena " + System.nanoTime(), "70000.00", 1);

        CartResponseDTO cart = cartService.createCart();

        assertThatThrownBy(() ->
                cartService.addItem(cart.id(), new AddCartItemRequest(car.getId(), 2)))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void cancellingOrderRestoresStock() {
        CarResponseDTO car = createCar("Xavante " + System.nanoTime(), "55000.00", 4);

        CartResponseDTO cart = cartService.createCart();
        cartService.addItem(cart.id(), new AddCartItemRequest(car.getId(), 3));
        OrderResponseDTO order = orderService.checkout(
                new CheckoutRequest(cart.id(), "Cliente", "cliente@example.com"));

        assertThat(carService.findById(car.getId()).getStock()).isEqualTo(1);

        OrderResponseDTO cancelled = orderService.cancel(order.id());

        assertThat(cancelled.status()).isEqualTo(OrderStatus.CANCELLED);
        assertThat(carService.findById(car.getId()).getStock()).isEqualTo(4);
    }
}
