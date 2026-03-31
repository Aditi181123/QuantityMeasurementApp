package com.app.QuantityMeasurementApp.controller;

import com.app.QuantityMeasurementApp.DTO.QuantityInputDTO;
import com.app.QuantityMeasurementApp.DTO.QuantityMeasurementDTO;
import com.app.QuantityMeasurementApp.service.IQuantityMeasurementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/quantities",
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
@Tag(name = "Quantity Measurements", description = "REST API for quantity measurement operations")
public class QuantityMeasurementController {

    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
    }

    // 🔥 API OVERVIEW

    @GetMapping
    @Operation(summary = "Get quantity API overview")
    public ResponseEntity<Map<String, Object>> apiOverview() {

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("message", "Quantity Measurement API is running");

        response.put("postEndpoints", List.of(
                "/api/v1/quantities/compare",
                "/api/v1/quantities/convert",
                "/api/v1/quantities/add",
                "/api/v1/quantities/subtract",
                "/api/v1/quantities/multiply",
                "/api/v1/quantities/divide"
        ));

        response.put("getEndpoints", List.of(
                "/api/v1/quantities/history/all",
                "/api/v1/quantities/history/operation/{operation}",
                "/api/v1/quantities/history/type/{measurementType}",
                "/api/v1/quantities/history/errored",
                "/api/v1/quantities/count/{operation}"
        ));

        response.put("swagger", "/swagger-ui.html");

        return ResponseEntity.ok(response);
    }

    //  OPERATIONS

    @PostMapping(value = "/compare", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Compare two quantities")
    public ResponseEntity<QuantityMeasurementDTO> compareQuantities(
            @Valid @RequestBody QuantityInputDTO input) {

        return ResponseEntity.ok(
                service.compareQuantities(input.getThisQuantityDTO(), input.getThatQuantityDTO())
        );
    }

    @PostMapping(value = "/convert", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Convert quantity to target unit")
    public ResponseEntity<QuantityMeasurementDTO> convertQuantity(
            @Valid @RequestBody QuantityInputDTO input) {

        return ResponseEntity.ok(
                service.convertQuantity(input.getThisQuantityDTO(), input.getThatQuantityDTO())
        );
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add two quantities")
    public ResponseEntity<QuantityMeasurementDTO> addQuantities(
            @Valid @RequestBody QuantityInputDTO input) {

        return ResponseEntity.ok(
                service.addQuantities(input.getThisQuantityDTO(), input.getThatQuantityDTO())
        );
    }

    @PostMapping(value = "/subtract", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Subtract two quantities")
    public ResponseEntity<QuantityMeasurementDTO> subtractQuantities(
            @Valid @RequestBody QuantityInputDTO input) {

        return ResponseEntity.ok(
                service.subtractQuantities(input.getThisQuantityDTO(), input.getThatQuantityDTO())
        );
    }

    @PostMapping(value = "/multiply", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Multiply two quantities")
    public ResponseEntity<QuantityMeasurementDTO> multiplyQuantities(
            @Valid @RequestBody QuantityInputDTO input) {

        return ResponseEntity.ok(
                service.multiplyQuantities(input.getThisQuantityDTO(), input.getThatQuantityDTO())
        );
    }

    @PostMapping(value = "/divide", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Divide two quantities")
    public ResponseEntity<QuantityMeasurementDTO> divideQuantities(
            @Valid @RequestBody QuantityInputDTO input) {

        return ResponseEntity.ok(
                service.divideQuantities(input.getThisQuantityDTO(), input.getThatQuantityDTO())
        );
    }

    //  HISTORY APIs (USER-SPECIFIC via JWT)

    @GetMapping("/history/all")
    @Operation(summary = "Get all history of logged-in user")
    public ResponseEntity<List<QuantityMeasurementDTO>> getAllHistory() {
        return ResponseEntity.ok(service.getAllUserHistory());
    }

    @GetMapping("/history/operation/{operation}")
    @Operation(summary = "Get operation history of logged-in user")
    public ResponseEntity<List<QuantityMeasurementDTO>> getOperationHistory(
            @PathVariable String operation) {

        return ResponseEntity.ok(service.getUserOperationHistory(operation));
    }

    @GetMapping("/history/type/{measurementType}")
    @Operation(summary = "Get history by measurement type of logged-in user")
    public ResponseEntity<List<QuantityMeasurementDTO>> getMeasurementHistory(
            @PathVariable String measurementType) {

        return ResponseEntity.ok(service.getUserMeasurementHistory(measurementType));
    }

    @GetMapping("/history/errored")
    @Operation(summary = "Get error history of logged-in user")
    public ResponseEntity<List<QuantityMeasurementDTO>> getErroredHistory() {

        return ResponseEntity.ok(service.getUserErroredHistory());
    }
    //  COUNT

    @GetMapping("/count/{operation}")
    @Operation(summary = "Get successful operation count")
    public ResponseEntity<Map<String, Object>> getOperationCount(
            @PathVariable String operation) {

        return ResponseEntity.ok(
                Map.of(
                        "operation", operation,
                        "count", service.getOperationCount(operation)
                )
        );
    }
}