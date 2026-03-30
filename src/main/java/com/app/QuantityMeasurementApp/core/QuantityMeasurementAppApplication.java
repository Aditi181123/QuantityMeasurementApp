package com.app.QuantityMeasurementApp.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * 🔷 SPRING BOOT MAIN CLASS (ENTRY POINT)
 *
 * 📌 @SpringBootApplication = Combination of:
 *    ✔ @Configuration  → Marks this as config class
 *    ✔ @EnableAutoConfiguration → Auto configures Spring Boot
 *    ✔ @ComponentScan → Scans all packages for beans
 *
 * 📌 This is the STARTING POINT of the application
 */

@SpringBootApplication
public class QuantityMeasurementAppApplication {

    /*
     * 🔷 MAIN METHOD (JVM ENTRY POINT)
     *
     * 📌 SpringApplication.run():
     *    ✔ Starts Spring Boot application
     *    ✔ Creates ApplicationContext (IOC container)
     *    ✔ Scans components (@Service, @Repository, @Controller)
     *    ✔ Starts embedded server (Tomcat)
     */

    public static void main(String[] args) {

        // 🚀 Starts the entire Spring Boot application
        SpringApplication.run(QuantityMeasurementAppApplication.class, args);
    }

}