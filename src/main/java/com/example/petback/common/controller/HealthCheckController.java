package com.example.petback.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HealthCheckController {
    @GetMapping("/actuator/health")
    public ResponseEntity checkHealthStatus() {
        log.info("정상");
        return ResponseEntity.ok("version2");
    }
}
