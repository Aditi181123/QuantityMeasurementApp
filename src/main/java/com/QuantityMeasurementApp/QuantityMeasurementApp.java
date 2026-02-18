package com.QuantityMeasurementApp;

import java.util.Scanner;

public class QuantityMeasurementApp {

    /*
     * UC1: Feet Measurement Equality
     */
    public static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Feet feet = (Feet) obj;
            return Double.compare(this.value, feet.value) == 0;
        }
    }
 // UC1 method
    public static void demonstrateFeetEquality() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter first value in feet: ");
        double value1 = sc.nextDouble();

        System.out.print("Enter second value in feet: ");
        double value2 = sc.nextDouble();

        System.out.print("Enter third value in feet: ");
        double value3 = sc.nextDouble();

        Feet f1 = new Feet(value1);
        Feet f2 = new Feet(value2);
        Feet f3 = new Feet(value3);

        System.out.println("Same value ? " + f1.equals(f2));
        System.out.println("Different value ? " + f1.equals(f3));
        System.out.println("Null Comparison ? " + f1.equals(null));
        System.out.println("Same reference ? " + f1.equals(f1));
    }

    /*
     * UC2: Inches Measurement Equality
     */
    public static class Inches {
        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Inches inch = (Inches) obj;
            return Double.compare(this.value, inch.value) == 0;
        }
    }

    // UC2 method
    public static void demonstrateInchEquality() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter first value in inches: ");
        double v1 = sc.nextDouble();

        System.out.print("Enter second value in inches: ");
        double v2 = sc.nextDouble();

        System.out.print("Enter third value in inches: ");
        double v3 = sc.nextDouble();

        Inches i1 = new Inches(v1);
        Inches i2 = new Inches(v2);
        Inches i3 = new Inches(v3);

        System.out.println("Same value ? " + i1.equals(i2));
        System.out.println("Different value ? " + i1.equals(i3));
        System.out.println("Null Comparison ? " + i1.equals(null));
        System.out.println("Same reference ? " + i1.equals(i1));
    }

    // MAIN METHOD
    public static void main(String[] args) {
        demonstrateFeetEquality();
        demonstrateInchEquality();
    }
}
