package com.example.logservice.ClickHouse;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
@Component
public class ClickHouserInitializer implements CommandLineRunner {
  //@Value("${clickhouse.url}")
  private String url = "jdbc:clickhouse://clickhouse:8123/default";
  @Value("${clickhouse.username}")
  private String username;
  @Value("${clickhouse.password}")
  private String password;

  @Override
  public void run(String... args) throws Exception {
    try {
      Connection connection = DriverManager.getConnection(url, username, password);
      Statement statement = connection.createStatement();
      String createTableSQL = """
        CREATE TABLE IF NOT EXISTS logs (
            chatId UInt64,
            message String,
            timestamp DateTime
        ) ENGINE = MergeTree()
        ORDER BY timestamp
        """;
      statement.execute(createTableSQL);
      connection.close();
    } catch (SQLException e) {
      System.err.println("❌ Ошибка при создании таблицы в ClickHouse: " + e.getMessage());
      throw e;
    }
  }
}
