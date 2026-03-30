package com.app.QuantityMeasurementApp.DTO.request;

import com.app.QuantityMeasurementApp.DTO.QuantityDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class ArithmeticRequestDTO {

    @Valid @NotNull(message = "thisQuantity must not be null")
    private QuantityDTO thisQuantity;

    @Valid @NotNull(message = "thatQuantity must not be null")
    private QuantityDTO thatQuantity;

    private String targetUnit;

    public ArithmeticRequestDTO() {}

    public ArithmeticRequestDTO(QuantityDTO thisQuantity, QuantityDTO thatQuantity, String targetUnit) {
        this.thisQuantity = thisQuantity;
        this.thatQuantity = thatQuantity;
        this.targetUnit   = targetUnit;
    }

    public QuantityDTO getThisQuantity() { return thisQuantity; }
    public void setThisQuantity(QuantityDTO v) { this.thisQuantity = v; }
    public QuantityDTO getThatQuantity() { return thatQuantity; }
    public void setThatQuantity(QuantityDTO v) { this.thatQuantity = v; }
    public String getTargetUnit() { return targetUnit; }
    public void setTargetUnit(String v) { this.targetUnit = v; }
}