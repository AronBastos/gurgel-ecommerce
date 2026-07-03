package com.gurgel.ecommerce.controller;

import com.gurgel.ecommerce.model.dto.CarRequestDTO;
import com.gurgel.ecommerce.model.dto.CarResponseDTO;
import com.gurgel.ecommerce.model.enums.GurgelCategory;
import com.gurgel.ecommerce.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
@Tag(name = "Carros", description = "Catálogo de carros clássicos Gurgel")
public class CarController {

    private final CarService carService;

    @GetMapping
    @Operation(summary = "Lista todos os carros ativos do catálogo")
    public List<CarResponseDTO> findAll() {
        return carService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um carro pelo id")
    public CarResponseDTO findById(@PathVariable Long id) {
        return carService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Cadastra um novo carro")
    public ResponseEntity<CarResponseDTO> create(@Valid @RequestBody CarRequestDTO request,
                                                 UriComponentsBuilder uriBuilder) {
        CarResponseDTO created = carService.create(request);
        URI location = uriBuilder.path("/api/cars/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um carro existente")
    public CarResponseDTO update(@PathVariable Long id,
                                 @Valid @RequestBody CarRequestDTO request) {
        return carService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove (desativa) um carro do catálogo")
    public void delete(@PathVariable Long id) {
        carService.delete(id);
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Lista carros por categoria")
    public List<CarResponseDTO> findByCategory(@PathVariable GurgelCategory category) {
        return carService.findByCategory(category);
    }

    @GetMapping("/search")
    @Operation(summary = "Busca carros por parte do modelo")
    public List<CarResponseDTO> searchByModel(@RequestParam String model) {
        return carService.searchByModel(model);
    }

    @GetMapping("/max-price")
    @Operation(summary = "Lista carros com preço até o valor informado")
    public List<CarResponseDTO> findByMaxPrice(@RequestParam BigDecimal value) {
        return carService.findByMaxPrice(value);
    }

    @GetMapping("/collectors")
    @Operation(summary = "Lista carros de colecionador (produção < 100 unidades)")
    public List<CarResponseDTO> findCollectors() {
        return carService.findCollectorCars();
    }

    @GetMapping("/critical-stock")
    @Operation(summary = "Lista carros com estoque crítico (1 ou 2 unidades)")
    public List<CarResponseDTO> findCriticalStock() {
        return carService.findWithCriticalStock();
    }

    @GetMapping("/stock-value")
    @Operation(summary = "Calcula o valor total do estoque em catálogo")
    public BigDecimal totalStockValue() {
        return carService.calculateTotalStockValue();
    }
}
