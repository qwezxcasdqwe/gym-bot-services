package com.example.gymbot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.gymbot.Configurations.WebHookBot;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class GymBotApplication {

    public static String botToken;

    private static final Logger log = LoggerFactory.getLogger(GymBotApplication.class);


    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.configure().directory("./").load();

        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });
        try{
          createDatabaseIfNotExists();
          log.info("Операции с базой выполнены успешно");
        }
        catch(Exception e){
          log.info(e.toString());
        }
        SpringApplication.run(GymBotApplication.class, args);
    }

    private static void createDatabaseIfNotExists() {
        String url = "jdbc:postgresql://db:5432/postgres";
        String user = "postgres";
        String password = "postgres";
    
        // SQL для создания таблицы
        String createTableSql = "CREATE TABLE IF NOT EXISTS users (" +
                                "chat_id BIGINT PRIMARY KEY, " +
                                "age INT, " +
                                "weight DOUBLE PRECISION, " +
                                "height DOUBLE PRECISION, " +
                                "activity_level VARCHAR(255), " +
                                "calorie_norm DOUBLE PRECISION);";
    
        String createDbUrl = "jdbc:postgresql://db:5432/";
    
        try (Connection conn = DriverManager.getConnection(createDbUrl, user, password);
             Statement stmt = conn.createStatement()) {
    
            // Проверка существования базы данных
            try {
                stmt.executeQuery("SELECT 1 FROM pg_database WHERE datname = 'postgres';");
            } catch (SQLException e) {
                // Если база данных не существует, создаем ее
                stmt.execute("CREATE DATABASE postgres;");
            }
    
            String urlWithDb = "jdbc:postgresql://db:5432/postgres";
            try (Connection connDb = DriverManager.getConnection(urlWithDb, user, password);
                 Statement stmtDb = connDb.createStatement()) {
    
                stmtDb.execute(createTableSql);
    
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Ошибка при подключении к базе данных для создания таблицы.");
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка при подключении к базе данных для создания базы данных.");
        }
    }
    
}
