package com.qm.qma.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity for storing measurement operation history
 */
@Entity
@Table(name = "measurement_history")
public class MeasurementHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** User ID from JWT token (can be null for anonymous users) */
    private String userId;

    /** Operation type: COMPARE, ADD, SUBTRACT, MULTIPLY, DIVIDE, CONVERT */
    private String operation;

    private Double firstValue;
    private String firstUnit;

    private Double secondValue;
    private String secondUnit;

    private Double result;
    private String resultUnit;

    /** Measurement type: LENGTH, WEIGHT, VOLUME, TEMPERATURE */
    private String measurementType;

    @Column(name = "created_at")
    private LocalDateTime timestamp;

    public MeasurementHistory() {
        this.timestamp = LocalDateTime.now();
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getOperation() { return operation; }
    public void setOperation(String operation) { this.operation = operation; }

    public Double getFirstValue() { return firstValue; }
    public void setFirstValue(Double firstValue) { this.firstValue = firstValue; }

    public String getFirstUnit() { return firstUnit; }
    public void setFirstUnit(String firstUnit) { this.firstUnit = firstUnit; }

    public Double getSecondValue() { return secondValue; }
    public void setSecondValue(Double secondValue) { this.secondValue = secondValue; }

    public String getSecondUnit() { return secondUnit; }
    public void setSecondUnit(String secondUnit) { this.secondUnit = secondUnit; }

    public Double getResult() { return result; }
    public void setResult(Double result) { this.result = result; }

    public String getResultUnit() { return resultUnit; }
    public void setResultUnit(String resultUnit) { this.resultUnit = resultUnit; }

    public String getMeasurementType() { return measurementType; }
    public void setMeasurementType(String measurementType) { this.measurementType = measurementType; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
