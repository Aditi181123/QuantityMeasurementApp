package com.app.QuantityMeasurementApp.service;

import com.app.QuantityMeasurementApp.DTO.*;
import com.app.QuantityMeasurementApp.model.*;
import com.app.QuantityMeasurementApp.repository.*;

import org.springframework.security.core.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private final QuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(QuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    private QuantityMeasurementDTO process(String operation, QuantityDTO q1, QuantityDTO q2) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        entity.setThisValue(q1.getValue());
        entity.setThisUnit(q1.getUnit());
        entity.setThisMeasurementType(q1.getMeasurementType().name());
        entity.setThatValue(q2.getValue());
        entity.setThatUnit(q2.getUnit());
        entity.setThatMeasurementType(q2.getMeasurementType().name());
        entity.setOperation(operation);

        try {
            double result = q1.getValue();

            entity.setResultValue(result);
            entity.setResultUnit(q1.getUnit());
            entity.setResultMeasurementType(q1.getMeasurementType().name());
            entity.setError(false);

        } catch (Exception e) {
            entity.setError(true);
            entity.setErrorMessage(e.getMessage());
        }

        repository.save(entity);

        return QuantityMeasurementDTO.fromEntity(entity);
    }

    @Override
    public QuantityMeasurementDTO compareQuantities(QuantityDTO q1, QuantityDTO q2) {
        return process("COMPARE", q1, q2);
    }

    @Override
    public QuantityMeasurementDTO convertQuantity(QuantityDTO q1, QuantityDTO q2) {
        return process("CONVERT", q1, q2);
    }

    @Override
    public QuantityMeasurementDTO addQuantities(QuantityDTO q1, QuantityDTO q2) {
        return process("ADD", q1, q2);
    }

    @Override
    public QuantityMeasurementDTO subtractQuantities(QuantityDTO q1, QuantityDTO q2) {
        return process("SUBTRACT", q1, q2);
    }

    @Override
    public QuantityMeasurementDTO multiplyQuantities(QuantityDTO q1, QuantityDTO q2) {
        return process("MULTIPLY", q1, q2);
    }

    @Override
    public QuantityMeasurementDTO divideQuantities(QuantityDTO q1, QuantityDTO q2) {
        return process("DIVIDE", q1, q2);
    }

    @Override
    public List<QuantityMeasurementDTO> getAllUserHistory(Authentication auth) {
        String username = auth.getName();
        return repository.findByUsername(username)
                .stream()
                .map(QuantityMeasurementDTO::fromEntity)
                .toList();
    }

    @Override
    public List<QuantityMeasurementDTO> getUserOperationHistory(String operation, Authentication auth) {
        String username = auth.getName();
        return repository.findByUsernameAndOperation(username, operation)
                .stream()
                .map(QuantityMeasurementDTO::fromEntity)
                .toList();
    }

    @Override
    public List<QuantityMeasurementDTO> getUserMeasurementHistory(String type, Authentication auth) {
        String username = auth.getName();
        return repository.findByUsernameAndMeasurementType(username, type)
                .stream()
                .map(QuantityMeasurementDTO::fromEntity)
                .toList();
    }

    @Override
    public List<QuantityMeasurementDTO> getUserErroredHistory(Authentication auth) {
        String username = auth.getName();
        return repository.findByUsernameAndErroredTrue(username)
                .stream()
                .map(QuantityMeasurementDTO::fromEntity)
                .toList();
    }

    @Override
    public long getOperationCount(String operation) {
        return repository.countByOperation(operation);
    }
}