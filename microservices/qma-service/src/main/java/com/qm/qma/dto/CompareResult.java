package com.qm.qma.dto;

/**
 * DTO for comparison results
 */
public class CompareResult {
    private String comparison;  // ">", "<", or "="
    private Double firstValue;
    private String firstUnit;
    private Double secondValue;
    private String secondUnit;

    public CompareResult() {}

    public CompareResult(String comparison, Double firstValue, String firstUnit, 
                       Double secondValue, String secondUnit) {
        this.comparison = comparison;
        this.firstValue = firstValue;
        this.firstUnit = firstUnit;
        this.secondValue = secondValue;
        this.secondUnit = secondUnit;
    }

    public String getComparison() { return comparison; }
    public void setComparison(String comparison) { this.comparison = comparison; }

    public Double getFirstValue() { return firstValue; }
    public void setFirstValue(Double firstValue) { this.firstValue = firstValue; }

    public String getFirstUnit() { return firstUnit; }
    public void setFirstUnit(String firstUnit) { this.firstUnit = firstUnit; }

    public Double getSecondValue() { return secondValue; }
    public void setSecondValue(Double secondValue) { this.secondValue = secondValue; }

    public String getSecondUnit() { return secondUnit; }
    public void setSecondUnit(String secondUnit) { this.secondUnit = secondUnit; }
}
