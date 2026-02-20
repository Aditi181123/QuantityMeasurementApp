package com.QuantityMeasurementApp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {
 

    @Test
    public void testEquality_YardToYard_SameValue() {
        Length yard1 = new Length(1.0, Length.LengthUnit.YARDS);
        Length yard2 = new Length(1.0, Length.LengthUnit.YARDS);
        assertEquals(yard1, yard2);
    }

    @Test
    public void testEquality_YardToYard_DifferentValue() {
        Length yard1 = new Length(1.0, Length.LengthUnit.YARDS);
        Length yard2 = new Length(2.0, Length.LengthUnit.YARDS);
        assertNotEquals(yard1, yard2);
    }
 

    @Test
    public void testEquality_YardToFeet_EquivalentValue() {
        Length yard = new Length(1.0, Length.LengthUnit.YARDS);
        Length feet = new Length(3.0, Length.LengthUnit.FEET);
        assertEquals(yard, feet);
    }

    @Test
    public void testEquality_FeetToYard_EquivalentValue() {
        Length feet = new Length(3.0, Length.LengthUnit.FEET);
        Length yard = new Length(1.0, Length.LengthUnit.YARDS);
        assertEquals(feet, yard);
    }

    @Test
    public void testEquality_YardToFeet_NonEquivalentValue() {
        Length yard = new Length(1.0, Length.LengthUnit.YARDS);
        Length feet = new Length(2.0, Length.LengthUnit.FEET);
        assertNotEquals(yard, feet);
    }
 

    @Test
    public void testEquality_YardToInches_EquivalentValue() {
        Length yard = new Length(1.0, Length.LengthUnit.YARDS);
        Length inches = new Length(36.0, Length.LengthUnit.INCHES);
        assertEquals(yard, inches);
    }

    @Test
    public void testEquality_InchesToYard_EquivalentValue() {
        Length inches = new Length(36.0, Length.LengthUnit.INCHES);
        Length yard = new Length(1.0, Length.LengthUnit.YARDS);
        assertEquals(inches, yard);
    }
 

    @Test
    public void testEquality_centimetersToInches_EquivalentValue() {
        Length cm = new Length(1.0, Length.LengthUnit.CENTIMETERS);
        Length inches = new Length(0.393701, Length.LengthUnit.INCHES);
        assertEquals(cm, inches);
    }

    @Test
    public void testEquality_centimetersToFeet_NonEquivalentValue() {
        Length cm = new Length(1.0, Length.LengthUnit.CENTIMETERS);
        Length feet = new Length(1.0, Length.LengthUnit.FEET);
        assertNotEquals(cm, feet);
    }
 

    @Test
    public void testEquality_MultiUnit_TransitiveProperty() {
        Length yard = new Length(1.0, Length.LengthUnit.YARDS);
        Length feet = new Length(3.0, Length.LengthUnit.FEET);
        Length inches = new Length(36.0, Length.LengthUnit.INCHES);

        assertEquals(yard, feet);
        assertEquals(feet, inches);
        assertEquals(yard, inches); 
    }
 

    @Test
    public void testEquality_YardWithNullUnit() {
        assertThrows(NullPointerException.class, () -> {
            new Length(1.0, null);
        });
    }

    @Test
    public void testEquality_CentimetersWithNullUnit() {
        assertThrows(NullPointerException.class, () -> {
            new Length(1.0, null);
        });
    }
 

    @Test
    public void testEquality_YardSameReference() {
        Length yard = new Length(1.0, Length.LengthUnit.YARDS);
        assertEquals(yard, yard);
    }

    @Test
    public void testEquality_CentimetersSameReference() {
        Length cm = new Length(2.0, Length.LengthUnit.CENTIMETERS);
        assertEquals(cm, cm);
    }
 

    @Test
    public void testEquality_YardNullComparison() {
        Length yard = new Length(1.0, Length.LengthUnit.YARDS);
        assertNotEquals(yard, null);
    }

    @Test
    public void testEquality_CentimetersNullComparison() {
        Length cm = new Length(1.0, Length.LengthUnit.CENTIMETERS);
        assertNotEquals(cm, null);
    }
 

    @Test
    public void testEquality_AllUnits_ComplexScenario() {
        Length yard = new Length(2.0, Length.LengthUnit.YARDS);
        Length feet = new Length(6.0, Length.LengthUnit.FEET);
        Length inches = new Length(72.0, Length.LengthUnit.INCHES);

        assertEquals(yard, feet);
        assertEquals(feet, inches);
        assertEquals(yard, inches);
    }
}