package com.qm.qma.dto;

/**
 * Generic result DTO for operations
 */
public class OperationResult {
    private Double result;
    private String resultUnit;
    private String operation;

    public OperationResult() {}

    public OperationResult(Double result, String resultUnit, String operation) {
        this.result = result;
        this.resultUnit = resultUnit;
        this.operation = operation;
    }

    public Double getResult() { return result; }
    public void setResult(Double result) { this.result = result; }

    public String getResultUnit() { return resultUnit; }
    public void setResultUnit(String resultUnit) { this.resultUnit = resultUnit; }

    public String getOperation() { return operation; }
    public void setOperation(String operation) { this.operation = operation; }
}
