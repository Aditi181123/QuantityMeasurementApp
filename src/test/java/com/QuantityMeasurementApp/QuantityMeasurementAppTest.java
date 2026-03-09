package com.QuantityMeasurementApp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityTest {



    @Test
    void testConstructor_NullUnit() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(1.0, null));
    }

    @Test
    void testConstructor_InvalidValue() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(Double.NaN, LengthUnit.FEET));
    }



    @Test
    void testLengthEquality_FeetToInch() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(12.0, LengthUnit.INCH);

        assertTrue(q1.equals(q2));
    }

    @Test
    void testLengthEquality_DifferentValue() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(2.0, LengthUnit.FEET);

        assertFalse(q1.equals(q2));
    }


    @Test
    void testWeightEquality_KgToGram() {
        Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.GRAM);

        assertTrue(w1.equals(w2));
    }

    @Test
    void testWeightEquality_DifferentValue() {
        Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(2.0, WeightUnit.KILOGRAM);

        assertFalse(w1.equals(w2));
    }



    @Test
    void testLengthConversion_FeetToInch() {
        Quantity<LengthUnit> q = new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> result = q.convertTo(LengthUnit.INCH);

        assertEquals(12.0, result.getValue());
    }

    @Test
    void testLengthConversion_InchToYard() {
        Quantity<LengthUnit> q = new Quantity<>(36.0, LengthUnit.INCH);

        Quantity<LengthUnit> result = q.convertTo(LengthUnit.YARD);

        assertEquals(1.0, result.getValue());
    }



    @Test
    void testWeightConversion_KgToGram() {
        Quantity<WeightUnit> q = new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> result = q.convertTo(WeightUnit.GRAM);

        assertEquals(1000.0, result.getValue());
    }

    @Test
    void testWeightConversion_GramToKg() {
        Quantity<WeightUnit> q = new Quantity<>(1000.0, WeightUnit.GRAM);

        Quantity<WeightUnit> result = q.convertTo(WeightUnit.KILOGRAM);

        assertEquals(1.0, result.getValue());
    }


    @Test
    void testLengthAddition_CrossUnit() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(12.0, LengthUnit.INCH);

        Quantity<LengthUnit> result = q1.add(q2);

        assertEquals(2.0, result.getValue());
    }

    @Test
    void testLengthAddition_ExplicitTarget() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(12.0, LengthUnit.INCH);

        Quantity<LengthUnit> result = q1.add(q2, LengthUnit.INCH);

        assertEquals(24.0, result.getValue());
    }


    @Test
    void testWeightAddition_CrossUnit() {
        Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.GRAM);

        Quantity<WeightUnit> result = w1.add(w2);

        assertEquals(2.0, result.getValue());
    }

    @Test
    void testWeightAddition_ExplicitTarget() {
        Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.GRAM);

        Quantity<WeightUnit> result = w1.add(w2, WeightUnit.GRAM);

        assertEquals(2000.0, result.getValue());
    }



    @Test
    void testCrossCategoryPrevention() {
        Quantity<LengthUnit> length =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<WeightUnit> weight =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertFalse(length.equals(weight));
    }


    @Test
    void testSameReference() {
        Quantity<LengthUnit> q =
                new Quantity<>(5.0, LengthUnit.FEET);

        assertTrue(q.equals(q));
    }
}