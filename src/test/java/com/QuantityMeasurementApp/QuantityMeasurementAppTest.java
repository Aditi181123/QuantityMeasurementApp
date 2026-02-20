package com.QuantityMeasurementApp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    //  Feet = Feet (Same Value)
    @Test
    public void testEquality_FeetToFeet_SameValue() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(1.0, Length.LengthUnit.FEET);

        assertEquals(l1, l2);
    }

    //  Inch = Inch (Same Value)
    @Test
    public void testEquality_InchToInch_SameValue() {
        Length l1 = new Length(1.0, Length.LengthUnit.INCH);
        Length l2 = new Length(1.0, Length.LengthUnit.INCH);

        assertEquals(l1, l2);
    }

    //  Feet = Inch (Equivalent)
    @Test
    public void testEquality_FeetToInch_EquivalentValue() {
        Length feet = new Length(1.0, Length.LengthUnit.FEET);
        Length inch = new Length(12.0, Length.LengthUnit.INCH);

        assertEquals(feet, inch);
    }

    //  Inch = Feet (Symmetry)
    @Test
    public void testEquality_InchToFeet_EquivalentValue() {
        Length inch = new Length(12.0, Length.LengthUnit.INCH);
        Length feet = new Length(1.0, Length.LengthUnit.FEET);

        assertEquals(inch, feet);
    }

    //  Feet != Feet (Different Value)
    @Test
    public void testEquality_FeetToFeet_DifferentValue() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(2.0, Length.LengthUnit.FEET);

        assertNotEquals(l1, l2);
    }

    //  Inch != Inch (Different Value)
    @Test
    public void testEquality_InchToInch_DifferentValue() {
        Length l1 = new Length(1.0, Length.LengthUnit.INCH);
        Length l2 = new Length(2.0, Length.LengthUnit.INCH);

        assertNotEquals(l1, l2);
    }

    //  Invalid Unit Test 
    @Test
    public void testEquality_InvalidUnit() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Length(1.0, null);
        });
    }

    //  Null Unit Test
    @Test
    public void testEquality_NullUnit() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Length(5.0, null);
        });
    }

    //  Same Reference 
    @Test
    public void testEquality_SameReference() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);

        assertEquals(l1, l1);
    }

    //  Null Comparison
    @Test
    public void testEquality_NullComparison() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);

        assertNotEquals(l1, null);
    }
}