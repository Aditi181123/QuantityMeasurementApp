package com.app.QuantityMeasurementApp.controller;

import com.app.QuantityMeasurementApp.DTO.QuantityInputDTO;
import com.app.QuantityMeasurementApp.DTO.QuantityMeasurementDTO;
import com.app.QuantityMeasurementApp.service.IQuantityMeasurementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<Map<String, Object>> apiOverview() {

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("message", "Quantity Measurement API is running");

        response.put("postEndpoints", List.of(
                "/compare", "/convert", "/add", "/subtract", "/multiply", "/divide"
        ));

        response.put("historyNote", "Login required for history APIs");

        return ResponseEntity.ok(response);
    }

    // ================= PUBLIC APIs =================

    @PostMapping("/compare")
    public ResponseEntity<QuantityMeasurementDTO> compare(@Valid @RequestBody QuantityInputDTO input) {
        return ResponseEntity.ok(
                service.compareQuantities(input.getThisQuantityDTO(), input.getThatQuantityDTO())
        );
    }

    @PostMapping("/convert")
    public ResponseEntity<QuantityMeasurementDTO> convert(@Valid @RequestBody QuantityInputDTO input) {
        return ResponseEntity.ok(
                service.convertQuantity(input.getThisQuantityDTO(), input.getThatQuantityDTO())
        );
    }

    @PostMapping("/add")
    public ResponseEntity<QuantityMeasurementDTO> add(@Valid @RequestBody QuantityInputDTO input) {
        return ResponseEntity.ok(
                service.addQuantities(input.getThisQuantityDTO(), input.getThatQuantityDTO())
        );
    }

    @PostMapping("/subtract")
    public ResponseEntity<QuantityMeasurementDTO> subtract(@Valid @RequestBody QuantityInputDTO input) {
        return ResponseEntity.ok(
                service.subtractQuantities(input.getThisQuantityDTO(), input.getThatQuantityDTO())
        );
    }

    @PostMapping("/multiply")
    public ResponseEntity<QuantityMeasurementDTO> multiply(@Valid @RequestBody QuantityInputDTO input) {
        return ResponseEntity.ok(
                service.multiplyQuantities(input.getThisQuantityDTO(), input.getThatQuantityDTO())
        );
    }

    @PostMapping("/divide")
    public ResponseEntity<QuantityMeasurementDTO> divide(@Valid @RequestBody QuantityInputDTO input) {
        return ResponseEntity.ok(
                service.divideQuantities(input.getThisQuantityDTO(), input.getThatQuantityDTO())
        );
    }

    // ================= PROTECTED APIs =================

    @GetMapping("/history/all")
    public ResponseEntity<List<QuantityMeasurementDTO>> getAllHistory(Authentication authentication) {
        return ResponseEntity.ok(service.getAllUserHistory(authentication));
    }

    @GetMapping("/history/operation/{operation}")
    public ResponseEntity<List<QuantityMeasurementDTO>> getOperationHistory(
            @PathVariable String operation,
            Authentication authentication) {

        return ResponseEntity.ok(service.getUserOperationHistory(operation, authentication));
    }

    @GetMapping("/history/type/{measurementType}")
    public ResponseEntity<List<QuantityMeasurementDTO>> getMeasurementHistory(
            @PathVariable String measurementType,
            Authentication authentication) {

        return ResponseEntity.ok(service.getUserMeasurementHistory(measurementType, authentication));
    }

    @GetMapping("/history/errored")
    public ResponseEntity<List<QuantityMeasurementDTO>> getErroredHistory(Authentication authentication) {
        return ResponseEntity.ok(service.getUserErroredHistory(authentication));
    }

    @GetMapping("/count/{operation}")
    public ResponseEntity<Map<String, Object>> getCount(@PathVariable String operation) {
        return ResponseEntity.ok(
                Map.of("operation", operation, "count", service.getOperationCount(operation))
        );
    }
}