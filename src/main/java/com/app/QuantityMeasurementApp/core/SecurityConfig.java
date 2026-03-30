package com.app.QuantityMeasurementApp.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/*
 * 🔷 SPRING SECURITY CONFIGURATION CLASS
 *
 * 📌 @Configuration
 *    ✔ Marks this as a configuration class
 *    ✔ Spring will scan and apply these settings
 */

@Configuration
public class SecurityConfig {

    /*
     * 🔷 SECURITY FILTER CHAIN (MAIN SECURITY CONFIG)
     *
     * 📌 @Bean
     *    ✔ Registers this method as a Spring Bean
     *    ✔ Used to configure security rules
     *
     * 📌 HttpSecurity
     *    ✔ Used to define security behavior (auth, CSRF, headers)
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            /*
             * 🔷 CSRF (Cross-Site Request Forgery)
             *
             * 📌 Disabled here for:
             *    ✔ H2 console access
             *    ✔ Development/testing
             *
             * ⚠️ In production → should be ENABLED
             */
            .csrf(csrf -> csrf.disable())

            /*
             * 🔷 AUTHORIZATION RULES
             *
             * 📌 requestMatchers("/h2-console/**")
             *    ✔ Allows access to H2 database console
             *
             * 📌 anyRequest().permitAll()
             *    ✔ Allows ALL APIs without authentication
             *    ✔ Used only in DEV mode
             */
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll()
                .anyRequest().permitAll()
            )

            /*
             * 🔷 HEADERS CONFIGURATION
             *
             * 📌 frameOptions().disable()
             *    ✔ Allows H2 console to load in browser (iframe)
             *    ✔ Without this → H2 console won't open
             */
            .headers(headers -> headers
                .frameOptions(frame -> frame.disable())
            );

        /*
         * 📌 build()
         *    ✔ Finalizes security configuration
         */
        return http.build();
    }
}