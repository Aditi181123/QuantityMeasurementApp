package com.QuantityMeasurementApp;
public class QuantityMeasurementApp {

	public static void LengthConversion(double value,
			LengthUnit from,
			LengthUnit to) {

		double result = Length.convert(value, from, to);
		System.out.println("convert(" + value + ", " + from + ", " + to + ") → " + result);
	}
	public static void LengthEquality(Length a,
			Length b) {

		System.out.println(a + " equals " + b + " ? " + a.equals(b));
	}

	public static void main(String[] args) {

		LengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCH);
		LengthConversion(3.0, LengthUnit.YARD, LengthUnit.FEET);
		LengthConversion(36.0, LengthUnit.INCH, LengthUnit.YARD);
		LengthConversion(1.0, LengthUnit.CENTIMETER, LengthUnit.INCH);

		LengthEquality(
				new Length(1.0, LengthUnit.YARD),
				new Length(3.0, LengthUnit.FEET)
				);
	}
}