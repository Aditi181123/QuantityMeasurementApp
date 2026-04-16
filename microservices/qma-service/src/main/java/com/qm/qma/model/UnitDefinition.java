package com.qm.qma.model;
public enum UnitDefinition {
   
    FEET(MeasurementType.LENGTH, 12.0, "ft"),
    INCH(MeasurementType.LENGTH, 1.0, "in"),
    YARD(MeasurementType.LENGTH, 36.0, "yd"),
    CENTIMETER(MeasurementType.LENGTH, 0.4, "cm"),
    METER(MeasurementType.LENGTH, 36.0, "m"),

 
    GRAM(MeasurementType.WEIGHT, 1.0, "g"),
    KILOGRAM(MeasurementType.WEIGHT, 1000.0, "kg"),
 
    LITRE(MeasurementType.VOLUME, 1.0, "L"),
    MILLILITRE(MeasurementType.VOLUME, 0.001, "mL"),
    GALLON(MeasurementType.VOLUME, 3.78, "gal"),

   
    FAHRENHEIT(MeasurementType.TEMPERATURE, 1.0, "°F"),
    CELSIUS(MeasurementType.TEMPERATURE, 1.0, "°C");

     
    private final MeasurementType type; 
    private final double baseUnitValue;
     
    private final String symbol;
 
    UnitDefinition(MeasurementType type, double baseUnitValue, String symbol) {
        this.type = type;
        this.baseUnitValue = baseUnitValue;
        this.symbol = symbol;
    }
 
    public MeasurementType getType() { return type; }
    public double getBaseUnitValue() { return baseUnitValue; }
    public String getSymbol() { return symbol; }

   
    public static UnitDefinition fromString(String unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        String upperUnit = unit.toUpperCase().trim();
        try {
            return UnitDefinition.valueOf(upperUnit);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid unit: " + unit);
        }
    }

  
    public double toBaseUnit(double value) {
        if (this.type == MeasurementType.TEMPERATURE) {
            return convertTemperatureToBase(value);
        }
        // Standard conversion: multiply by base value
        return value * this.baseUnitValue;
    }

   
    private double convertTemperatureToBase(double value) {
        switch (this) {
            case FAHRENHEIT:
                return (value - 32) * 5.0 / 9.0;  // °F → °C
            case CELSIUS:
                return value;  // Already in base
            default:
                return value;  // Should not reach here
        }
    }
 
    public static double fromBaseToUnit(double baseValue, UnitDefinition targetUnit) {
        if (targetUnit.type == MeasurementType.TEMPERATURE) {
            return convertFromBaseToTemperature(baseValue, targetUnit);
        }
        // Standard conversion: divide by base value
        return baseValue / targetUnit.baseUnitValue;
    }
 
    private static double convertFromBaseToTemperature(double baseValue, UnitDefinition targetUnit) {
        switch (targetUnit) {
            case FAHRENHEIT:
                return baseValue * 9.0 / 5.0 + 32;  // °C → °F
            case CELSIUS:
                return baseValue;  // Already in base
            default:
                return baseValue;  // Should not reach here
        }
    }
}
