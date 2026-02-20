package com.QuantityMeasurementApp;

import java.util.Objects;

public class Length {

    private final double value;
    private final LengthUnit unit;

    public enum LengthUnit {
        FEET(12.0),          // 1 ft = 12 in
        INCHES(1.0),          // base unit
        YARDS(36.0),          // 1 yard = 36 in
        CENTIMETERS(0.393701); // 1 cm = 0.393701 in

        private final double toInches;

        LengthUnit(double toInches) {
            this.toInches = toInches;
        }

        public double toInches() {
            return toInches;
        }
    }

    public Length(double value, LengthUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    private double convertToInches() {
        return value * unit.toInches();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Length)) return false;
        Length length = (Length) o;
        return Double.compare(this.convertToInches(), length.convertToInches()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(convertToInches());
    }
}