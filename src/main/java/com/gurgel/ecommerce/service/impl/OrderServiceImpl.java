package com.gurgel.ecommerce.service.impl;

import com.gurgel.ecommerce.exception.BusinessException;
import com.gurgel.ecommerce.exception.ResourceNotFoundException;
import com.gurgel.ecommerce.mapper.CartOrderMapper;
import com.gurgel.ecommerce.model.dto.CheckoutRequest;
import com.gurgel.ecommerce.model.dto.OrderResponseDTO;
import com.gurgel.ecommerce.model.entity.Car;
import com.gurgel.ecommerce.model.entity.Cart;
import com.gurgel.ecommerce.model.entity.Order;
import com.gurgel.ecommerce.model.entity.OrderItem;
import com.gurgel.ecommerce.model.enums.OrderStatus;
import com.gurgel.ecommerce.repository.CarRepository;
import com.gurgel.ecommerce.repository.CartRepository;
import com.gurgel.ecommerce.repository.OrderRepository;
import com.gurgel.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CarRepository carRepository;
    private final CartOrderMapper mapper;

    @Override
    public OrderResponseDTO checkout(CheckoutRequest request) {
        Cart cart = cartRepository.findById(request.cartId())
                .orElseThrow(() -> ResourceNotFoundException.of("Carrinho", request.cartId()));

        if (cart.isCheckedOut()) {
            throw new BusinessException("O carrinho " + cart.getId() + " já foi finalizado");
        }
        if (cart.isEmpty()) {
            throw new BusinessException("Não é possível finalizar um carrinho vazio");
        }

        Order order = new Order(request.customerName(), request.customerEmail());

        cart.getItems().forEach(cartItem -> {
            Car car = carRepository.findById(cartItem.getCar().getId())
                    .orElseThrow(() -> ResourceNotFoundException.of("Carro", cartItem.getCar().getId()));

            // Car.reduceStock valida a disponibilidade e lança em caso de falta.
            car.reduceStock(cartItem.getQuantity());
            carRepository.save(car);

            order.addItem(OrderItem.fromCar(car, cartItem.getQuantity()));
        });

        Order saved = orderRepository.save(order);
        cart.markCheckedOut();
        cartRepository.save(cart);

        log.info("Pedido {} criado a partir do carrinho {} (total {})",
                saved.getId(), cart.getId(), saved.getTotalAmount());
        return mapper.toOrderResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDTO findById(Long orderId) {
        return mapper.toOrderResponse(getEntity(orderId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> findAll() {
        return orderRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(mapper::toOrderResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> findByCustomerEmail(String email) {
        return orderRepository.findByCustomerEmailIgnoreCaseOrderByCreatedAtDesc(email).stream()
                .map(mapper::toOrderResponse)
                .toList();
    }

    @Override
    public OrderResponseDTO cancel(Long orderId) {
        Order order = getEntity(orderId);
        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new BusinessException("O pedido " + orderId + " já está cancelado");
        }

        // Devolve os itens ao estoque.
        order.getItems().forEach(item ->
                carRepository.findById(item.getCarId())
                        .ifPresent(car -> {
                            car.increaseStock(item.getQuantity());
                            carRepository.save(car);
                        }));

        order.cancel();
        Order saved = orderRepository.save(order);
        log.info("Pedido {} cancelado e estoque devolvido", orderId);
        return mapper.toOrderResponse(saved);
    }

    private Order getEntity(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> ResourceNotFoundException.of("Pedido", orderId));
    }
}
