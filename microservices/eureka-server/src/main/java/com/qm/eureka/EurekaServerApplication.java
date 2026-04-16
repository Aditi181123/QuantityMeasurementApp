package com.qm.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * ╔═══════════════════════════════════════════════════════════════════════════════════╗
 * ║                         EUREKA SERVER APPLICATION                           ║
 * ║                                                                              ║
 * ║  Service Registry and Discovery Server                                        ║
 * ║                                                                              ║
 * ║  Responsibilities:                                                           ║
 * ║  1. Maintain a registry of all running services                             ║
 * ║  2. Allow services to find each other by name                               ║
 * ║  3. Provide a web dashboard for monitoring                                  ║
 * ║                                                                              ║
 * ║  Port: 8761                                                                ║
 * ║  Dashboard: http://localhost:8761                                            ║
 * ╚═══════════════════════════════════════════════════════════════════════════════════╝
 */

/**
 * @EnableEurekaServer - Marks this as a Eureka Service Registry
 * 
 * What this enables:
 * - REST API for service registration
 * - Web dashboard at /
 * - Periodic heartbeat checking from services
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
