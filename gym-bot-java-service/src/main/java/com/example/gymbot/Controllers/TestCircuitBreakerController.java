package com.example.gymbot.Controllers;

import com.example.gymbot.Services.TestCircuitBreakerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestCircuitBreakerController {

    private final TestCircuitBreakerService testService;

    public TestCircuitBreakerController(TestCircuitBreakerService testService) {
        this.testService = testService;
    }

    @GetMapping("/unreliable")
    public String testUnreliableService() {
        return testService.testUnreliableService();
    }

    @GetMapping("/unreliable-annotation")
    public String testUnreliableServiceWithAnnotation() {
        return testService.testUnreliableServiceWithAnnotation();
    }

    @GetMapping("/state")
    public String getCircuitBreakerState() {
        return testService.getCircuitBreakerState();
    }
} 