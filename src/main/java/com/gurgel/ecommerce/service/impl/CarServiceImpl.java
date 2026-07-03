package com.gurgel.ecommerce.service.impl;

import com.gurgel.ecommerce.exception.BusinessException;
import com.gurgel.ecommerce.exception.ResourceNotFoundException;
import com.gurgel.ecommerce.model.dto.CarRequestDTO;
import com.gurgel.ecommerce.model.dto.CarResponseDTO;
import com.gurgel.ecommerce.model.entity.Car;
import com.gurgel.ecommerce.model.enums.GurgelCategory;
import com.gurgel.ecommerce.repository.CarRepository;
import com.gurgel.ecommerce.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CarResponseDTO> findAll() {
        return carRepository.findByActiveTrue().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CarResponseDTO findById(Long id) {
        return toResponse(getEntity(id));
    }

    @Override
    public CarResponseDTO create(CarRequestDTO request) {
        if (carRepository.existsByModel(request.getModel())) {
            throw new BusinessException("Já existe um carro cadastrado com o modelo: " + request.getModel());
        }
        Car car = modelMapper.map(request, Car.class);
        car.setId(null);
        car.setActive(true);
        Car saved = carRepository.save(car);
        log.info("Carro criado com id {}", saved.getId());
        return toResponse(saved);
    }

    @Override
    public CarResponseDTO update(Long id, CarRequestDTO request) {
        Car car = getEntity(id);
        car.setModel(request.getModel());
        car.setYear(request.getYear());
        car.setDescription(request.getDescription());
        car.setPrice(request.getPrice());
        car.setStock(request.getStock());
        car.setImageUrl(request.getImageUrl());
        car.setCategory(request.getCategory());
        car.setEngineCapacity(request.getEngineCapacity());
        car.setDriveType(request.getDriveType());
        car.setVehicleType(request.getVehicleType());
        car.setProductionPeriod(request.getProductionPeriod());
        car.setUnitsProduced(request.getUnitsProduced());
        car.setHistoricalFact(request.getHistoricalFact());
        Car updated = carRepository.save(car);
        log.info("Carro atualizado com id {}", updated.getId());
        return toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        Car car = getEntity(id);
        car.setActive(false);
        carRepository.save(car);
        log.info("Carro desativado com id {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarResponseDTO> findByCategory(GurgelCategory category) {
        return toResponseList(carRepository.findByCategory(category));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarResponseDTO> searchByModel(String model) {
        return toResponseList(carRepository.findByModelContainingIgnoreCase(model));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarResponseDTO> findByMaxPrice(BigDecimal maxPrice) {
        return toResponseList(carRepository.findByPriceLessThanEqual(maxPrice));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarResponseDTO> findCollectorCars() {
        return toResponseList(carRepository.findCollectorCars());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarResponseDTO> findWithCriticalStock() {
        return toResponseList(carRepository.findWithCriticalStock());
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateTotalStockValue() {
        return carRepository.calculateTotalStockValue().orElse(BigDecimal.ZERO);
    }

    // ==================== HELPERS ====================

    private Car getEntity(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Carro", id));
    }

    private List<CarResponseDTO> toResponseList(List<Car> cars) {
        return cars.stream().map(this::toResponse).toList();
    }

    private CarResponseDTO toResponse(Car car) {
        return modelMapper.map(car, CarResponseDTO.class);
    }
}
