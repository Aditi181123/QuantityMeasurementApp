package com.app.QuantityMeasurementApp.service;

import com.app.QuantityMeasurementApp.DTO.QuantityMeasurementDTO;
import com.app.QuantityMeasurementApp.DTO.QuantityDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IQuantityMeasurementService {

    // PUBLIC OPERATIONS
    QuantityMeasurementDTO compareQuantities(QuantityDTO q1, QuantityDTO q2);

    QuantityMeasurementDTO convertQuantity(QuantityDTO from, QuantityDTO to);

    QuantityMeasurementDTO addQuantities(QuantityDTO q1, QuantityDTO q2);

    QuantityMeasurementDTO subtractQuantities(QuantityDTO q1, QuantityDTO q2);

    QuantityMeasurementDTO multiplyQuantities(QuantityDTO q1, QuantityDTO q2);

    QuantityMeasurementDTO divideQuantities(QuantityDTO q1, QuantityDTO q2);

    // HISTORY (AUTH REQUIRED)
    List<QuantityMeasurementDTO> getAllUserHistory(Authentication authentication);

    List<QuantityMeasurementDTO> getUserOperationHistory(String operation, Authentication authentication);

    List<QuantityMeasurementDTO> getUserMeasurementHistory(String type, Authentication authentication);

    List<QuantityMeasurementDTO> getUserErroredHistory(Authentication authentication);

    // COUNT
    long getOperationCount(String operation);
}