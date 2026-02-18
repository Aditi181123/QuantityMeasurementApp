package com.QuantityMeasurementApp;

public class QuantityMeasurementApp {
	/*
	 * Quantity Measurement App - UC1: Feet Measurement Equality
	 */
	public static class Feet{
		private final double value;
		
		public Feet(double value) {
			this.value = value;
		}
		public double getValue() {
			return value;
		}
		
		public boolean equals(Object obj) {
			if(this == obj) {
				return true;
			}
			
			if(obj == null || getClass() != obj.getClass()) {
				return false;
			}
			Feet feet = (Feet) obj;
			
			return Double.compare(this.value, feet.value) == 0;
		}
	}
	
	public static void demonstrateFeetEquality() {
		Feet f1 = new Feet(1.0);
		Feet f2 = new Feet(1.0);
		Feet f3 = new Feet(2.0);

		System.out.println("Same value ? " + f1.equals(f2)); 
		System.out.println("Different value ? " + f1.equals(f3)); 
		System.out.println("Null Comparsion ? " + f1.equals(null)); 
		System.out.println("Same reference ? " + f1.equals(f1)); 
	}
	
	
	public static void main(String[] args) {
		demonstrateFeetEquality();
	}
}
