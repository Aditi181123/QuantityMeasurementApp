package com.QuantityMeasurementApp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    private static final double EPSILON = 0.0001;



    @Test
    void testEquality_LitreToLitre_SameValue() {
        assertTrue(new Quantity<>(1.0, VolumeUnit.LITRE)
                .equals(new Quantity<>(1.0, VolumeUnit.LITRE)));
    }

    @Test
    void testEquality_LitreToMillilitre_EquivalentValue() {
        assertTrue(new Quantity<>(1.0, VolumeUnit.LITRE)
                .equals(new Quantity<>(1000.0, VolumeUnit.MILLILITRE)));
    }

    @Test
    void testEquality_VolumeVsWeight_Incompatible() {
        assertFalse(new Quantity<>(1.0, VolumeUnit.LITRE)
                .equals(new Quantity<>(1.0, WeightUnit.KILOGRAM)));
    }


    @Test
    void testConversion_LitreToMillilitre() {

        Quantity<VolumeUnit> result =
                new Quantity<>(1.0, VolumeUnit.LITRE)
                        .convertTo(VolumeUnit.MILLILITRE);

        assertEquals(1000.0, result.getValue(), EPSILON);
    }

    @Test
    void testConversion_GallonToLitre() {

        Quantity<VolumeUnit> result =
                new Quantity<>(1.0, VolumeUnit.GALLON)
                        .convertTo(VolumeUnit.LITRE);

        assertEquals(3.78541, result.getValue(), EPSILON);
    }



    @Test
    void testAddition_LitrePlusMillilitre() {

        Quantity<VolumeUnit> result =
                new Quantity<>(1.0, VolumeUnit.LITRE)
                        .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE));

        assertEquals(2.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_MillilitrePlusMillilitre() {

        Quantity<VolumeUnit> result =
                new Quantity<>(500.0, VolumeUnit.MILLILITRE)
                        .add(new Quantity<>(500.0, VolumeUnit.MILLILITRE));

        assertEquals(1000.0, result.getValue(), EPSILON);
    }



    @Test
    void testSubtraction_LitreMinusLitre() {

        Quantity<VolumeUnit> result =
                new Quantity<>(5.0, VolumeUnit.LITRE)
                        .subtract(new Quantity<>(2.0, VolumeUnit.LITRE));

        assertEquals(3.0, result.getValue(), EPSILON);
    }

    @Test
    void testSubtraction_LitreMinusMillilitre() {

        Quantity<VolumeUnit> result =
                new Quantity<>(2.0, VolumeUnit.LITRE)
                        .subtract(new Quantity<>(500.0, VolumeUnit.MILLILITRE));

        assertEquals(1.5, result.getValue(), EPSILON);
    }

    @Test
    void testSubtraction_MillilitreMinusLitre() {

        Quantity<VolumeUnit> result =
                new Quantity<>(2000.0, VolumeUnit.MILLILITRE)
                        .subtract(new Quantity<>(1.0, VolumeUnit.LITRE));

        assertEquals(1000.0, result.getValue(), EPSILON);
    }

    @Test
    void testSubtraction_ResultZero() {

        Quantity<VolumeUnit> result =
                new Quantity<>(1.0, VolumeUnit.LITRE)
                        .subtract(new Quantity<>(1000.0, VolumeUnit.MILLILITRE));

        assertEquals(0.0, result.getValue(), EPSILON);
    }

    @Test
    void testSubtraction_NegativeResult() {

        Quantity<VolumeUnit> result =
                new Quantity<>(1.0, VolumeUnit.LITRE)
                        .subtract(new Quantity<>(2.0, VolumeUnit.LITRE));

        assertEquals(-1.0, result.getValue(), EPSILON);
    }



    @Test
    void testDivision_LitreByLitre() {

        double result =
                new Quantity<>(4.0, VolumeUnit.LITRE)
                        .divide(new Quantity<>(2.0, VolumeUnit.LITRE));

        assertEquals(2.0, result, EPSILON);
    }

    @Test
    void testDivision_LitreByMillilitre() {

        double result =
                new Quantity<>(1.0, VolumeUnit.LITRE)
                        .divide(new Quantity<>(500.0, VolumeUnit.MILLILITRE));

        assertEquals(2.0, result, EPSILON);
    }

    @Test
    void testDivision_MillilitreByLitre() {

        double result =
                new Quantity<>(500.0, VolumeUnit.MILLILITRE)
                        .divide(new Quantity<>(1.0, VolumeUnit.LITRE));

        assertEquals(0.5, result, EPSILON);
    }

    @Test
    void testDivision_BySameQuantity() {

        double result =
                new Quantity<>(1.0, VolumeUnit.LITRE)
                        .divide(new Quantity<>(1.0, VolumeUnit.LITRE));

        assertEquals(1.0, result, EPSILON);
    }

    @Test
    void testDivision_ByZero() {

        assertThrows(ArithmeticException.class,
                () -> new Quantity<>(1.0, VolumeUnit.LITRE)
                        .divide(new Quantity<>(0.0, VolumeUnit.LITRE)));
    }



    @Test
    void testVolumeUnitEnum_LitreConstant() {
        assertEquals(1.0, VolumeUnit.LITRE.getConversionFactor(), EPSILON);
    }

    @Test
    void testVolumeUnitEnum_MillilitreConstant() {
        assertEquals(0.001, VolumeUnit.MILLILITRE.getConversionFactor(), EPSILON);
    }

    @Test
    void testVolumeUnitEnum_GallonConstant() {
        assertEquals(3.78541, VolumeUnit.GALLON.getConversionFactor(), EPSILON);
    }

}