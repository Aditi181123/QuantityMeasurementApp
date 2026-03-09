package com.QuantityMeasurementApp;

import java.util.Objects;

public class QuantityWeight {

    private final double value;
    private final WeightUnit unit;

    private static final double EPS = 1e-6;

    public QuantityWeight(double value, WeightUnit unit) {

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Value must be finite");

        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public WeightUnit getUnit() {
        return unit;
    }

    private double toKilogram() {
        return unit.convertToBaseUnit(value);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        QuantityWeight other = (QuantityWeight) obj;

        return Math.abs(this.toKilogram() - other.toKilogram()) < EPS;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Math.round(toKilogram() / EPS));
    }


    public QuantityWeight convertTo(WeightUnit targetUnit) {

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double base = toKilogram();
        double converted = targetUnit.convertFromBaseUnit(base);

        return new QuantityWeight(converted, targetUnit);
    }

    // -------------------------
    // Addition (implicit target)
    // -------------------------

    public QuantityWeight add(QuantityWeight other) {

        if (other == null)
            throw new IllegalArgumentException("Other weight cannot be null");

        double sumBase = this.toKilogram() + other.toKilogram();
        double result = this.unit.convertFromBaseUnit(sumBase);

        return new QuantityWeight(result, this.unit);
    }



    public QuantityWeight add(QuantityWeight other,
                              WeightUnit targetUnit) {

        if (other == null)
            throw new IllegalArgumentException("Other weight cannot be null");

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double sumBase = this.toKilogram() + other.toKilogram();
        double result = targetUnit.convertFromBaseUnit(sumBase);

        return new QuantityWeight(result, targetUnit);
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}