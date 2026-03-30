package com.app.QuantityMeasurementApp.Controller;

import com.app.QuantityMeasurementApp.DTO.*;
import com.app.QuantityMeasurementApp.DTO.request.ArithmeticRequestDTO;
import com.app.QuantityMeasurementApp.DTO.request.CompareRequestDTO;
import com.app.QuantityMeasurementApp.DTO.request.ConvertRequestDTO;
import com.app.QuantityMeasurementApp.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/measurements")
@Tag(name = "Quantity Measurements", description = "REST API for quantity measurement operations")
public class QuantityMeasurementController {

    private static final Logger logger =
            LoggerFactory.getLogger(QuantityMeasurementController.class);

    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
    }

    // ── POST operations ──────────────-----------------------------

    @PostMapping("/compare")
    @Operation(summary = "Compare two quantities",
               description = "Returns true/false in the resultString field.")
    public ResponseEntity<QuantityMeasurementDTO> compare(
            @Valid @RequestBody CompareRequestDTO request) {
        logger.info("POST /compare");
        return ResponseEntity.ok(
                service.compare(request.getThisQuantity(), request.getThatQuantity()));
    }

    @PostMapping("/convert")
    @Operation(summary = "Convert a quantity to a different unit")
    public ResponseEntity<QuantityMeasurementDTO> convert(
            @Valid @RequestBody ConvertRequestDTO request) {
        logger.info("POST /convert");
        return ResponseEntity.ok(
                service.convert(request.getThisQuantity(), request.getTargetUnit()));
    }

    @PostMapping("/add")
    @Operation(summary = "Add two quantities")
    public ResponseEntity<QuantityMeasurementDTO> add(
            @Valid @RequestBody ArithmeticRequestDTO request) {
        logger.info("POST /add");
        QuantityMeasurementDTO result = request.getTargetUnit() != null
                ? service.add(request.getThisQuantity(), request.getThatQuantity(), request.getTargetUnit())
                : service.add(request.getThisQuantity(), request.getThatQuantity());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/subtract")
    @Operation(summary = "Subtract two quantities")
    public ResponseEntity<QuantityMeasurementDTO> subtract(
            @Valid @RequestBody ArithmeticRequestDTO request) {
        logger.info("POST /subtract");
        QuantityMeasurementDTO result = request.getTargetUnit() != null
                ? service.subtract(request.getThisQuantity(), request.getThatQuantity(), request.getTargetUnit())
                : service.subtract(request.getThisQuantity(), request.getThatQuantity());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/divide")
    @Operation(summary = "Divide two quantities")
    public ResponseEntity<QuantityMeasurementDTO> divide(
            @Valid @RequestBody ArithmeticRequestDTO request) {
        logger.info("POST /divide");
        return ResponseEntity.ok(
                service.divide(request.getThisQuantity(), request.getThatQuantity()));
    }

    // ── GET history ──────────────────────────────────────────────

    @GetMapping("/history")
    @Operation(summary = "Get all measurement history")
    public ResponseEntity<List<QuantityMeasurementDTO>> getHistory() {
        return ResponseEntity.ok(service.getAllMeasurements());
    }

    @GetMapping("/history/{operation}")
    @Operation(summary = "Get history by operation type",
               description = "Operation values: COMPARE, CONVERT, ADD, SUBTRACT, DIVIDE")
    public ResponseEntity<List<QuantityMeasurementDTO>> getByOperation(
            @Parameter(description = "Operation type e.g. COMPARE, ADD")
            @PathVariable String operation) {
        return ResponseEntity.ok(service.getMeasurementsByOperation(operation.toUpperCase()));
    }

    @GetMapping("/history/type/{measurementType}")
    @Operation(summary = "Get history by measurement type",
               description = "Type values: LengthUnit, WeightUnit, VolumeUnit, TemperatureUnit")
    public ResponseEntity<List<QuantityMeasurementDTO>> getByType(
            @Parameter(description = "Measurement type e.g. LengthUnit, WeightUnit")
            @PathVariable String measurementType) {
        return ResponseEntity.ok(service.getMeasurementsByType(measurementType));
    }

    @GetMapping("/history/errored")
    @Operation(summary = "Get all error records")
    public ResponseEntity<List<QuantityMeasurementDTO>> getErrorHistory() {
        return ResponseEntity.ok(service.getErrorHistory());
    }

    @GetMapping("/count/{operation}")
    @Operation(summary = "Count successful operations by type")
    public ResponseEntity<Map<String, Object>> getOperationCount(
            @Parameter(description = "Operation type e.g. COMPARE, ADD")
            @PathVariable String operation) {
        long count = service.getOperationCount(operation);
        return ResponseEntity.ok(Map.of(
                "operation", operation.toUpperCase(),
                "count",     count));
    }
}