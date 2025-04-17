package com.example.gymbot.Services;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalApiService {

    private final RestTemplate restTemplate;
    private final CircuitBreaker circuitBreaker;

    public ExternalApiService(RestTemplate restTemplate, CircuitBreakerRegistry circuitBreakerRegistry) {
        this.restTemplate = restTemplate;
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("gymBotCircuitBreaker");
    }

    public String callExternalApi(String url) {
        return circuitBreaker.executeSupplier(() -> {
            try {
                return restTemplate.getForObject(url, String.class);
            } catch (Exception e) {
                throw new RuntimeException("Failed to call external API", e);
            }
        });
    }

    @io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker(name = "gymBotCircuitBreaker", fallbackMethod = "fallbackMethod")
    public String callExternalApiWithAnnotation(String url) {
        return restTemplate.getForObject(url, String.class);
    }

    private String fallbackMethod(String url, Exception e) {
        return "Fallback response for URL: " + url;
    }
} 