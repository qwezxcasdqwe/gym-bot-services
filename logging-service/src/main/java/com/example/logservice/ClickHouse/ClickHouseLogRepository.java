package com.example.logservice.ClickHouse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClickHouseLogRepository {
    
    private String url = "jdbc:clickhouse://clickhouse:8123";
    
    @Value("${clickhouse.username}")
    private String username;
    
    @Value("${clickhouse.password}")
    private String password;    

    private String database="default";

    public void save(LogRecord record) {
        String sql = "INSERT INTO logs (chatId, message, timestamp) VALUES (?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(url + "/" + database, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, record.getChatId());
            stmt.setString(2, record.getMessage());
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(record.getTimestamp()));
            
            stmt.execute();
            log.info("Successfully saved log record for chatId: {}", record.getChatId());
            
        } catch (SQLException e) {
            log.error("Error saving log record: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to save log record", e);
        }
    }
}
