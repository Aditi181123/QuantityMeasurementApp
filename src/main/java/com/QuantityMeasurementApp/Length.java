package com.QuantityMeasurementApp;

public class Length {
 
    private double value;
    private LengthUnit unit;

    
    public enum LengthUnit {
        FEET(12.0),   // 1 FEET = 12 INCH
        INCH(1.0);    // 1 INCH = 1 INCH

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double getConversionFactor() {
            return conversionFactor;
        }
    }
 
    public Length(double value, LengthUnit unit) {
        if (Double.isNaN(value)) {
            throw new IllegalArgumentException("Invalid numeric value");
        }
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        this.value = value;
        this.unit = unit;
    }
 
    private double convertToBaseUnit() {
        return value * unit.getConversionFactor();
    }
 
    public boolean compare(Length thatLength) {
        return Double.compare(this.convertToBaseUnit(), thatLength.convertToBaseUnit()) == 0;
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Length thatLength = (Length) o;
        return this.compare(thatLength);
    }
 
    @Override
    public String toString() {
        return value + " " + unit;
    }
 
    @Override
    public int hashCode() {
        return Double.hashCode(convertToBaseUnit());
    }
}