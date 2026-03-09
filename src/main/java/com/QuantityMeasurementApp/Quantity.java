package com.QuantityMeasurementApp;

import java.util.Objects;

public class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;

    private static final int SCALE = 6;

    public Quantity(double value, U unit) {
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Value must be finite");
        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        this.value = round(value);
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    private double toBase() {
        return round(unit.convertToBaseUnit(value));
    }

    private static double round(double value) {
        double factor = Math.pow(10, SCALE);
        return Math.round(value * factor) / factor;
    }

    public Quantity<U> convertTo(U targetUnit) {
        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double base = toBase();
        double converted = targetUnit.convertFromBaseUnit(base);

        return new Quantity<>(round(converted), targetUnit);
    }

    public Quantity<U> add(Quantity<U> other) {
        if (other == null)
            throw new IllegalArgumentException("Other cannot be null");

        double sumBase = this.toBase() + other.toBase();
        double result = unit.convertFromBaseUnit(sumBase);

        return new Quantity<>(round(result), unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        if (other == null)
            throw new IllegalArgumentException("Other cannot be null");
        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double sumBase = this.toBase() + other.toBase();
        double result = targetUnit.convertFromBaseUnit(sumBase);

        return new Quantity<>(round(result), targetUnit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        Quantity<?> other = (Quantity<?>) obj;

        if (this.unit.getClass() != other.unit.getClass())
            return false;

        return round(this.toBase()) == round(other.toBase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(unit.getClass(), round(toBase()));
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit.getUnitName() + ")";
    }
}