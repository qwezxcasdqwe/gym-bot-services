package com.example.gymbot.Services;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Service
public class TestCircuitBreakerService {

    private final RestTemplate restTemplate;
    private final CircuitBreaker circuitBreaker;
    private final Random random = new Random();

    public TestCircuitBreakerService(RestTemplate restTemplate, CircuitBreakerRegistry circuitBreakerRegistry) {
        this.restTemplate = restTemplate;
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("gymBotCircuitBreaker");
    }

    // Метод, который имитирует вызов внешнего сервиса с вероятностью ошибки
    public String testUnreliableService() {
        return circuitBreaker.executeSupplier(() -> {
            // Имитируем 50% вероятность ошибки
            if (random.nextBoolean()) {
                throw new RuntimeException("Случайная ошибка сервиса");
            }
            return "Успешный ответ от сервиса";
        });
    }

    // Метод с аннотацией @CircuitBreaker
    @io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker(name = "gymBotCircuitBreaker", fallbackMethod = "fallbackMethod")
    public String testUnreliableServiceWithAnnotation() {
        // Имитируем 50% вероятность ошибки
        if (random.nextBoolean()) {
            throw new RuntimeException("Случайная ошибка сервиса");
        }
        return "Успешный ответ от сервиса";
    }

    // Fallback метод
    private String fallbackMethod(Exception e) {
        return "Fallback ответ: Сервис временно недоступен";
    }

    public String getCircuitBreakerState() {
        return "Состояние Circuit Breaker: " + circuitBreaker.getState() + 
               "\nМетрики: " + 
               "\n- Количество успешных вызовов: " + circuitBreaker.getMetrics().getNumberOfSuccessfulCalls() +
               "\n- Количество неудачных вызовов: " + circuitBreaker.getMetrics().getNumberOfFailedCalls() +
               "\n- Процент отказов: " + circuitBreaker.getMetrics().getFailureRate() + "%";
    }
} 