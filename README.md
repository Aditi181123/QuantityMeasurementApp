# Quantity Measurement Application

A Java-based application that demonstrates progressively advanced object-oriented design patterns through a series of use cases covering measurement equality, unit conversion, and arithmetic operations across multiple measurement categories (length, weight, volume).

---

## Table of Contents

- [Overview](#overview)
- [Use Cases](#use-cases)
- [Key Design Principles](#key-design-principles)
- [Supported Units](#supported-units)
- [Testing](#testing)
- [Project Structure](#project-structure)

---

## Overview

This project is structured as an incremental series of use cases, each building on the previous to introduce new concepts, refactor existing design, and extend functionality. It evolves from a simple equality check for feet measurements all the way to a fully generic, multi-category measurement system following SOLID principles.

---

## Use Cases

### UC1 – Feet Measurement Equality
Introduces the `Feet` inner class with a proper `equals()` override using `Double.compare()` for floating-point safety.

**Concepts:** Object equality, floating-point comparison, null checking, type safety.

---

### UC2 – Feet and Inches Measurement Equality
Extends UC1 by adding a separate `Inches` class. Highlights the DRY violation of maintaining two nearly identical classes.

**Concepts:** Same as UC1, applied to a second unit type.

---

### UC3 – Generic Quantity Class (DRY Principle)
Refactors `Feet` and `Inches` into a single `QuantityLength` class backed by a `LengthUnit` enum with conversion factors.

**Concepts:** DRY principle, enum usage, unit abstraction, cross-unit equality (1 foot = 12 inches).

---

### UC4 – Extended Unit Support (Yards & Centimeters)
Adds `YARDS` and `CENTIMETERS` to the `LengthUnit` enum. No changes to `QuantityLength` are needed, validating the scalable design.

**Conversions:**
- 1 yard = 3 feet = 36 inches
- 1 cm = 0.393701 inches

---

### UC5 – Unit-to-Unit Conversion
Exposes a `convert(value, sourceUnit, targetUnit)` method. Normalizes values through the base unit (feet) before converting to the target unit.

**Concepts:** Base unit normalization, bidirectional conversion, method design, API usability.

---

### UC6 – Addition of Two Length Units
Adds two `QuantityLength` objects of potentially different units. The result is expressed in the unit of the first operand.

**Concepts:** Arithmetic on value objects, immutability, unit conversion reuse, commutativity.

---

### UC7 – Addition with Explicit Target Unit
Extends UC6 by allowing the caller to specify any supported unit for the result.

**Concepts:** Method overloading, explicit parameter passing, functional approach.

---

### UC8 – Standalone LengthUnit Enum with Conversion Responsibility
Extracts `LengthUnit` from inside `QuantityLength` into a standalone top-level class. Assigns conversion responsibility (`convertToBaseUnit`, `convertFromBaseUnit`) to the enum itself.

**Concepts:** SRP, separation of concerns, dependency inversion, circular dependency elimination.

---

### UC9 – Weight Measurement (Kilogram, Gram, Pound)
Introduces `WeightUnit` and `QuantityWeight`, mirroring the length design. Demonstrates support for a second, independent measurement category.

**Conversions:**
- 1 kg = 1000 g
- 1 lb ≈ 0.453592 kg

**Concepts:** Multi-category support, category type safety, equals/hashCode contract.

---

### UC10 – Generic Quantity Class with IMeasurable Interface
Eliminates the duplication between `QuantityLength` and `QuantityWeight` by introducing:
- `IMeasurable` interface for all unit enums
- Generic `Quantity<U extends IMeasurable>` class replacing all category-specific Quantity classes
- Simplified `QuantityMeasurementApp` with generic demonstration methods

**Concepts:** Generic programming, bounded type parameters, interface-based design, OCP, LSP, composition over inheritance.

---

### UC11 – Volume Measurement (Liter, Milliliter, Gallon)
Introduces a new VolumeUnit enum implementing IMeasurable, extending the system to support volume measurements alongside length and weight.

**Conversions:**
- 1 liter = 1000 milliliters
- 1 gallon ≈ 3.78541 liters

**Concepts:** Multi-category extensibility, interface reuse, scalability without modifying existing logic (OCP).

---

### UC12 – Subtraction and Division of Quantities
Extends arithmetic support by introducing:

- subtract(Quantity<U> other)
- divide(double scalar)

Subtraction works similarly to addition using base unit normalization. Division supports scalar-only operations.

**Concepts:** Arithmetic consistency, immutability, method extension, code reuse.

---

### UC13 – Centralized Arithmetic Logic
Refactors arithmetic operations (add, subtract, convert) into a dedicated service class (e.g., QuantityArithmeticService) instead of handling them inside the Quantity class.

**Concepts:** Single Responsibility Principle (SRP), separation of concerns, cleaner domain model, improved maintainability.

---

### UC14 – Temperature Measurement (Celsius, Fahrenheit, Kelvin)
Introduces TemperatureUnit enum with non-linear conversion logic (involving both scaling and offset).

**Conversions:**
- °F = (°C × 9/5) + 32
- K = °C + 273.15

Unlike other units, conversion is not purely multiplicative.

**Concepts:** Handling non-linear transformations, real-world modeling complexity, abstraction flexibility.

---

### UC15 – N-Tier Architecture Implementation
Refactors the application into a layered architecture:

- Controller Layer – Handles API requests
- Service Layer – Business logic
- Repository Layer – Data persistence
- Model Layer – Core domain classes

**Concepts:** Layered architecture, separation of concerns, scalability, maintainability, enterprise design patterns.

---

### UC16 – Database Integration (Persistence Layer)
Integrates a database using Spring Data JPA to persist measurement operations.

**Introduces QuantityMeasurementEntity with fields such as:**
- value
- unit
- operation type
- result
- timestamp

**Concepts:** ORM (Object Relational Mapping), persistence, entity modeling, database interaction.

---

### UC17 – Spring Boot REST API Integration
Exposes the application functionality via REST APIs.

**Sample Endpoints:**
- POST /convert → Convert between units
- POST /add → Add two quantities
- POST /subtract → Subtract quantities
- GET /history → Fetch stored operations

**Concepts:** RESTful services, API design, Spring Boot framework, controller-service interaction, real-world application deployment.

---

### UC18 – Spring Security with JWT and OAuth2 Authentication
Integrates Spring Security to secure the application using JWT (JSON Web Token) and OAuth2 authentication (e.g., Google/GitHub login).

**Introduces security components such as:**
- SecurityConfig for configuring access rules
- JwtUtil for token generation and validation
- JwtFilter for request interception
- UserDetailsService for loading user-specific data

**Supports authentication flows including:**
- JWT-based login with token generation
- OAuth2-based third-party login

**Secures endpoints like:**
- /api/* → Requires authentication
- /auth/login and /auth/register → Public access

**Concepts:** Authentication vs Authorization, stateless security, JWT token lifecycle, OAuth2 protocol, Spring Security filter chain, secure API design.

---

## Key Design Principles

| Principle | Where Applied |
|---|---|
| **DRY** | UC3 (single Quantity class), UC10 (generic Quantity replaces all category classes) |
| **SRP** | UC8 (unit enum owns conversion), UC10 (each class has one responsibility) |
| **OCP** | UC4, UC11 (new units/categories added without modifying existing code) |
| **LSP** | UC10 (any `IMeasurable` can substitute another in `Quantity<U>`) |
| **Encapsulation** | All UCs (private final fields, controlled access) |
| **Immutability** | All UCs (value objects return new instances on operations) |

---

## Supported Units

### Length
| Unit | Conversion Factor (relative to feet) |
|---|---|
| FEET | 1.0 |
| INCHES | 1/12 ≈ 0.0833 |
| YARDS | 3.0 |
| CENTIMETERS | ~0.0328 |

### Weight
| Unit | Conversion Factor (relative to kg) |
|---|---|
| KILOGRAM | 1.0 |
| GRAM | 0.001 |
| POUND | 0.453592 |

### Volume
| Unit | Conversion Factor (relative to L) |
|---|---|
| LITRE | 1.0 |
| MILLILITRE | 0.001 |
| GALLON | 3.78541 |

### Temperature
| Unit | Conversion Factor (relative to C)|
|---|---|
| Celcius | c|
| Fahernite | (c * 9 / 5) + 32 |
| Kelvin | c + 273.15 |
---

## Testing

Each use case includes comprehensive JUnit test coverage following the **given-when-then** pattern with one assertion per test.

### Test Categories per UC

- Same-value equality
- Different-value inequality
- Cross-unit equivalence (symmetric and transitive)
- Null comparison handling
- Same-reference (reflexive) equality
- Invalid / null unit handling
- Conversion accuracy (round-trip within epsilon)
- Addition (same unit, cross-unit, with explicit target unit)
- Commutativity of addition
- Edge cases: zero, negative, large, small values
- Cross-category incompatibility (from UC9 onward)
---

## Project Structure

```

QuantityMeasurementApp
|
+--main (branch)
|  +- README file
|
+--dev(branch)
|  
+--feature/UC1-FeetEquality
    
+--feature/UC2-IncheEquality
    
+--feature/UC3-GenericLength
   
+--feature/UC4-YardEquality
    
+--feature/UC5-Unit-to-UnitConversion
   
+--feature/UC6-UnitAddition
    
+--feature/UC7-Unit-Specification-Addition
   
+--feature/UC8-Standalone-Conversion
  
+--feature/UC9-WeightMeasurement
  
+--feature/UC10-GenericQuantity
   
+--feature/UC11-VolumeMeasurement
  
+--feature/UC12-UnitSubtractionAndDivision
   
+--feature/UC13-CenterlizedArthmeticLogic

+--feature/UC14-TemperatureMeasurement
  
+--feature/UC15-NTier
   
+--feature/UC16-DatabaseConnection

+--feature/UC17-SpringFrameworkIntegration

```

---

## Adding a New Measurement Category

Thanks to the `IMeasurable` interface and generic `Quantity<T>` class, adding a new category (e.g., temperature, time) requires only:

1. Create a new enum implementing `IMeasurable`
2. Define constants with appropriate conversion factors
3. Implement `convertToBaseUnit()` and `convertFromBaseUnit()`
4. Use `new Quantity<>(value, YourUnit.CONSTANT)` immediately

No changes to `Quantity<T>`, `QuantityMeasurementApp`, or existing tests are needed.