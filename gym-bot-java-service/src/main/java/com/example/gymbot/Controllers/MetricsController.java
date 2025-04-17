package com.example.gymbot.Controllers;

import com.example.gymbot.Services.MetricsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/metrics")
public class MetricsController {

    private final MetricsService metricsService;
    private final Random random = new Random();

    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @GetMapping("/test")
    public String testMetrics() {
        metricsService.incrementApiCall();
        
        metricsService.recordApiCallTime(() -> {
            try {
                // Имитация работы
                Thread.sleep(random.nextInt(1000));
                
                // 20% вероятность ошибки
                if (random.nextInt(100) < 20) {
                    metricsService.incrementError();
                    throw new RuntimeException("Тестовая ошибка");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        return "Метрики обновлены";
    }

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalApiCalls", metricsService.getApiCallCount());
        stats.put("averageApiCallTime", metricsService.getAverageApiCallTime());
        stats.put("totalErrors", metricsService.getErrorCount());
        return stats;
    }
}