package com.QuantityMeasurementApp;

import java.util.Scanner;

public class QuantityMeasurementApp {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {

            System.out.println("1. Length");
            System.out.println("2. Weight");
            System.out.println("3. Volume");
            System.out.println("4. Temperature");
            System.out.println("5. Exit");

            int category = sc.nextInt();

            switch (category) {
                case 1:
                    handleLength();
                    break;
                case 2:
                    handleWeight();
                    break;
                case 3:
                    handleVolume();
                    break;
                case 4 :
                	handleTemperature();
                	break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }


	private static void handleLength() {

        System.out.println("\nSelect Operation");
        System.out.println("1 Conversion");
        System.out.println("2 Addition");
        System.out.println("3 Subtraction");
        System.out.println("4 Division");

        int op = sc.nextInt();

        System.out.println("Enter first value:");
        double v1 = sc.nextDouble();

        System.out.println("Select unit: 1 FEET 2 INCHES 3 YARDS 4 CENTIMETERS");
        LengthUnit u1 = getLengthUnit(sc.nextInt());

        Quantity<LengthUnit> q1 = new Quantity<>(v1, u1);

        if (op == 1) {

            System.out.println("Convert to unit: 1 FEET 2 INCHES 3 YARDS 4 CENTIMETERS");
            LengthUnit target = getLengthUnit(sc.nextInt());

            System.out.println("Result: " + q1.convertTo(target));
        } else {

            System.out.println("Enter second value:");
            double v2 = sc.nextDouble();

            System.out.println("Select second unit: 1 FEET 2 INCHES 3 YARDS 4 CENTIMETERS");
            LengthUnit u2 = getLengthUnit(sc.nextInt());

            Quantity<LengthUnit> q2 = new Quantity<>(v2, u2);

            switch (op) {
                case 2:
                    System.out.println("Result: " + q1.add(q2));
                    break;
                case 3:
                    System.out.println("Result: " + q1.subtract(q2));
                    break;
                case 4:
                    System.out.println("Result: " + q1.divide(q2));
                    break;
            }
        }
    }



    private static void handleWeight() {

        System.out.println("\nSelect Operation");
        System.out.println("1 Conversion");
        System.out.println("2 Addition");
        System.out.println("3 Subtraction");
        System.out.println("4 Division");

        int op = sc.nextInt();

        System.out.println("Enter first value:");
        double v1 = sc.nextDouble();

        System.out.println("Select unit: 1 KG 2 GRAM 3 POUND");
        WeightUnit u1 = getWeightUnit(sc.nextInt());

        Quantity<WeightUnit> q1 = new Quantity<>(v1, u1);

        if (op == 1) {

            System.out.println("Convert to unit: 1 KG 2 GRAM 3 POUND");
            WeightUnit target = getWeightUnit(sc.nextInt());

            System.out.println("Result: " + q1.convertTo(target));
        } else {

            System.out.println("Enter second value:");
            double v2 = sc.nextDouble();

            System.out.println("Select second unit: 1 KG 2 GRAM 3 POUND");
            WeightUnit u2 = getWeightUnit(sc.nextInt());

            Quantity<WeightUnit> q2 = new Quantity<>(v2, u2);

            switch (op) {
                case 2:
                    System.out.println("Result: " + q1.add(q2));
                    break;
                case 3:
                    System.out.println("Result: " + q1.subtract(q2));
                    break;
                case 4:
                    System.out.println("Result: " + q1.divide(q2));
                    break;
            }
        }
    }


