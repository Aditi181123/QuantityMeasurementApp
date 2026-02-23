package com.QuantityMeasurementApp;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class Unit2UnitTest {
	@Test
    void testConversion_FeetToInches() {
        Length length = new Length(1.0, Length.LengthUnit.FEET);
        assertEquals(12.0, length.convertTo(Length.LengthUnit.INCHES));
    }

    @Test
    void testConversion_InchesToFeet() {
        Length length = new Length(24.0, Length.LengthUnit.INCHES);
        assertEquals(2.0, length.convertTo(Length.LengthUnit.FEET));
    }
    @Test
    void testConversion_YardsToInches() {
        Length length = new Length(1.0, Length.LengthUnit.YARDS);
        assertEquals(36.0, length.convertTo(Length.LengthUnit.INCHES));
    }

    @Test
    void testConversion_InchesToYards() {
        Length length = new Length(72.0, Length.LengthUnit.INCHES);
        assertEquals(2.0, length.convertTo(Length.LengthUnit.YARDS));
    }

    @Test
    void testConversion_CentimetersToInches() {
        Length length = new Length(1.0, Length.LengthUnit.CENTIMETERS);
        assertEquals(0.393701, length.convertTo(Length.LengthUnit.INCHES));
    }

    @Test
    void testConversion_FeetToYard() {
        Length length = new Length(6.0, Length.LengthUnit.FEET);
        assertEquals(2.0, length.convertTo(Length.LengthUnit.YARDS));
    }
    @Test
    void testConversion_RoundTrip_PreservesValue() {
        Length yards = new Length(5.5, Length.LengthUnit.YARDS);
        double feet = yards.convertTo(Length.LengthUnit.FEET);

        Length backToYards = new Length(feet, Length.LengthUnit.FEET);
        double yardsAgain = backToYards.convertTo(Length.LengthUnit.YARDS);

        assertEquals(5.5, yardsAgain);
    }
    @Test
    void testZeroValue() {
        Length length = new Length(0.0, Length.LengthUnit.FEET);
        assertEquals(0.0, length.convertTo(Length.LengthUnit.INCHES));
    }

    @Test
    void testNegativeValue() {
        Length length = new Length(-1.0, Length.LengthUnit.FEET);
        assertEquals(-12.0, length.convertTo(Length.LengthUnit.INCHES));
    }
    @Test
    void testConversion_PrecisionTolerance() {
        Length length = new Length(1.0, Length.LengthUnit.CENTIMETERS);
        double inches = length.convertTo(Length.LengthUnit.INCHES);

        assertEquals(0.393701, inches);
    }
    @Test
    void testConversion_InvalidUnit_Throws() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Length(1.0, null);
        });

        Length length = new Length(1.0, Length.LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class, () -> {
            length.convertTo(null);
        });
    }
    @Test
    void testConversion_NaNOrInfinite_Throws() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Length(Double.NaN, Length.LengthUnit.FEET);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Length(Double.POSITIVE_INFINITY, Length.LengthUnit.FEET);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Length(Double.NEGATIVE_INFINITY, Length.LengthUnit.FEET);
        });
    }
}
