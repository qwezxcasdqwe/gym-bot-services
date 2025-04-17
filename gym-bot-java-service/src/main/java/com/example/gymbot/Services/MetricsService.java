package com.example.gymbot.Services;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.Gauge;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MetricsService {

    private final Counter apiCallCounter;
    private final Timer apiCallTimer;
    private final Counter errorCounter;
    private final Counter telegramMessagesCounter;
    private final Counter kafkaMessagesCounter;
    private final AtomicInteger activeUsersGauge;
    private final Timer databaseQueryTimer;

    public MetricsService(MeterRegistry registry) {
        this.apiCallCounter = Counter.builder("api.calls.total")
                .description("Total number of API calls")
                .tag("application", "gym-bot")
                .register(registry);

        this.apiCallTimer = Timer.builder("api.calls.duration")
                .description("API call duration")
                .tag("application", "gym-bot")
                .register(registry);
                
        this.errorCounter = Counter.builder("api.errors.total")
                .description("Total number of API errors")
                .tag("application", "gym-bot")
                .register(registry);

        this.telegramMessagesCounter = Counter.builder("telegram.messages.total")
                .description("Total number of Telegram messages processed")
                .tag("application", "gym-bot")
                .register(registry);

        this.kafkaMessagesCounter = Counter.builder("kafka.messages.total")
                .description("Total number of Kafka messages processed")
                .tag("application", "gym-bot")
                .register(registry);

        this.activeUsersGauge = new AtomicInteger(0);
        Gauge.builder("active.users", activeUsersGauge, AtomicInteger::get)
                .description("Number of currently active users")
                .tag("application", "gym-bot")
                .register(registry);

        this.databaseQueryTimer = Timer.builder("database.query.duration")
                .description("Database query duration")
                .tag("application", "gym-bot")
                .register(registry);
    }

    public void incrementApiCall() {
        apiCallCounter.increment();
    }

    public void recordApiCallTime(Runnable operation) {
        apiCallTimer.record(operation);
    }

    public void incrementError() {
        errorCounter.increment();
    }

    public void incrementTelegramMessage() {
        telegramMessagesCounter.increment();
    }

    public void incrementKafkaMessage() {
        kafkaMessagesCounter.increment();
    }

    public void setActiveUsers(int count) {
        activeUsersGauge.set(count);
    }

    public void recordDatabaseQueryTime(Runnable operation) {
        databaseQueryTimer.record(operation);
    }

    public long getApiCallCount() {
        return (long) apiCallCounter.count();
    }

    public double getAverageApiCallTime() {
        return apiCallTimer.mean(TimeUnit.MILLISECONDS);
    }

    public long getErrorCount() {
        return (long) errorCounter.count();
    }

    public long getTelegramMessageCount() {
        return (long) telegramMessagesCounter.count();
    }

    public long getKafkaMessageCount() {
        return (long) kafkaMessagesCounter.count();
    }

    public int getActiveUsers() {
        return activeUsersGauge.get();
    }

    public double getAverageDatabaseQueryTime() {
        return databaseQueryTimer.mean(TimeUnit.MILLISECONDS);
    }
}