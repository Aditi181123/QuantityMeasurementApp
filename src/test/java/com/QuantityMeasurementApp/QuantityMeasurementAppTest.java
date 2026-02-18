package com.QuantityMeasurementApp;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class QuantityMeasurementAppTest {
	/*
	 * Test for Feet Measurement Quantity
	 */
	
	@Test
    public void Test_FeetEquality_SameValue(){
    	QuantityMeasurementApp.Feet f1 = new QuantityMeasurementApp.Feet(1.0);
        QuantityMeasurementApp.Feet f2 = new QuantityMeasurementApp.Feet(1.0);
        assertTrue(f1.equals(f2));
    }
    @Test
    public void Test_FeetEquality_DiffValue() {
    	QuantityMeasurementApp.Feet f1 = new QuantityMeasurementApp.Feet(1.0);
        QuantityMeasurementApp.Feet f2 = new QuantityMeasurementApp.Feet(2.0);

        assertFalse(f1.equals(f2));
    }
    @Test
    public void Test_FeetEquality_NullComp() {
    	QuantityMeasurementApp.Feet f1 = new QuantityMeasurementApp.Feet(1.0);
        assertFalse(f1.equals(null));
    }
    @Test
    public void Test_FeetEquality_DiffClass() {
    	QuantityMeasurementApp.Feet f1 = new QuantityMeasurementApp.Feet(1.0);
        assertFalse(f1.equals("1.0"));
    }
    @Test 
    public void Test_FeetEquality_SameRefrence() {
    	QuantityMeasurementApp.Feet f1 = new QuantityMeasurementApp.Feet(1.0);
        assertTrue(f1.equals(f1));
    }
}
