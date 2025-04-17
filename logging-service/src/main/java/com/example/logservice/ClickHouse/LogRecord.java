package com.example.logservice.ClickHouse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime; 


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogRecord {
  private Long chatId;
  private String message;
  private LocalDateTime timestamp;
}
