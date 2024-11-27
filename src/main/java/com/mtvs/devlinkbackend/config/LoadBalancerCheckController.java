package com.mtvs.devlinkbackend.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoadBalancerCheckController {
    @GetMapping("/health")
    public ResponseEntity<?> checkStatus() {
        return ResponseEntity.ok().build();
    }
    @PostMapping("/health")
    public ResponseEntity<?> checkHealth() {
        return ResponseEntity.ok().build();
    }
}
