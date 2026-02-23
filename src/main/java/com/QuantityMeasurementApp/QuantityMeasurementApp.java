package com.QuantityMeasurementApp;

/**
 * QuantityMeasurementApp UC4 - Extended Unit Support
 */
public class QuantityMeasurementApp {
 
    public static boolean demonstrateLengthEquality(Length length1, Length length2) {
        return length1.equals(length2);
    }
 
    public static void demonstrateLengthComparison(double value1, Length.LengthUnit unit1,
                                                   double value2, Length.LengthUnit unit2) {

        Length length1 = new Length(value1, unit1);
        Length length2 = new Length(value2, unit2);

        boolean result = demonstrateLengthEquality(length1, length2);

        System.out.println("Comparing: " + value1 + " " + unit1 + "  and  " + value2 + " " + unit2);
        System.out.println("Result: " + result); 
    }
 
    public static void main(String[] args) {

        // Feet and Inches comparison
        demonstrateLengthComparison(1.0, Length.LengthUnit.FEET,
                                    12.0, Length.LengthUnit.INCHES);

        // Yards and Inches comparison
        demonstrateLengthComparison(36.0, Length.LengthUnit.INCHES,
                                    1.0, Length.LengthUnit.YARDS);

        // Centimeters and Inches comparison
        demonstrateLengthComparison(1.0, Length.LengthUnit.CENTIMETERS,
                                    0.393701, Length.LengthUnit.INCHES);

        // Feet and Yards comparison
        demonstrateLengthComparison(3.0, Length.LengthUnit.FEET,
                                    1.0, Length.LengthUnit.YARDS);

        // Centimeters and Feet comparison
        demonstrateLengthComparison(30.48, Length.LengthUnit.CENTIMETERS,
                                    1.0, Length.LengthUnit.FEET);
    }
}