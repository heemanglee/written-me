package com.match.team.migration_kotlin.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {

    @GetMapping("/health")
    fun elbHealthCheck(): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.OK).body("Success Health Check")
    }
}