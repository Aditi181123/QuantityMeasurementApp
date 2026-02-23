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
		if (unit == null) {
			throw new IllegalArgumentException("Unit cannot be null");
		}
		if (Double.isNaN(value) || Double.isInfinite(value)) {
			throw new IllegalArgumentException("Value must be finite number");
		}
		this.value = value;
		this.unit = unit;
	}

	private double convertToBase() {
		return value * unit.toInches();
	}

	public double convertTo(LengthUnit targetUnit) {
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}
		double valueInInches = convertToBase();
		return valueInInches / targetUnit.toInches();
	}

	private boolean compare(Length thatLength) {
		return Double.compare(this.convertToBase(), thatLength.convertToBase()) == 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Length)) return false;
		Length length = (Length) o;
		return compare(length);
	}

	@Override
	public String toString() {
		return value + " " + unit;
	}
	@Override
	public int hashCode() {
		return Objects.hash(convertToBase());
	}
}