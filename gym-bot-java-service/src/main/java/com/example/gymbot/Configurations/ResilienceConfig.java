package com.example.gymbot.Configurations;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.qos.logback.core.net.SocketConnector.ExceptionHandler;

import java.time.Duration;

@Configuration
public class ResilienceConfig {
    @Bean
    public CircuitBreaker circuitBreaker() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(51) // Процент отказов, при котором срабатывает Circuit Breaker
                .waitDurationInOpenState(Duration.ofMillis(1000)) // Время ожидания в открытом состоянии
                .permittedNumberOfCallsInHalfOpenState(2) // Количество разрешенных вызовов в полуоткрытом состоянии
                .slidingWindowSize(2)
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED) // Тип окна (по количеству вызовов)
                .minimumNumberOfCalls(4) // Минимальное количество вызовов для расчета процента отказов
                .recordExceptions(Exception.class) // Какие исключения считать ошибками
                .build();

        return CircuitBreaker.of("gymBotCircuitBreaker", config);
    }
} 