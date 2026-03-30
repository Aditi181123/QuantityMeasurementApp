package com.app.QuantityMeasurementApp.service;

import com.app.QuantityMeasurementApp.core.*;
import com.app.QuantityMeasurementApp.DTO.*;
import com.app.QuantityMeasurementApp.model.*;
import com.app.QuantityMeasurementApp.Exception.*;
import com.app.QuantityMeasurementApp.repository.IQuantityMeasurementRepository;
import com.app.QuantityMeasurementApp.operation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * 🔷 SERVICE IMPLEMENTATION (BUSINESS LOGIC)
 *
 * 📌 Responsibilities:
 *    ✔ Perform operations (compare, convert, add, subtract, divide)
 *    ✔ Convert DTO → Model
 *    ✔ Save results in DB
 *    ✔ Handle errors
 */

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private static final Logger logger =
            LoggerFactory.getLogger(QuantityMeasurementServiceImpl.class);

    private final IQuantityMeasurementRepository repository;

    //  Constructor Injection
    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        this.repository = repository;
        logger.info("QuantityMeasurementServiceImpl initialized");
    }

    private  IMeasurable resolveUnit(String unitName) {

        try { return LengthUnit.valueOf(unitName.toUpperCase()); } catch (Exception ignored) {}
        try { return WeightUnit.valueOf(unitName.toUpperCase()); } catch (Exception ignored) {}
        try { return VolumeUnit.valueOf(unitName.toUpperCase()); } catch (Exception ignored) {}
        try { return TemperatureUnit.valueOf(unitName.toUpperCase()); } catch (Exception ignored) {}

        throw new QuantityMeasurementException("Invalid unit: " + unitName);
    }

    //  Convert DTO → Model
    private QuantityModel<IMeasurable> toModel(Double value, String unitName) {

        if (value == null || unitName == null)
            throw new QuantityMeasurementException("Value or Unit cannot be null");

        return new QuantityModel<>(value, resolveUnit(unitName));
    }

    //  Build Entity
    private QuantityMeasurementEntity buildEntity(
            QuantityModel<IMeasurable> m1,
            QuantityModel<IMeasurable> m2,
            String operation,
            String resultString,
            QuantityModel<IMeasurable> resultModel,
            boolean isError,
            String errorMessage) {

        QuantityMeasurementEntity e = new QuantityMeasurementEntity();

        if (m1 != null) {
            e.setThisValue(m1.getValue());
            e.setThisUnit(m1.getUnit().getUnitName());
            e.setThisMeasurementType(m1.getUnit().getMeasurementType());
        }

        if (m2 != null) {
            e.setThatValue(m2.getValue());
            e.setThatUnit(m2.getUnit().getUnitName());
            e.setThatMeasurementType(m2.getUnit().getMeasurementType());
        }

        if (resultModel != null) {
            e.setResultValue(resultModel.getValue());
            e.setResultUnit(resultModel.getUnit().getUnitName());
            e.setResultMeasurementType(resultModel.getUnit().getMeasurementType());
        }

        e.setOperation(operation);
        e.setResultString(resultString);
        e.setError(isError);
        e.setErrorMessage(errorMessage);

        return e;
    }

    //  COMPARE
    @Override
    public QuantityMeasurementDTO compare(QuantityDTO dto1, QuantityDTO dto2) {

        QuantityModel<IMeasurable> m1 =
                toModel(dto1.getThisValue(), dto1.getThisUnit());

        QuantityModel<IMeasurable> m2 =
                toModel(dto2.getThatValue(), dto2.getThatUnit());

        try {
            boolean equal = Math.abs(
                    m1.getUnit().convertToBaseUnit(m1.getValue()) -
                    m2.getUnit().convertToBaseUnit(m2.getValue())
            ) <= 1e-5;

            QuantityMeasurementEntity e = buildEntity(
                    m1, m2, "COMPARE", equal ? "Equal" : "Not Equal",
                    null, false, null
            );

            repository.save(e);
            return QuantityMeasurementDTO.fromEntity(e);

        } catch (Exception ex) {
            QuantityMeasurementEntity e = buildEntity(
                    m1, m2, "COMPARE", null,
                    null, true, ex.getMessage()
            );

            repository.save(e);
            return QuantityMeasurementDTO.fromEntity(e);
        }
    }

    //  CONVERT
    @Override
    public QuantityMeasurementDTO convert(QuantityDTO dto, String targetUnitName) {

        QuantityModel<IMeasurable> m =
                toModel(dto.getThisValue(), dto.getThisUnit());

        try {
            IMeasurable targetUnit = resolveUnit(targetUnitName);

            Quantity<IMeasurable> converted = new Quantity<>(m.getValue(), m.getUnit()).convertTo(targetUnit);

            QuantityModel<IMeasurable> resultModel =
                    new QuantityModel<>(converted.getValue(), converted.getUnit());

            QuantityMeasurementEntity e = buildEntity(
                    m, null, "CONVERT", null,
                    resultModel, false, null
            );

            repository.save(e);
            return QuantityMeasurementDTO.fromEntity(e);

        } catch (Exception ex) {
            QuantityMeasurementEntity e = buildEntity(
                    m, null, "CONVERT", null,
                    null, true, ex.getMessage()
            );

            repository.save(e);
            return QuantityMeasurementDTO.fromEntity(e);
        }
    }

    //  ADD (default)
    @Override
    public QuantityMeasurementDTO add(QuantityDTO dto1, QuantityDTO dto2) {
        return add(dto1, dto2, dto1.getThisUnit());
    }

    //  ADD (with target unit)
    @Override
    public QuantityMeasurementDTO add(QuantityDTO dto1, QuantityDTO dto2, String targetUnit) {

        QuantityModel<IMeasurable> m1 =
                toModel(dto1.getThisValue(), dto1.getThisUnit());

        QuantityModel<IMeasurable> m2 =
                toModel(dto2.getThatValue(), dto2.getThatUnit());

        try {
            IMeasurable unit = resolveUnit(targetUnit);

            Quantity<IMeasurable> q1 =
                    new Quantity<>(m1.getValue(), m1.getUnit());

            Quantity<IMeasurable> q2 =
                    new Quantity<>(m2.getValue(), m2.getUnit());

            Quantity<IMeasurable> result = q1.add(q2);
            
            QuantityMeasurementEntity e = buildEntity(
                    m1, m2, "ADD", null,
                    new QuantityModel<>(result.getValue(), result.getUnit()),
                    false, null
            );

            repository.save(e);
            return QuantityMeasurementDTO.fromEntity(e);

        } catch (Exception ex) {
            QuantityMeasurementEntity e = buildEntity(
                    m1, m2, "ADD", null, null, true, ex.getMessage()
            );

            repository.save(e);
            return QuantityMeasurementDTO.fromEntity(e);
        }
    }

    //  SUBTRACT (default)
    @Override
    public QuantityMeasurementDTO subtract(QuantityDTO dto1, QuantityDTO dto2) {
        return subtract(dto1, dto2, dto1.getThisUnit());
    }

    //  SUBTRACT (with target unit)
    @Override
    public QuantityMeasurementDTO subtract(QuantityDTO dto1, QuantityDTO dto2, String targetUnit) {

        QuantityModel<IMeasurable> m1 =
                toModel(dto1.getThisValue(), dto1.getThisUnit());

        QuantityModel<IMeasurable> m2 =
                toModel(dto2.getThatValue(), dto2.getThatUnit());

        try {
            IMeasurable unit = resolveUnit(targetUnit);

            
            Quantity<IMeasurable> q1 =
                    new Quantity<>(m1.getValue(), m1.getUnit());

            Quantity<IMeasurable> q2 =
                    new Quantity<>(m2.getValue(), m2.getUnit());

            Quantity<IMeasurable> result = q1.subtract(q2);

            QuantityMeasurementEntity e = buildEntity(
                    m1, m2, "SUBTRACT", null,
                    new QuantityModel<>(result.getValue(), result.getUnit()),
                    false, null
            );

            repository.save(e);
            return QuantityMeasurementDTO.fromEntity(e);

        } catch (Exception ex) {
            QuantityMeasurementEntity e = buildEntity(
                    m1, m2, "SUBTRACT", null, null, true, ex.getMessage()
            );

            repository.save(e);
            return QuantityMeasurementDTO.fromEntity(e);
        }
    }

    //  DIVIDE
    @Override
    public QuantityMeasurementDTO divide(QuantityDTO dto1, QuantityDTO dto2) {

        QuantityModel<IMeasurable> m1 =
                toModel(dto1.getThisValue(), dto1.getThisUnit());

        QuantityModel<IMeasurable> m2 =
                toModel(dto2.getThatValue(), dto2.getThatUnit());

        try {
        	Quantity<IMeasurable> q1 =
        	        new Quantity<>(m1.getValue(), m1.getUnit());

        	Quantity<IMeasurable> q2 =
        	        new Quantity<>(m2.getValue(), m2.getUnit());

        	double result = q1.divide(q2);

            QuantityMeasurementEntity e = buildEntity(
                    m1, m2, "DIVIDE", String.valueOf(result),
                    null, false, null
            );

            repository.save(e);
            return QuantityMeasurementDTO.fromEntity(e);

        } catch (Exception ex) {
            QuantityMeasurementEntity e = buildEntity(
                    m1, m2, "DIVIDE", null, null, true, ex.getMessage()
            );

            repository.save(e);
            return QuantityMeasurementDTO.fromEntity(e);
        }
    }

    //  HISTORY

    @Override
    public List<QuantityMeasurementDTO> getAllMeasurements() {
        return QuantityMeasurementDTO.fromEntityList(repository.findAll());
    }

    @Override
    public List<QuantityMeasurementDTO> getMeasurementsByOperation(String operation) {
        return QuantityMeasurementDTO.fromEntityList(
                repository.findByOperation(operation.toUpperCase()));
    }

    @Override
    public List<QuantityMeasurementDTO> getMeasurementsByType(String measurementType) {
        return QuantityMeasurementDTO.fromEntityList(
                repository.findByThisMeasurementType(measurementType));
    }

    @Override
    public List<QuantityMeasurementDTO> getErrorHistory() {
        return QuantityMeasurementDTO.fromEntityList(
                repository.findByIsErrorTrue());
    }

    @Override
    public long getOperationCount(String operation) {
        return repository.countByOperationAndIsErrorFalse(operation.toUpperCase());
    }
}