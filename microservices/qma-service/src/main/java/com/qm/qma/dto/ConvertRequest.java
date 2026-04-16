package com.qm.qma.dto;

import jakarta.validation.constraints.NotNull;

/**
 * DTO for conversion requests
 * 
 * Example: Convert 100 cm to inches
 * {
 *   "value": 100,
 *   "from": "CENTIMETER",
 *   "to": "INCH"
 * }
 */
public class ConvertRequest {
    
    @NotNull(message = "Value is required")
    private Double value;
    
    @NotNull(message = "From unit is required")
    private String from;
    
    @NotNull(message = "To unit is required")
    private String to;

    public ConvertRequest() {}

    public ConvertRequest(Double value, String from, String to) {
        this.value = value;
        this.from = from;
        this.to = to;
    }

    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }
}
