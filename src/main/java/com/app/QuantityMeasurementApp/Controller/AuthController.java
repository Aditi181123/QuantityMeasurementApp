package com.app.QuantityMeasurementApp.controller;

import com.app.QuantityMeasurementApp.security.JwtUtil;
import com.app.QuantityMeasurementApp.user.*;

import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserRepository userRepository,
                          JwtUtil jwtUtil,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setProvider("LOCAL");
        userRepository.save(user);
        return "User Registered";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );

        return jwtUtil.generateToken(user.getUsername());
    }

    @GetMapping("/oauth-success")
    public String oauthSuccess(Authentication auth) {

        String username = auth.getName();

        userRepository.findByUsername(username)
                .orElseGet(() -> {
                    User u = new User();
                    u.setUsername(username);
                    u.setProvider("GOOGLE");
                    return userRepository.save(u);
                });

        return jwtUtil.generateToken(username);
    }
}