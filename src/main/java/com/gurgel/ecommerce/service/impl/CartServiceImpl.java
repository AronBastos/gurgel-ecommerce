package com.gurgel.ecommerce.service.impl;

import com.gurgel.ecommerce.exception.BusinessException;
import com.gurgel.ecommerce.exception.ResourceNotFoundException;
import com.gurgel.ecommerce.mapper.CartOrderMapper;
import com.gurgel.ecommerce.model.dto.AddCartItemRequest;
import com.gurgel.ecommerce.model.dto.CartResponseDTO;
import com.gurgel.ecommerce.model.dto.UpdateCartItemRequest;
import com.gurgel.ecommerce.model.entity.Car;
import com.gurgel.ecommerce.model.entity.Cart;
import com.gurgel.ecommerce.model.entity.CartItem;
import com.gurgel.ecommerce.repository.CarRepository;
import com.gurgel.ecommerce.repository.CartRepository;
import com.gurgel.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CarRepository carRepository;
    private final CartOrderMapper mapper;

    @Override
    public CartResponseDTO createCart() {
        Cart cart = cartRepository.save(new Cart());
        log.info("Carrinho criado com id {}", cart.getId());
        return mapper.toCartResponse(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponseDTO getCart(Long cartId) {
        return mapper.toCartResponse(getEntity(cartId));
    }

    @Override
    public CartResponseDTO addItem(Long cartId, AddCartItemRequest request) {
        Cart cart = getOpenCart(cartId);
        Car car = getActiveCar(request.carId());

        int desiredQuantity = cart.findItemByCarId(car.getId())
                .map(item -> item.getQuantity() + request.quantity())
                .orElse(request.quantity());
        ensureStock(car, desiredQuantity);

        cart.addItem(car, request.quantity());
        Cart saved = cartRepository.save(cart);
        log.info("Adicionado carro {} (qtd {}) ao carrinho {}", car.getId(), request.quantity(), cartId);
        return mapper.toCartResponse(saved);
    }

    @Override
    public CartResponseDTO updateItem(Long cartId, Long itemId, UpdateCartItemRequest request) {
        Cart cart = getOpenCart(cartId);
        CartItem item = findItem(cart, itemId);
        ensureStock(item.getCar(), request.quantity());

        item.setQuantity(request.quantity());
        Cart saved = cartRepository.save(cart);
        return mapper.toCartResponse(saved);
    }

    @Override
    public CartResponseDTO removeItem(Long cartId, Long itemId) {
        Cart cart = getOpenCart(cartId);
        CartItem item = findItem(cart, itemId);
        cart.removeItem(item);
        Cart saved = cartRepository.save(cart);
        return mapper.toCartResponse(saved);
    }

    // ==================== HELPERS ====================

    private Cart getEntity(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> ResourceNotFoundException.of("Carrinho", cartId));
    }

    private Cart getOpenCart(Long cartId) {
        Cart cart = getEntity(cartId);
        if (cart.isCheckedOut()) {
            throw new BusinessException("O carrinho " + cartId + " já foi finalizado");
        }
        return cart;
    }

    private Car getActiveCar(Long carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> ResourceNotFoundException.of("Carro", carId));
        if (Boolean.FALSE.equals(car.getActive())) {
            throw new BusinessException("O carro " + carId + " não está disponível");
        }
        return car;
    }

    private CartItem findItem(Cart cart, Long itemId) {
        return cart.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> ResourceNotFoundException.of("Item do carrinho", itemId));
    }

    private void ensureStock(Car car, int quantity) {
        if (!car.hasStockFor(quantity)) {
            throw new BusinessException(String.format(
                    "Estoque insuficiente para %s. Disponível: %d, solicitado: %d",
                    car.getModel(), car.getStock(), quantity));
        }
    }
}
