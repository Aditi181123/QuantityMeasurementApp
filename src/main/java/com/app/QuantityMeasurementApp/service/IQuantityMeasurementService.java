package com.app.QuantityMeasurementApp.service;

import java.util.List;
import com.app.QuantityMeasurementApp.DTO.*;

/*
 *  SERVICE INTERFACE (BUSINESS LAYER CONTRACT)
 *
 *  Defines all operations:
 *    ✔ Compare
 *    ✔ Convert
 *    ✔ Arithmetic (Add, Subtract, Divide)
 *    ✔ History / Reporting
 *
 *  Implemented by:
 *    → QuantityMeasurementServiceImpl
 */

public interface IQuantityMeasurementService {

    //  COMPARE
    QuantityMeasurementDTO compare(QuantityDTO thisQ, QuantityDTO thatQ);

    //  CONVERT
    QuantityMeasurementDTO convert(QuantityDTO thisQ, String targetUnit);

    //  ADD (Overloaded)
    QuantityMeasurementDTO add(QuantityDTO thisQ, QuantityDTO thatQ);
    QuantityMeasurementDTO add(QuantityDTO thisQ, QuantityDTO thatQ, String targetUnit);

    //  SUBTRACT (Overloaded)
    QuantityMeasurementDTO subtract(QuantityDTO thisQ, QuantityDTO thatQ);
    QuantityMeasurementDTO subtract(QuantityDTO thisQ, QuantityDTO thatQ, String targetUnit);

    //  DIVIDE
    QuantityMeasurementDTO divide(QuantityDTO thisQ, QuantityDTO thatQ);

   

    //  Get all records
    List<QuantityMeasurementDTO> getAllMeasurements();

    //  Filter by operation (ADD, SUBTRACT, etc.)
    List<QuantityMeasurementDTO> getMeasurementsByOperation(String operation);

    //  Filter by measurement type (LengthUnit, etc.)
    List<QuantityMeasurementDTO> getMeasurementsByType(String measurementType);

    //  Get only error records
    List<QuantityMeasurementDTO> getErrorHistory();

    //  Count successful operations
    long getOperationCount(String operation);
}