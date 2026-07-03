package com.gurgel.ecommerce.model.dto;

import com.gurgel.ecommerce.model.enums.GurgelCategory;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * Dados de entrada para criação/atualização de um carro.
 */
public class CarRequestDTO {

    @NotBlank(message = "O modelo é obrigatório")
    @Size(min = 2, max = 100, message = "O modelo deve ter entre 2 e 100 caracteres")
    private String model;

    @NotBlank(message = "O ano é obrigatório")
    @Pattern(regexp = "^(19[0-9]{2}|20[0-9]{2})$", message = "O ano deve estar entre 1900 e 2099")
    private String year;

    @Size(max = 1000, message = "A descrição é muito longa")
    private String description;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero")
    @DecimalMax(value = "1000000.0", message = "O preço não pode exceder 1.000.000")
    private BigDecimal price;

    @NotNull(message = "O estoque é obrigatório")
    @Min(value = 0, message = "O estoque não pode ser negativo")
    @Max(value = 1000, message = "O estoque máximo é 1000")
    private Integer stock;

    private String imageUrl;

    @NotNull(message = "A categoria é obrigatória")
    private GurgelCategory category;

    private String engineCapacity;

    private String driveType;

    private String vehicleType;

    private String productionPeriod;

    @Min(value = 1, message = "As unidades produzidas devem ser ao menos 1")
    private Integer unitsProduced;

    private String historicalFact;

    // ==================== GETTERS E SETTERS ====================

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
}
