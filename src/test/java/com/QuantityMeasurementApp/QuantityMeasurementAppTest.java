package com.QuantityMeasurementApp;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

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
    void testDivision_ByZero() {

        assertThrows(ArithmeticException.class,
                () -> new Quantity<>(1.0, VolumeUnit.LITRE)
                        .divide(new Quantity<>(0.0, VolumeUnit.LITRE)));
    }



    @Test
    void testValidation_NullOperand_ConsistentAcrossOperations() {

        Quantity<VolumeUnit> q = new Quantity<>(1.0, VolumeUnit.LITRE);

        assertThrows(IllegalArgumentException.class, () -> q.add(null));
        assertThrows(IllegalArgumentException.class, () -> q.subtract(null));
        assertThrows(IllegalArgumentException.class, () -> q.divide(null));
    }

    @Test
    void testValidation_CrossCategory_ConsistentAcrossOperations() {

        Quantity<VolumeUnit> v = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<WeightUnit> w = new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertThrows(IllegalArgumentException.class, () -> v.add((Quantity) w));
        assertThrows(IllegalArgumentException.class, () -> v.subtract((Quantity) w));
        assertThrows(IllegalArgumentException.class, () -> v.divide((Quantity) w));
    }

    @Test
    void testValidation_FiniteValue_ConsistentAcrossOperations() {

        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(Double.NaN, VolumeUnit.LITRE));

        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(Double.POSITIVE_INFINITY, VolumeUnit.LITRE));
    }

    @Test
    void testValidation_NullTargetUnit_AddSubtractReject() {

        Quantity<VolumeUnit> q1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> q2 = new Quantity<>(1.0, VolumeUnit.LITRE);

        assertThrows(IllegalArgumentException.class, () -> q1.add(q2, null));
        assertThrows(IllegalArgumentException.class, () -> q1.subtract(q2, null));
    }


    @Test
    void testImmutability_AfterAdd_ViaCentralizedHelper() {

        Quantity<VolumeUnit> q1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> q2 = new Quantity<>(1.0, VolumeUnit.LITRE);

        q1.add(q2);

        assertEquals(1.0, q1.getValue(), EPSILON);
        assertEquals(1.0, q2.getValue(), EPSILON);
    }

    @Test
    void testImmutability_AfterSubtract_ViaCentralizedHelper() {

        Quantity<VolumeUnit> q1 = new Quantity<>(2.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> q2 = new Quantity<>(1.0, VolumeUnit.LITRE);

        q1.subtract(q2);

        assertEquals(2.0, q1.getValue(), EPSILON);
    }

    @Test
    void testImmutability_AfterDivide_ViaCentralizedHelper() {

        Quantity<VolumeUnit> q1 = new Quantity<>(4.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> q2 = new Quantity<>(2.0, VolumeUnit.LITRE);

        q1.divide(q2);

        assertEquals(4.0, q1.getValue(), EPSILON);
    }


    @Test
    void testImplicitTargetUnit_AddSubtract() {

        Quantity<VolumeUnit> result =
                new Quantity<>(1.0, VolumeUnit.LITRE)
                        .add(new Quantity<>(500.0, VolumeUnit.MILLILITRE));

        assertEquals(1.5, result.getValue(), EPSILON);
    }

    @Test
    void testExplicitTargetUnit_AddSubtract_Overrides() {

        Quantity<VolumeUnit> result =
                new Quantity<>(1.0, VolumeUnit.LITRE)
                        .add(new Quantity<>(500.0, VolumeUnit.MILLILITRE),
                                VolumeUnit.MILLILITRE);

        assertEquals(1500.0, result.getValue(), EPSILON);
    }



    @Test
    void testArithmetic_Chain_Operations() {

        Quantity<VolumeUnit> q1 = new Quantity<>(2.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> q2 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> q3 = new Quantity<>(500.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> q4 = new Quantity<>(1.0, VolumeUnit.LITRE);

        double result =
                q1.add(q2)
                        .subtract(q3)
                        .divide(q4);

        assertEquals(2.5, result, EPSILON);
    }


    @Test
    void testHelper_PrivateVisibility() throws Exception {

        Method method =
                Quantity.class.getDeclaredMethod("performBaseArithmetic",
                        Quantity.class,
                        Class.forName("QuantityMeasurementApp.com.QuantityMeasurementApp.Quantity$ArithmeticOperation"));

        assertTrue(java.lang.reflect.Modifier.isPrivate(method.getModifiers()));
    }

    @Test
    void testValidation_Helper_PrivateVisibility() throws Exception {

        Method method =
                Quantity.class.getDeclaredMethod("validateArithmeticOperands",
                        Quantity.class,
                        Object.class,
                        boolean.class);

        assertTrue(java.lang.reflect.Modifier.isPrivate(method.getModifiers()));
    }




    @Test
    void testEnumConstant_ADD_CorrectlyAdds() throws Exception {

        Class<?> enumClass =
                Class.forName("QuantityMeasurementApp.com.QuantityMeasurementApp.Quantity$ArithmeticOperation");

        Object addEnum = Enum.valueOf((Class<Enum>) enumClass, "ADD");

        Method compute = enumClass.getDeclaredMethod("compute", double.class, double.class);

        double result = (double) compute.invoke(addEnum, 7.0, 3.0);

        assertEquals(10.0, result, EPSILON);
    }

    @Test
    void testEnumConstant_SUBTRACT_CorrectlySubtracts() throws Exception {

        Class<?> enumClass =
                Class.forName("QuantityMeasurementApp.com.QuantityMeasurementApp.Quantity$ArithmeticOperation");

        Object subEnum = Enum.valueOf((Class<Enum>) enumClass, "SUBTRACT");

        Method compute = enumClass.getDeclaredMethod("compute", double.class, double.class);

        double result = (double) compute.invoke(subEnum, 7.0, 3.0);

        assertEquals(4.0, result, EPSILON);
    }

    @Test
    void testEnumConstant_DIVIDE_CorrectlyDivides() throws Exception {

        Class<?> enumClass =
                Class.forName("QuantityMeasurementApp.com.QuantityMeasurementApp.Quantity$ArithmeticOperation");

        Object divEnum = Enum.valueOf((Class<Enum>) enumClass, "DIVIDE");

        Method compute = enumClass.getDeclaredMethod("compute", double.class, double.class);

        double result = (double) compute.invoke(divEnum, 7.0, 2.0);

        assertEquals(3.5, result, EPSILON);
    }

}