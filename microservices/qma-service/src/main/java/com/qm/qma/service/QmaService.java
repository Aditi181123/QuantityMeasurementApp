package com.qm.qma.service;
import com.qm.qma.dto.*;
import com.qm.qma.model.MeasurementHistory;
import com.qm.qma.model.MeasurementType;
import com.qm.qma.model.UnitDefinition;
import com.qm.qma.repository.MeasurementHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class QmaService {

    private final MeasurementHistoryRepository historyRepository;

    public QmaService(MeasurementHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

   
    public Double convert(ConvertRequest request) {
        UnitDefinition fromUnit = UnitDefinition.fromString(request.getFrom());
        UnitDefinition toUnit = UnitDefinition.fromString(request.getTo());

        // Units must be of the same type (can't convert length to weight)
        if (fromUnit.getType() != toUnit.getType()) {
            throw new IllegalArgumentException("Cannot convert between different measurement types");
        }

        // Convert: value → base → target unit
        double baseValue = fromUnit.toBaseUnit(request.getValue());
        return UnitDefinition.fromBaseToUnit(baseValue, toUnit);
    }

 
    public CompareResult compare(OperationRequest request) {
        UnitDefinition firstUnit = UnitDefinition.fromString(request.getFirstUnit());
        UnitDefinition secondUnit = UnitDefinition.fromString(request.getSecondUnit());

        if (firstUnit.getType() != secondUnit.getType()) {
            throw new IllegalArgumentException("Cannot compare different measurement types");
        }

        double firstBase = firstUnit.toBaseUnit(request.getFirstValue());
        double secondBase = secondUnit.toBaseUnit(request.getSecondValue());

        String comparison;
        if (firstBase > secondBase) {
            comparison = ">";
        } else if (firstBase < secondBase) {
            comparison = "<";
        } else {
            comparison = "=";
        }

        return new CompareResult(
            comparison,
            request.getFirstValue(),
            request.getFirstUnit(),
            request.getSecondValue(),
            request.getSecondUnit()
        );
    }


    public OperationResult add(OperationRequest request, String userId) {
        UnitDefinition firstUnit = UnitDefinition.fromString(request.getFirstUnit());
        UnitDefinition secondUnit = UnitDefinition.fromString(request.getSecondUnit());

        validateArithmeticOperation(firstUnit, secondUnit);

        double firstBase = firstUnit.toBaseUnit(request.getFirstValue());
        double secondBase = secondUnit.toBaseUnit(request.getSecondValue());
        double resultBase = firstBase + secondBase;
        double result = UnitDefinition.fromBaseToUnit(resultBase, firstUnit);

        return saveHistoryAndReturn(userId, "ADD", request, result, firstUnit.name());
    }

 
    public OperationResult subtract(OperationRequest request, String userId) {
        UnitDefinition firstUnit = UnitDefinition.fromString(request.getFirstUnit());
        UnitDefinition secondUnit = UnitDefinition.fromString(request.getSecondUnit());

        validateArithmeticOperation(firstUnit, secondUnit);

        double firstBase = firstUnit.toBaseUnit(request.getFirstValue());
        double secondBase = secondUnit.toBaseUnit(request.getSecondValue());
        double resultBase = firstBase - secondBase;
        double result = UnitDefinition.fromBaseToUnit(resultBase, firstUnit);

        return saveHistoryAndReturn(userId, "SUBTRACT", request, result, firstUnit.name());
    }

    public OperationResult multiply(OperationRequest request, String userId) {
        UnitDefinition firstUnit = UnitDefinition.fromString(request.getFirstUnit());
        UnitDefinition secondUnit = UnitDefinition.fromString(request.getSecondUnit());

        validateArithmeticOperation(firstUnit, secondUnit);

        // Convert second value to first unit, then multiply
        double firstBase = firstUnit.toBaseUnit(request.getFirstValue());
        double secondBase = secondUnit.toBaseUnit(request.getSecondValue());
        double resultBase = firstBase * secondBase;
        double result = UnitDefinition.fromBaseToUnit(resultBase, firstUnit);

        return saveHistoryAndReturn(userId, "MULTIPLY", request, result, firstUnit.name());
    }

    public OperationResult divide(OperationRequest request, String userId) {
        UnitDefinition firstUnit = UnitDefinition.fromString(request.getFirstUnit());
        UnitDefinition secondUnit = UnitDefinition.fromString(request.getSecondUnit());

        validateArithmeticOperation(firstUnit, secondUnit);

        if (request.getSecondValue() == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }

        // Convert both to base, divide, convert back to first unit
        double firstBase = firstUnit.toBaseUnit(request.getFirstValue());
        double secondBase = secondUnit.toBaseUnit(request.getSecondValue());
        double resultBase = firstBase / secondBase;
        double result = UnitDefinition.fromBaseToUnit(resultBase, firstUnit);

        return saveHistoryAndReturn(userId, "DIVIDE", request, result, firstUnit.name());
    }


    private void validateArithmeticOperation(UnitDefinition firstUnit, UnitDefinition secondUnit) {
        if (firstUnit.getType() != secondUnit.getType()) {
            throw new IllegalArgumentException("Cannot perform arithmetic on different measurement types");
        }
        if (firstUnit.getType() == MeasurementType.TEMPERATURE) {
            throw new IllegalArgumentException("Temperature cannot be used in arithmetic");
        }
    }
    

    private OperationResult saveHistoryAndReturn(String userId, String operation,
            OperationRequest request, double result, String resultUnit) {
        
        MeasurementHistory history = new MeasurementHistory();
        history.setUserId(userId);
        history.setOperation(operation);
        history.setFirstValue(request.getFirstValue());
        history.setFirstUnit(request.getFirstUnit());
        history.setSecondValue(request.getSecondValue());
        history.setSecondUnit(request.getSecondUnit());
        history.setResult(result);
        history.setResultUnit(resultUnit);
        history.setMeasurementType(UnitDefinition.fromString(request.getFirstUnit()).getType().name());

        historyRepository.save(history);

        return new OperationResult(result, resultUnit, operation);
    }

    /**
     * Get user history from database
     */
    public List<MeasurementHistory> getHistory(String userId) {
        return historyRepository.findByUserIdOrderByTimestampDesc(userId);
    }
}
