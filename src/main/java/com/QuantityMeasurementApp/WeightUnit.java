package com.QuantityMeasurementApp;
public enum WeightUnit {

    KILOGRAM(1.0),           // Base unit
    GRAM(0.001),             // 1 g = 0.001 kg
    POUND(0.453592);         // 1 lb ≈ 0.453592 kg

    private final double toKilogramFactor;

    WeightUnit(double toKilogramFactor) {
        this.toKilogramFactor = toKilogramFactor;
    }

    public double getConversionFactor() {
        return toKilogramFactor;
    }

    public double convertToBaseUnit(double value) {
        return value * toKilogramFactor;
    }

    public double convertFromBaseUnit(double baseValue) {
        return baseValue / toKilogramFactor;
    }
}