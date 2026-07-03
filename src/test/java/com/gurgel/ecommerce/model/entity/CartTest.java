package com.gurgel.ecommerce.model.entity;

import com.gurgel.ecommerce.model.enums.GurgelCategory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Testes das regras de negócio do domínio (sem Spring).
 */
class CartTest {

    private Car car(long id, String model, String price, int stock) {
        Car car = new Car();
        car.setId(id);
        car.setModel(model);
        car.setYear("1988");
        car.setCategory(GurgelCategory.CLASSIC);
        car.setPrice(new BigDecimal(price));
        car.setStock(stock);
        return car;
    }

    @Test
    void addingSameCarTwiceMergesQuantities() {
        Cart cart = new Cart();
        Car brand = car(1L, "BR-800", "38000.00", 10);

        cart.addItem(brand, 1);
        cart.addItem(brand, 2);

        assertThat(cart.getItems()).hasSize(1);
        assertThat(cart.getItems().get(0).getQuantity()).isEqualTo(3);
    }

    @Test
    void totalIsSumOfSubtotals() {
        Cart cart = new Cart();
        cart.addItem(car(1L, "BR-800", "38000.00", 10), 2);   // 76.000
        cart.addItem(car(2L, "Cena", "70000.00", 5), 1);      // 70.000

        assertThat(cart.getTotal()).isEqualByComparingTo("146000.00");
    }

    @Test
    void reduceStockFailsWhenInsufficient() {
        Car brand = car(1L, "Itaipu", "95000.00", 1);

        assertThatThrownBy(() -> brand.reduceStock(2))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void reduceStockDecrementsAvailableUnits() {
        Car brand = car(1L, "Xavante", "55000.00", 3);

        brand.reduceStock(2);

        assertThat(brand.getStock()).isEqualTo(1);
    }
}