    private static void handleVolume() {

        System.out.println("\nSelect Operation");
        System.out.println("1 Conversion");
        System.out.println("2 Addition");
        System.out.println("3 Subtraction");
        System.out.println("4 Division");

        int op = sc.nextInt();

        System.out.println("Enter first value:");
        double v1 = sc.nextDouble();

        System.out.println("Select unit: 1 LITRE 2 MILLILITRE 3 GALLON");
        VolumeUnit u1 = getVolumeUnit(sc.nextInt());

        Quantity<VolumeUnit> q1 = new Quantity<>(v1, u1);

        if (op == 1) {

            System.out.println("Convert to unit: 1 LITRE 2 MILLILITRE 3 GALLON");
            VolumeUnit target = getVolumeUnit(sc.nextInt());

            System.out.println("Result: " + q1.convertTo(target));
        } else {

            System.out.println("Enter second value:");
            double v2 = sc.nextDouble();

            System.out.println("Select second unit: 1 LITRE 2 MILLILITRE 3 GALLON");
            VolumeUnit u2 = getVolumeUnit(sc.nextInt());

            Quantity<VolumeUnit> q2 = new Quantity<>(v2, u2);

            switch (op) {
                case 2:
                    System.out.println("Result: " + q1.add(q2));
                    break;
                case 3:
                    System.out.println("Result: " + q1.subtract(q2));
                    break;
                case 4:
                    System.out.println("Result: " + q1.divide(q2));
                    break;
            }
        }
    }

    private static void handleTemperature() {

        System.out.println("\nSelect Operation");
        System.out.println("1 Conversion");
        System.out.println("2 Addition");
        System.out.println("3 Subtraction");
        System.out.println("4 Division");

        int op = sc.nextInt();

        System.out.println("Enter first value:");
        double v1 = sc.nextDouble();

        System.out.println("Select unit: 1 CELSIUS 2 FAHRENHEIT 3 KELVIN");
        TemperatureUnit u1 = getTemperatureUnit(sc.nextInt());

        Quantity<TemperatureUnit> q1 = new Quantity<>(v1, u1);

        if (op == 1) {

            System.out.println("Convert to unit: 1 CELSIUS 2 FAHRENHEIT 3 KELVIN");
            TemperatureUnit target = getTemperatureUnit(sc.nextInt());

            System.out.println("Result: " + q1.convertTo(target));

        } else {

            System.out.println("Enter second value:");
            double v2 = sc.nextDouble();

            System.out.println("Select second unit: 1 CELSIUS 2 FAHRENHEIT 3 KELVIN");
            TemperatureUnit u2 = getTemperatureUnit(sc.nextInt());

            Quantity<TemperatureUnit> q2 = new Quantity<>(v2, u2);

            switch (op) {
                case 2:
                    System.out.println("Result: " + q1.add(q2));
                    break;

                case 3:
                    System.out.println("Result: " + q1.subtract(q2));
                    break;

                case 4:
                    System.out.println("Result: " + q1.divide(q2));
                    break;
            }
        }
    }


    private static LengthUnit getLengthUnit(int choice) {
        switch (choice) {
            case 1: return LengthUnit.FEET;
            case 2: return LengthUnit.INCH;
            case 3: return LengthUnit.YARDS;
            case 4: return LengthUnit.CENTIMETER;
            default: throw new IllegalArgumentException("Invalid unit");
        }
    }

    private static WeightUnit getWeightUnit(int choice) {
        switch (choice) {
            case 1: return WeightUnit.KILOGRAM;
            case 2: return WeightUnit.GRAM;
            case 3: return WeightUnit.POUND;
            default: throw new IllegalArgumentException("Invalid unit");
        }
    }

    private static VolumeUnit getVolumeUnit(int choice) {
        switch (choice) {
            case 1: return VolumeUnit.LITRE;
            case 2: return VolumeUnit.MILLILITRE;
            case 3: return VolumeUnit.GALLON;
            default: throw new IllegalArgumentException("Invalid unit");
        }
    }
    
    private static TemperatureUnit getTemperatureUnit(int choice) {
        switch (choice) {
            case 1: return TemperatureUnit.CELSIUS;
            case 2: return TemperatureUnit.FAHRENHEIT;
            case 3: return TemperatureUnit.KELVIN;
            default: throw new IllegalArgumentException("Invalid unit");
        }
    }
}