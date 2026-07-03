package com.gurgel.ecommerce.model.dto;

import com.gurgel.ecommerce.model.enums.GurgelCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representação de saída de um carro, exposta pela API.
 */
public class CarResponseDTO {

    private Long id;
    private String model;
    private String year;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String imageUrl;
    private GurgelCategory category;
    private String engineCapacity;
    private String driveType;
    private String vehicleType;
    private String productionPeriod;
    private Integer unitsProduced;
    private String historicalFact;
    private Boolean active;
    private LocalDateTime createdAt;

    public CarResponseDTO() {
    }

    // ==================== GETTERS E SETTERS ====================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public GurgelCategory getCategory() {
        return category;
    }

    public void setCategory(GurgelCategory category) {
        this.category = category;
    }

    public String getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(String engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public String getDriveType() {
        return driveType;
    }

    public void setDriveType(String driveType) {
        this.driveType = driveType;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getProductionPeriod() {
        return productionPeriod;
    }

    public void setProductionPeriod(String productionPeriod) {
        this.productionPeriod = productionPeriod;
    }

    public Integer getUnitsProduced() {
        return unitsProduced;
    }

    public void setUnitsProduced(Integer unitsProduced) {
        this.unitsProduced = unitsProduced;
    }

    public String getHistoricalFact() {
        return historicalFact;
    }

    public void setHistoricalFact(String historicalFact) {
        this.historicalFact = historicalFact;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
