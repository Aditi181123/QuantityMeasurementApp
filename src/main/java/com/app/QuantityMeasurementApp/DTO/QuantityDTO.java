package com.app.QuantityMeasurementApp.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

/*
 * 🔷 DTO (Data Transfer Object)
 *
 * 📌 Used to transfer data between:
 *    ✔ Controller ↔ Service
 *    ✔ Client ↔ Backend
 *
 * 📌 Contains VALIDATION annotations
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantityDTO {

    //  THIS QUANTITY

    @NotNull(message = "Value cannot be null")
    private Double thisValue;

    @NotEmpty(message = "Unit cannot be empty")
    private String thisUnit;

    /*
     *  @Pattern → restricts allowed values
     */
    @NotEmpty(message = "Measurement type cannot be empty")
    @Pattern(
        regexp = "LengthUnit|VolumeUnit|WeightUnit|TemperatureUnit",
        message = "Measurement type must be one of: LengthUnit, VolumeUnit, WeightUnit, TemperatureUnit"
    )
    private String thisMeasurementType;   


    //  THAT QUANTITY

    @NotNull(message = "Value cannot be null")
    private Double thatValue;

    @NotEmpty(message = "Unit cannot be empty")
    private String thatUnit;

    @NotEmpty(message = "Measurement type cannot be empty")
    @Pattern(
        regexp = "LengthUnit|VolumeUnit|WeightUnit|TemperatureUnit",
        message = "Measurement type must be one of: LengthUnit, VolumeUnit, WeightUnit, TemperatureUnit"
    )
    private String thatMeasurementType;   



    //  OPERATION

    @NotEmpty(message = "Operation cannot be empty")
    @Pattern(
        regexp = "ADD|SUBTRACT|COMPARE|CONVERT",
        message = "Operation must be one of: ADD, SUBTRACT, COMPARE, CONVERT"
    )
    private String operation;



    //  CUSTOM VALIDATION

    /*
     *  @AssertTrue
     *    ✔ Custom validation logic
     *    ✔ Must return true for valid case
     */
    @AssertTrue(message = "Measurement types of both quantities must match")
    public boolean isMeasurementTypeValid() {

        // avoid NullPointerException
        if (operation == null) return true;

        switch (operation.toUpperCase()) {

            case "ADD":
            case "SUBTRACT":
            case "COMPARE":
            case "CONVERT":
                return thisMeasurementType != null &&
                       thisMeasurementType.equals(thatMeasurementType);

            default:
                return true;
        }
    }
}