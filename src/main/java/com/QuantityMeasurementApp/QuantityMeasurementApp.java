package com.QuantityMeasurementApp;
/*
 * UC 6 : Unit Addition
 */
public class QuantityMeasurementApp {
 
    public static boolean demonstrateLengthEquality(Length length1, Length length2) {
        boolean isEqual = length1.equals(length2);

        if (isEqual) {
            System.out.println("The two length measurements are equal.");
        } else {
            System.out.println("The two length measurements are not equal.");
        }

        return isEqual;
    }
 
    public static boolean demonstrateLengthComparison(double value1, Length.LengthUnit unit1,
                                                      double value2, Length.LengthUnit unit2) {

        Length length1 = new Length(value1, unit1);
        Length length2 = new Length(value2, unit2);

        return demonstrateLengthEquality(length1, length2);
    }
 
    public static Length demonstrateLengthConversion(double value, Length.LengthUnit fromUnit,
                                                     Length.LengthUnit toUnit) {

        Length originalLength = new Length(value, fromUnit);
        double convertedValue = originalLength.convertTo(toUnit);

        System.out.println(value + " " + fromUnit + " = " + convertedValue + " " + toUnit);

        return new Length(convertedValue, toUnit);
    }

    
    public static Length demonstrateLengthConversion(Length length, Length.LengthUnit toUnit) {
        double convertedValue = length.convertTo(toUnit);
        return new Length(convertedValue, toUnit);
    }

    public static Length demonstrateLengthAddition(Length length1, Length length2) {
        Length result = length1.add(length2);
        System.out.println("Sum = " + result);
        return result;
    }
 
    public static void main(String[] args) {

        // Equality demo
        demonstrateLengthComparison(1.0, Length.LengthUnit.FEET, 12.0, Length.LengthUnit.INCHES);

        // Conversion demo
        demonstrateLengthConversion(1.0, Length.LengthUnit.YARDS, Length.LengthUnit.FEET);

        // Addition demo
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(12.0, Length.LengthUnit.INCHES);
        demonstrateLengthAddition(l1, l2);
    }
}