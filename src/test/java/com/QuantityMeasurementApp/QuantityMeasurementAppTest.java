package com.QuantityMeasurementApp;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    @Test
    void testAddition_ExplicitTargetUnit_Feet() {

        Length length1 = new Length(1.0, LengthUnit.FEET);
        Length length2 = new Length(12.0, LengthUnit.INCH);

        Length result =
                Length.add(length1, length2, LengthUnit.FEET);

        assertEquals(2.0, result.getValue());
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    void testAddition_ExplicitTargetUnit_Inches() {

        Length length1 = new Length(1.0, LengthUnit.FEET);
        Length length2 = new Length(12.0, LengthUnit.INCH);

        Length result =
                Length.add(length1, length2, LengthUnit.INCH);

        assertEquals(24.0, result.getValue());
        assertEquals(LengthUnit.INCH, result.getUnit());
    }

    @Test
    void testAddition_ExplicitTargetUnit_Yards() {

        Length length1 = new Length(1.0, LengthUnit.FEET);
        Length length2 = new Length(12.0, LengthUnit.INCH);

        Length result =
                Length.add(length1, length2, LengthUnit.YARD);

        assertEquals(0.666667, result.getValue());
        assertEquals(LengthUnit.YARD, result.getUnit());
    }

    @Test
    void testAddition_ExplicitTargetUnit_Centimeters() {

        Length length1 = new Length(1.0, LengthUnit.INCH);
        Length length2 = new Length(1.0, LengthUnit.INCH);

        Length result =
                Length.add(length1, length2, LengthUnit.CENTIMETER);

        assertEquals(0.555556, result.getValue());
        assertEquals(LengthUnit.CENTIMETER, result.getUnit());
    }

    @Test
    void testAddition_ExplicitTargetUnit_SameAsFirstOperand() {

        Length length1 = new Length(2.0, LengthUnit.YARD);
        Length length2 = new Length(3.0, LengthUnit.FEET);

        Length result =
                Length.add(length1, length2, LengthUnit.YARD);

        assertEquals(3.0, result.getValue());
    }

    @Test
    void testAddition_ExplicitTargetUnit_SameAsSecondOperand() {

        Length length1 = new Length(2.0, LengthUnit.YARD);
        Length length2 = new Length(3.0, LengthUnit.FEET);

        Length result =
                Length.add(length1, length2, LengthUnit.FEET);

        assertEquals(9.0, result.getValue());
    }

    @Test
    void testAddition_ExplicitTargetUnit_Commutativity() {

        Length length1 = new Length(1.0, LengthUnit.FEET);
        Length length2 = new Length(12.0, LengthUnit.INCH);

        Length result1 =
                Length.add(length1, length2, LengthUnit.YARD);

        Length result2 =
                Length.add(length2, length1, LengthUnit.YARD);

        assertEquals(result1.getValue(), result2.getValue());
    }

    @Test
    void testAddition_ExplicitTargetUnit_WithZero() {

        Length length1 = new Length(5.0, LengthUnit.FEET);
        Length length2 = new Length(0.0, LengthUnit.INCH);

        Length result =
                Length.add(length1, length2, LengthUnit.YARD);

        assertEquals(1.666667, result.getValue()); // assuming rounding
    }

    @Test
    void testAddition_ExplicitTargetUnit_NegativeValues() {

        Length length1 = new Length(5.0, LengthUnit.FEET);
        Length length2 = new Length(-2.0, LengthUnit.FEET);

        Length result =
                Length.add(length1, length2, LengthUnit.INCH);

        assertEquals(36.0, result.getValue());
    }

    @Test
    void testAddition_ExplicitTargetUnit_NullTargetUnit() {

        Length length1 = new Length(1.0, LengthUnit.FEET);
        Length length2 = new Length(12.0, LengthUnit.INCH);

        assertThrows(IllegalArgumentException.class,
                () -> Length.add(length1, length2, null));
    }

    @Test
    void testAddition_ExplicitTargetUnit_LargeToSmallScale() {

        Length length1 = new Length(1000.0, LengthUnit.FEET);
        Length length2 = new Length(500.0, LengthUnit.FEET);

        Length result =
                Length.add(length1, length2, LengthUnit.INCH);

        assertEquals(18000.0, result.getValue());
    }

    @Test
    void testAddition_ExplicitTargetUnit_SmallToLargeScale() {

        Length length1 = new Length(12.0, LengthUnit.INCH);
        Length length2 = new Length(12.0, LengthUnit.INCH);

        Length result =
                Length.add(length1, length2, LengthUnit.YARD);

        assertEquals(0.666667, result.getValue());
    }
}