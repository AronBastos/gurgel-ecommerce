package com.gurgel.ecommerce.controller;

import com.gurgel.ecommerce.model.dto.CheckoutRequest;
import com.gurgel.ecommerce.model.dto.OrderResponseDTO;
import com.gurgel.ecommerce.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Checkout e consulta de pedidos")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    @Operation(summary = "Finaliza um carrinho, gerando um pedido e baixando o estoque")
    public ResponseEntity<OrderResponseDTO> checkout(@Valid @RequestBody CheckoutRequest request,
                                                     UriComponentsBuilder uriBuilder) {
        OrderResponseDTO order = orderService.checkout(request);
        URI location = uriBuilder.path("/api/orders/{id}").buildAndExpand(order.id()).toUri();
        return ResponseEntity.created(location).body(order);
    }

    @GetMapping
    @Operation(summary = "Lista pedidos; opcionalmente filtra por e-mail do cliente")
    public List<OrderResponseDTO> findAll(@RequestParam(required = false) String email) {
        return (email == null || email.isBlank())
                ? orderService.findAll()
                : orderService.findByCustomerEmail(email);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulta um pedido pelo id")
    public OrderResponseDTO findById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @PostMapping("/{id}/cancel")
    @Operation(summary = "Cancela um pedido e devolve os itens ao estoque")
    public OrderResponseDTO cancel(@PathVariable Long id) {
        return orderService.cancel(id);
    }
}
