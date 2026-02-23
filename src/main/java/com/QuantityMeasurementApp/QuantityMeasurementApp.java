package com.QuantityMeasurementApp;

/*
 * UC7: Addition with Target Unit Specification
 */

public class QuantityMeasurementApp {

    // UC1 Equality
    public static boolean demonstrateLengthEquality(Length l1, Length l2) {
        boolean equal = l1.equals(l2);
        System.out.println("Equal? " + equal);
        return equal;
    }

    // UC3 Conversion
    public static Length demonstrateLengthConversion(double value, Length.LengthUnit from,
                                                     Length.LengthUnit to) {
        Length l = new Length(value, from);
        double converted = l.convertTo(to);
        System.out.println(value + " " + from + " = " + converted + " " + to);
        return new Length(converted, to);
    }

    // UC6 Addition (default target = first operand unit)
    public static Length demonstrateLengthAddition(Length l1, Length l2) {
        Length result = l1.add(l2);
        System.out.println("Sum = " + result);
        return result;
    }

    // UC7 Addition with Explicit Target Unit
    public static Length demonstrateLengthAddition(Length l1, Length l2,
                                                   Length.LengthUnit targetUnit) {
        Length result = l1.add(l2, targetUnit);
        System.out.println("Sum in " + targetUnit + " = " + result);
        return result;
    }
 
    public static void main(String[] args) {

        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(12.0, Length.LengthUnit.INCHES);
        Length l3 = new Length(1.0, Length.LengthUnit.YARDS);
        Length l4 = new Length(3.0, Length.LengthUnit.FEET);

        // UC6 demo
        demonstrateLengthAddition(l1, l2);

        // UC7 demos
        demonstrateLengthAddition(l1, l2, Length.LengthUnit.FEET);
        demonstrateLengthAddition(l1, l2, Length.LengthUnit.INCHES);
        demonstrateLengthAddition(l1, l2, Length.LengthUnit.YARDS);

        demonstrateLengthAddition(l3, l4, Length.LengthUnit.YARDS);
        demonstrateLengthAddition(new Length(2.54, Length.LengthUnit.CENTIMETERS),
                                  new Length(1.0, Length.LengthUnit.INCHES),
                                  Length.LengthUnit.CENTIMETERS);
    }
}