package com.qm.qma.controller;
import com.qm.qma.dto.ConvertRequest;
import com.qm.qma.dto.OperationRequest;
import com.qm.qma.dto.CompareResult;
import com.qm.qma.dto.OperationResult;
import com.qm.qma.model.MeasurementHistory;
import com.qm.qma.service.QmaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/qma")
public class QmaController {

    private final QmaService qmaService;

    public QmaController(QmaService qmaService) {
        this.qmaService = qmaService;
    }

    @PostMapping("/convert")
    public ResponseEntity<?> convert(@Valid @RequestBody ConvertRequest request) {
        try {
            Double result = qmaService.convert(request);
            return ResponseEntity.ok(Map.of(
                "result", result,
                "unit", request.getTo()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

  
    @PostMapping("/compare")
    public ResponseEntity<?> compare(@Valid @RequestBody OperationRequest request) {
        try {
            CompareResult result = qmaService.compare(request);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

  
    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody OperationRequest request,
                                @RequestHeader(value = "X-User-Id", required = false) String userId) {
        try {
            OperationResult result = qmaService.add(request, userId);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/subtract")
    public ResponseEntity<?> subtract(@Valid @RequestBody OperationRequest request,
                                     @RequestHeader(value = "X-User-Id", required = false) String userId) {
        try {
            OperationResult result = qmaService.subtract(request, userId);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/multiply")
    public ResponseEntity<?> multiply(@Valid @RequestBody OperationRequest request,
                                      @RequestHeader(value = "X-User-Id", required = false) String userId) {
        try {
            OperationResult result = qmaService.multiply(request, userId);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }


    @PostMapping("/divide")
    public ResponseEntity<?> divide(@Valid @RequestBody OperationRequest request,
                                   @RequestHeader(value = "X-User-Id", required = false) String userId) {
        try {
            OperationResult result = qmaService.divide(request, userId);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/history")
    public ResponseEntity<List<MeasurementHistory>> getHistory(
            @RequestHeader(value = "X-User-Id", required = false) String userId) {
        if (userId == null || userId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(qmaService.getHistory(userId));
    }
}
