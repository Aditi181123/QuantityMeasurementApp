package com.qm.auth.controller;
import com.qm.auth.dto.*;
import com.qm.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            // Call service to authenticate
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);  // 200 OK with token
        } catch (RuntimeException e) {
            // Authentication failed
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));  // 400 Bad Request
        }
    }

    
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request) {
        try {
            AuthResponse response = authService.signup(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    
    @PostMapping("/google")
    public ResponseEntity<?> googleAuth(@RequestBody GoogleUserInfo googleUser) {
        try {
            AuthResponse response = authService.processGoogleUser(googleUser);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

  
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Invalid token format"));
        }
        
        String token = authHeader.substring(7);
        
        // Token validation is handled by Spring Security
        // If we reach here, token format is valid
        return ResponseEntity.ok(Map.of("valid", true));
    }
}
