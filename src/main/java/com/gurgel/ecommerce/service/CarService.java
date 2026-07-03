package com.gurgel.ecommerce.service;

import com.gurgel.ecommerce.model.dto.CarRequestDTO;
import com.gurgel.ecommerce.model.dto.CarResponseDTO;
import com.gurgel.ecommerce.model.enums.GurgelCategory;

import java.math.BigDecimal;
import java.util.List;

/**
 * Casos de uso do catálogo de carros.
 */
public interface CarService {

    List<CarResponseDTO> findAll();

    CarResponseDTO findById(Long id);

    CarResponseDTO create(CarRequestDTO request);

    CarResponseDTO update(Long id, CarRequestDTO request);

    void delete(Long id);

    List<CarResponseDTO> findByCategory(GurgelCategory category);

    List<CarResponseDTO> searchByModel(String model);

    List<CarResponseDTO> findByMaxPrice(BigDecimal maxPrice);

    List<CarResponseDTO> findCollectorCars();

    List<CarResponseDTO> findWithCriticalStock();

    BigDecimal calculateTotalStockValue();
}
