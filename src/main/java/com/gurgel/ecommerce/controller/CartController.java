package com.gurgel.ecommerce.controller;

import com.gurgel.ecommerce.model.dto.AddCartItemRequest;
import com.gurgel.ecommerce.model.dto.CartResponseDTO;
import com.gurgel.ecommerce.model.dto.UpdateCartItemRequest;
import com.gurgel.ecommerce.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
@Tag(name = "Carrinho", description = "Carrinho de compras")
public class CartController {

    private final CartService cartService;

    @PostMapping
    @Operation(summary = "Cria um novo carrinho vazio")
    public ResponseEntity<CartResponseDTO> create(UriComponentsBuilder uriBuilder) {
        CartResponseDTO cart = cartService.createCart();
        URI location = uriBuilder.path("/api/carts/{id}").buildAndExpand(cart.id()).toUri();
        return ResponseEntity.created(location).body(cart);
    }

    @GetMapping("/{cartId}")
    @Operation(summary = "Consulta um carrinho e seus itens")
    public CartResponseDTO getCart(@PathVariable Long cartId) {
        return cartService.getCart(cartId);
    }

    @PostMapping("/{cartId}/items")
    @Operation(summary = "Adiciona um carro ao carrinho")
    public CartResponseDTO addItem(@PathVariable Long cartId,
                                   @Valid @RequestBody AddCartItemRequest request) {
        return cartService.addItem(cartId, request);
    }

    @PutMapping("/{cartId}/items/{itemId}")
    @Operation(summary = "Atualiza a quantidade de um item do carrinho")
    public CartResponseDTO updateItem(@PathVariable Long cartId,
                                      @PathVariable Long itemId,
                                      @Valid @RequestBody UpdateCartItemRequest request) {
        return cartService.updateItem(cartId, itemId, request);
    }

    @DeleteMapping("/{cartId}/items/{itemId}")
    @Operation(summary = "Remove um item do carrinho")
    public CartResponseDTO removeItem(@PathVariable Long cartId,
                                      @PathVariable Long itemId) {
        return cartService.removeItem(cartId, itemId);
    }
}
