package com.app.QuantityMeasurementApp.Exception;

public class QuantityMeasurementException extends RuntimeException {

    // Constructor with message
    public QuantityMeasurementException(String msg) {
        super(msg);
    }

    // Constructor with message and cause
    public QuantityMeasurementException(String msg, Throwable cause) {
        super(msg, cause);
    }

    // Main method for testing
    public static void main(String[] args) {
        try {
            throw new QuantityMeasurementException(
                    "Test exception for Quantity Measurement."
            );
        } catch (QuantityMeasurementException ex) {
            System.out.println("QuantityMeasurementException Caught: " + ex.getMessage());
        }
    }
}