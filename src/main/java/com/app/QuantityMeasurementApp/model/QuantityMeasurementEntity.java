package com.app.QuantityMeasurementApp.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity // mark this jpa entity
@Table(name = "quantity_measurement" , indexes = {
		@Index(name = "idx_operation" , columnList = "operation"),
		@Index(name = "idx_measurement_type" , columnList = "this_measurement_type"),
		@Index(name = "idx_created_at" , columnList = "created_at")}) 

// lombok (auto genarated boiler code)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantityMeasurementEntity {

    @Id  //primary key 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // THIS QUANTITY
    
    @Column(name = "this_value" , nullable = false)
    public double thisValue;
    
    @Column(name = "this_Unit" , nullable = false)
    public String thisUnit;
    
    @Column(name = "this_measurement_type" , nullable = false)
    public String thisMeasurementType;
    
    // THAT QUANTITY
    
    @Column(name = "that_Value" , nullable = false)
    public double thatValue;
    
    @Column(name = "that_Unit" , nullable = false)
    public String thatUnit;
    
    @Column(name = "that_measurement_type" , nullable = false)
    public String thatMeasurementType;
    
    // for Operations (COMPARE , CONVERT , ADD , SUBTRACT )
    
    @Column(name = "operation" , nullable = false)
    public String operation;
    @Column(name = "result_value")
    public double resultValue;
    @Column(name = "result_unit")
    public String resultUnit;
    @Column(name = "result_measurement_type")
    public String resultMeasurementType;
    
    // for comparison results like "Equal" or "Not Equal"
    @Column(name = "result_string")
    public String resultString;
    
    // Error occurred or not
    @Column(name = "is_error")
    public boolean isError;
    
    // Error Message
    @Column(name = "error_message")
    public String errorMessage;
    
    // TIMESTAMP

    @Column(name = "created_at" , nullable = false , updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updates_at", nullable = false)
    private LocalDateTime updatedAt;

    // Automatically set before insert
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Automatically update before update
    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    

    public QuantityMeasurementEntity(double thisValue, String thisUnit, String thisMeasurementType,
                                     double thatValue, String thatUnit, String thatMeasurementType,
                                     String operation, String resultString) {
        this.thisValue = thisValue; this.thisUnit = thisUnit;
        this.thisMeasurementType = thisMeasurementType;
        this.thatValue = thatValue; this.thatUnit = thatUnit;
        this.thatMeasurementType = thatMeasurementType;
        this.operation = operation; this.resultString = resultString;
    }

    public QuantityMeasurementEntity(double thisValue, String thisUnit, String thisMeasurementType,
                                     double thatValue, String thatUnit, String thatMeasurementType,
                                     String operation, double resultValue, String resultUnit,
                                     String resultMeasurementType) {
        this.thisValue = thisValue; this.thisUnit = thisUnit;
        this.thisMeasurementType = thisMeasurementType;
        this.thatValue = thatValue; this.thatUnit = thatUnit;
        this.thatMeasurementType = thatMeasurementType;
        this.operation = operation; this.resultValue = resultValue;
        this.resultUnit = resultUnit; this.resultMeasurementType = resultMeasurementType;
    }

    public QuantityMeasurementEntity(double thisValue, String thisUnit, String thisMeasurementType,
                                     double thatValue, String thatUnit, String thatMeasurementType,
                                     String operation, String errorMessage, boolean isError) {
        this.thisValue = thisValue; this.thisUnit = thisUnit;
        this.thisMeasurementType = thisMeasurementType;
        this.thatValue = thatValue; this.thatUnit = thatUnit;
        this.thatMeasurementType = thatMeasurementType;
        this.operation = operation; this.errorMessage = errorMessage; this.isError = isError;
    }

}