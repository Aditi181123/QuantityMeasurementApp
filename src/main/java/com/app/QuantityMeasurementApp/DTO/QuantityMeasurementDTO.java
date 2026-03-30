package com.app.QuantityMeasurementApp.DTO;

import lombok.*;
import java.util.*;
import java.util.stream.*;

import com.app.QuantityMeasurementApp.model.QuantityMeasurementEntity;
import com.fasterxml.jackson.annotation.JsonProperty;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantityMeasurementDTO {
	// this quantity
	private double thisValue;
	private String thisUnit;
	private String thisMeasurementType;
	
	// that quantity
	private double thatValue;
	private String thatUnit;
	private String thatMeasurementType;
	
	private String operation;
	
	private double resultValue;
	private String resultUnit;
	private String resultMeasurementType;
	
	private String resultString;
	
	private String errormessage;
	
	@JsonProperty("error")
	public boolean error;
	
	
	/** JPA entity → DTO */
    public static QuantityMeasurementDTO fromEntity(QuantityMeasurementEntity e) {
        QuantityMeasurementDTO dto = new QuantityMeasurementDTO();
        dto.thisValue             = e.getThisValue();
        dto.thisUnit              = e.getThisUnit();
        dto.thisMeasurementType   = e.getThisMeasurementType();
        dto.thatValue             = e.getThatValue();
        dto.thatUnit              = e.getThatUnit();
        dto.thatMeasurementType   = e.getThatMeasurementType();
        dto.operation             = e.getOperation();
        dto.resultString          = e.getResultString();
        dto.resultValue           = e.getResultValue();
        dto.resultUnit            = e.getResultUnit();
        dto.resultMeasurementType = e.getResultMeasurementType();
        dto.errormessage          = e.getErrorMessage();
        dto.error                 = e.isError();
        return dto;
    }

    /** DTO → JPA entity */
    public QuantityMeasurementEntity toEntity() {
        QuantityMeasurementEntity e = new QuantityMeasurementEntity();
        e.setThisValue(thisValue);
        e.setThisUnit(thisUnit);
        e.setThisMeasurementType(thisMeasurementType);
        e.setThatValue(thatValue);
        e.setThatUnit(thatUnit);
        e.setThatMeasurementType(thatMeasurementType);
        e.setOperation(operation);
        e.setResultString(resultString);
        e.setResultValue(resultValue);
        e.setResultUnit(resultUnit);
        e.setResultMeasurementType(resultMeasurementType);
        e.setErrorMessage(errormessage);
        e.setError(error);
        return e;
    }

    /** List of entities → list of DTOs */
    public static List<QuantityMeasurementDTO> fromEntityList(List<QuantityMeasurementEntity> entities) {
        return entities.stream()
                .map(QuantityMeasurementDTO::fromEntity)
                .collect(Collectors.toList());
    }

	
}
