package com.QuantityMeasurementApp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    private static final double EPSILON = 0.001;
 
    @Test
    public void testAddition_ExplicitTargetUnit_Feet() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(12.0, Length.LengthUnit.INCHES);

        Length result = l1.add(l2, Length.LengthUnit.FEET);

        assertEquals(2.0, result.getValue(), EPSILON);
        assertEquals(Length.LengthUnit.FEET, result.getUnit());
    }
 
    @Test
    public void testAddition_ExplicitTargetUnit_Inches() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(12.0, Length.LengthUnit.INCHES);

        Length result = l1.add(l2, Length.LengthUnit.INCHES);

        assertEquals(24.0, result.getValue(), EPSILON);
        assertEquals(Length.LengthUnit.INCHES, result.getUnit());
    }
 
    @Test
    public void testAddition_ExplicitTargetUnit_Yards() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(12.0, Length.LengthUnit.INCHES);

        Length result = l1.add(l2, Length.LengthUnit.YARDS);

        assertEquals(0.6667, result.getValue(), 0.01);
        assertEquals(Length.LengthUnit.YARDS, result.getUnit());
    }
 
    @Test
    public void testAddition_ExplicitTargetUnit_Centimeters() {
        Length l1 = new Length(1.0, Length.LengthUnit.INCHES);
        Length l2 = new Length(1.0, Length.LengthUnit.INCHES);

        Length result = l1.add(l2, Length.LengthUnit.CENTIMETERS);

        assertEquals(5.08, result.getValue(), 0.01);
        assertEquals(Length.LengthUnit.CENTIMETERS, result.getUnit());
    }
 
    @Test
    public void testAddition_TargetUnit_SameAsFirstOperand() {
        Length l1 = new Length(2.0, Length.LengthUnit.YARDS);
        Length l2 = new Length(3.0, Length.LengthUnit.FEET);

        Length result = l1.add(l2, Length.LengthUnit.YARDS);

        assertEquals(3.0, result.getValue(), EPSILON);
    }
 
    @Test
    public void testAddition_TargetUnit_SameAsSecondOperand() {
        Length l1 = new Length(2.0, Length.LengthUnit.YARDS);
        Length l2 = new Length(3.0, Length.LengthUnit.FEET);

        Length result = l1.add(l2, Length.LengthUnit.FEET);

        assertEquals(9.0, result.getValue(), EPSILON);
    }
 
    @Test
    public void testAddition_ExplicitTargetUnit_Commutativity() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(12.0, Length.LengthUnit.INCHES);

        Length r1 = l1.add(l2, Length.LengthUnit.YARDS);
        Length r2 = l2.add(l1, Length.LengthUnit.YARDS);

        assertEquals(r1.getValue(), r2.getValue(), EPSILON);
    }
 
    @Test
    public void testAddition_ExplicitTargetUnit_WithZero() {
        Length l1 = new Length(5.0, Length.LengthUnit.FEET);
        Length l2 = new Length(0.0, Length.LengthUnit.INCHES);

        Length result = l1.add(l2, Length.LengthUnit.YARDS);

        assertEquals(1.6667, result.getValue(), 0.01);
    }
 
    @Test
    public void testAddition_ExplicitTargetUnit_NegativeValues() {
        Length l1 = new Length(5.0, Length.LengthUnit.FEET);
        Length l2 = new Length(-2.0, Length.LengthUnit.FEET);

        Length result = l1.add(l2, Length.LengthUnit.INCHES);

        assertEquals(36.0, result.getValue(), EPSILON);
    }
 
    @Test
    public void testAddition_ExplicitTargetUnit_NullTargetUnit() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(12.0, Length.LengthUnit.INCHES);

        assertThrows(IllegalArgumentException.class,
                () -> l1.add(l2, null));
    }
 
    @Test
    public void testAddition_ExplicitTargetUnit_LargeToSmallScale() {
        Length l1 = new Length(1000.0, Length.LengthUnit.FEET);
        Length l2 = new Length(500.0, Length.LengthUnit.FEET);

        Length result = l1.add(l2, Length.LengthUnit.INCHES);

        assertEquals(18000.0, result.getValue(), EPSILON);
    }
 
    @Test
    public void testAddition_ExplicitTargetUnit_SmallToLargeScale() {
        Length l1 = new Length(12.0, Length.LengthUnit.INCHES);
        Length l2 = new Length(12.0, Length.LengthUnit.INCHES);

        Length result = l1.add(l2, Length.LengthUnit.YARDS);

        assertEquals(0.6667, result.getValue(), 0.01);
    }
}