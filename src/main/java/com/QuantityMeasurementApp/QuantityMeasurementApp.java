package com.QuantityMeasurementApp;

import java.util.Scanner;

public class QuantityMeasurementApp {

	static Scanner sc = new Scanner(System.in);

	// Generic Equality Method
	public static void demonstrateLengthEquality(Length l1, Length l2) {
		System.out.println(l1 + "  and  " + l2);
		System.out.println("Equal? => " + l1.equals(l2));
		System.out.println("--------------------------------");
	}

	// UC1: Feet Equality (User Input)
	public static void demonstrateFeetEquality() {
		System.out.println("UC1: Feet Equality Test");

		System.out.print("Enter first value in FEET: ");
		double v1 = sc.nextDouble();

		System.out.print("Enter second value in FEET: ");
		double v2 = sc.nextDouble();

		Length f1 = new Length(v1, Length.LengthUnit.FEET);
		Length f2 = new Length(v2, Length.LengthUnit.FEET);

		demonstrateLengthEquality(f1, f2);
	}

	// UC2: Inch Equality (User Input)
	public static void demonstrateInchEquality() {
		System.out.println("UC2: Inch Equality Test");

		System.out.print("Enter first value in INCH: ");
		double v1 = sc.nextDouble();

		System.out.print("Enter second value in INCH: ");
		double v2 = sc.nextDouble();

		Length i1 = new Length(v1, Length.LengthUnit.INCH);
		Length i2 = new Length(v2, Length.LengthUnit.INCH);

		demonstrateLengthEquality(i1, i2);
	}

	// UC3: Feet ↔ Inch Equality (User Input)
	public static void demonstrateFeetInchesEquality() {
		System.out.println("UC3: Feet and Inch Cross Unit Equality Test");

		System.out.print("Enter value in FEET: ");
		double feet = sc.nextDouble();

		System.out.print("Enter value in INCH: ");
		double inch = sc.nextDouble();

		Length l1 = new Length(feet, Length.LengthUnit.FEET);
		Length l2 = new Length(inch, Length.LengthUnit.INCH);

		demonstrateLengthEquality(l1, l2);
	}

	// MAIN MENU
	public static void main(String[] args) {

		demonstrateFeetEquality();
		demonstrateInchEquality();
		demonstrateFeetInchesEquality();

	}
}