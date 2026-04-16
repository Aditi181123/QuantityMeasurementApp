package com.qm.qma.dto;

import jakarta.validation.constraints.NotNull;

/**
 * DTO for operation requests (compare, add, subtract, multiply, divide)
 * 
 * Example: Add 1 foot + 6 inches
 * {
 *   "firstValue": 1,
 *   "firstUnit": "FEET",
 *   "secondValue": 6,
 *   "secondUnit": "INCH"
 * }
 */
public class OperationRequest {
    
    @NotNull(message = "First value is required")
    private Double firstValue;
    
    @NotNull(message = "First unit is required")
    private String firstUnit;
    
    @NotNull(message = "Second value is required")
    private Double secondValue;
    
    @NotNull(message = "Second unit is required")
    private String secondUnit;

    public OperationRequest() {}

    public OperationRequest(Double firstValue, String firstUnit, Double secondValue, String secondUnit) {
        this.firstValue = firstValue;
        this.firstUnit = firstUnit;
        this.secondValue = secondValue;
        this.secondUnit = secondUnit;
    }

    public Double getFirstValue() { return firstValue; }
    public void setFirstValue(Double firstValue) { this.firstValue = firstValue; }

    public String getFirstUnit() { return firstUnit; }
    public void setFirstUnit(String firstUnit) { this.firstUnit = firstUnit; }

    public Double getSecondValue() { return secondValue; }
    public void setSecondValue(Double secondValue) { this.secondValue = secondValue; }

    public String getSecondUnit() { return secondUnit; }
    public void setSecondUnit(String secondUnit) { this.secondUnit = secondUnit; }
}
