package com.app.QuantityMeasurementApp.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.QuantityMeasurementApp.security.CustomUserDetailsService;
import com.app.QuantityMeasurementApp.security.JwtFilter;
import com.app.QuantityMeasurementApp.security.JwtUtil;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    JwtUtil jwtUtil() {
        return new JwtUtil();
    }

    @Bean
    JwtFilter jwtFilter(JwtUtil jwtUtil) {
        return new JwtFilter(jwtUtil, userDetailsService);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                .requestMatchers(
                        "/",
                        "/api",
                        "/auth/**",
                        "/oauth2/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/h2-console/**",

                        "/api/v1/quantities",
                        "/api/v1/quantities/compare",
                        "/api/v1/quantities/convert",
                        "/api/v1/quantities/add",
                        "/api/v1/quantities/subtract",
                        "/api/v1/quantities/multiply",
                        "/api/v1/quantities/divide",
                        "/api/v1/quantities/count/**"
                ).permitAll()

                .requestMatchers("/api/v1/quantities/history/**").authenticated()

                .anyRequest().authenticated()
            )

            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            )

            .oauth2Login(oauth -> oauth
                    .defaultSuccessUrl("/auth/oauth-success", true)
            )

            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            )

            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}