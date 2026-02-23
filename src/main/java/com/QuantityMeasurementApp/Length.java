package com.QuantityMeasurementApp;

import java.util.Objects;

public class Length {

	// instance variable
	private final double value;
	private final LengthUnit unit;
	
	public double getValue() {
	    return value;
	}

	public LengthUnit getUnit() {
	    return unit;
	}

	// enum method for length units
	public enum LengthUnit {
		FEET(12.0),          // 1 ft = 12 in
		INCHES(1.0),          // base unit
		YARDS(36.0),          // 1 yard = 36 in
		CENTIMETERS(0.393701); // 1 cm = 0.393701 in

		private final double toInches;

		LengthUnit(double toInches) {
			this.toInches = toInches;
		}

		public double toInches(double value) {
			return value * toInches;
		}

		public double fromInches(double inches) {
			return inches / toInches;
		}
		
	}

	// constructor to initialize length value and unit
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

	// convert given value to base value
	private double convertToBase() {
		return unit.toInches(value);
	}

	// convert base value to given unit value
	public double convertTo(LengthUnit targetUnit) {
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}
		double inches = convertToBase();
		return targetUnit.fromInches(inches);
	}

	// compare method
	private boolean compare(Length that) {
		return Double.compare(this.convertToBase(), that.convertToBase()) == 0;
	}

	// override given equals method
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

	public Length add(Length thatLength) {
		if (thatLength == null)
			throw new IllegalArgumentException("Length cannot be null");

		double thisInches = this.unit.toInches(this.value);
		double thatInches = thatLength.unit.toInches(thatLength.value);

		double sumInches = thisInches + thatInches;

		// Convert back to this unit
		double resultInOriginalUnit = this.unit.fromInches(sumInches);

		return new Length(resultInOriginalUnit, this.unit);
	}
	 
}