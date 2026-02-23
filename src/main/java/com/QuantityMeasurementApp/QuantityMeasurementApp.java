package com.QuantityMeasurementApp;
/*
 * UC5 - Unit-to-Unit Conversion
 */

public class QuantityMeasurementApp {
	public static boolean demonstrateLengthEquality(Length l1 , Length l2) {
		return l1.equals(l2);
	}

	public static boolean demonstrateLengthComparison(double value1, Length.LengthUnit unit1,
			double value2, Length.LengthUnit unit2) {
		Length length1 = new Length(value1, unit1);
		Length length2 = new Length(value2, unit2);

		return demonstrateLengthEquality(length1, length2);
	}
	public static Length demonstrateLengthConversion(double value , Length.LengthUnit from , Length.LengthUnit to) {
		Length l1 = new Length(value , from);
		double v1 = l1.convertTo(to) ;
		Length l2 = new Length(v1 , to);
		return l2;
	}

	public static Length demonstrateLengthConversion(Length l1 , Length.LengthUnit toUnit) {
		double v2 = l1.convertTo(toUnit);
		return new Length(v2, toUnit);
	}

	public static void main(String [] args) {
		System.out.println(demonstrateLengthConversion(1.0 , Length.LengthUnit.FEET , Length.LengthUnit.INCHES));
		System.out.println(demonstrateLengthConversion(3.0 , Length.LengthUnit.YARDS , Length.LengthUnit.FEET));
		System.out.println(demonstrateLengthConversion(36.0 , Length.LengthUnit.INCHES , Length.LengthUnit.YARDS));
		System.out.println(demonstrateLengthConversion(-1.0 , Length.LengthUnit.CENTIMETERS , Length.LengthUnit.INCHES));
		System.out.println(demonstrateLengthConversion(0.0 , Length.LengthUnit.FEET , Length.LengthUnit.INCHES));
	}
}
