package com.example.petback.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping("/actuator/health")
    public ResponseEntity checkHealthStatus() {
        System.out.println("정상");
        return ResponseEntity.ok("version2");
    }
}
