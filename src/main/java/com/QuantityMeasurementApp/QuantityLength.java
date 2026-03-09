package com.QuantityMeasurementApp;

import java.util.*;

public class QuantityLength {

    private final double value;
    private final LengthUnit unit;

    private static final int SCALE = 6;

    public QuantityLength(double value, LengthUnit unit) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("The value must be finite");
        }
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }

        this.value = round(value);  // round at object creation
        this.unit = unit;
    }

    private double toFeet() {
        return unit.toFeet(value);
    }

    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }


    private static double round(double value) {
        double factor = Math.pow(10, SCALE);
        return Math.round(value * factor) / factor;
    }

    public static double convert(double value, LengthUnit source, LengthUnit target) {

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("The value must be finite");
        }
        if (source == null || target == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }

        double valueToFeet = source.toFeet(value);
        double result = target.fromFeet(valueToFeet);

        return round(result);
    }

    public QuantityLength add(QuantityLength thatLength) {

        if (thatLength == null)
            throw new IllegalArgumentException("Value cannot be null");

        double thisInFeet = this.unit.toFeet(this.value);
        double thatLengthInFeet = thatLength.unit.toFeet(thatLength.value);

        double sumInFeet = thisInFeet + thatLengthInFeet;

        double resultInTarget = this.unit.fromFeet(sumInFeet);

        return new QuantityLength(round(resultInTarget), this.unit);
    }

    public static QuantityLength add(QuantityLength length1,
                                     QuantityLength length2,
                                     LengthUnit targetUnit) {

        if (length1 == null || length2 == null)
            throw new IllegalArgumentException("Operands cannot be null");

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double lengthOne = length1.getUnit().toFeet(length1.getValue());
        double lengthSecond = length2.getUnit().toFeet(length2.getValue());

        double sumInFeet = lengthOne + lengthSecond;

        double result = targetUnit.fromFeet(sumInFeet);

        return new QuantityLength(round(result), targetUnit);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        QuantityLength other = (QuantityLength) obj;

        return Double.compare(this.toFeet(), other.toFeet()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(toFeet());
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}