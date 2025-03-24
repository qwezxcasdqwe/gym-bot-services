package com.example.MenuService.AI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OllamaService {

     private static final Logger log = LoggerFactory.getLogger(OllamaService.class);

    public String generateResponse(String model, String prompt, String message) {
      log.info("Отправлен запрос к модели " + message);
      try {
  
          String url = "http://192.168.1.84:11434/api/generate";
          URL obj = new URL(url);
          HttpURLConnection con = (HttpURLConnection) obj.openConnection();
  
          con.setRequestMethod("POST");
  
          con.setRequestProperty("Content-Type", "application/json");
  
          con.setDoOutput(true);
  
          String jsonInputString = String.format("{\"model\": \"%s\", \"prompt\": \"%s %s\", \"stream\": false}",
                  model, prompt, message);
  
          try (OutputStream os = con.getOutputStream()) {
              byte[] input = jsonInputString.getBytes("utf-8");
              os.write(input, 0, input.length);
          }
  
          // Получаем код ответа от сервера
          int responseCode = con.getResponseCode();
          System.out.println("Response Code: " + responseCode);
  
          // Читаем ответ
          try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
              String inputLine;
              StringBuilder content = new StringBuilder();
              while ((inputLine = in.readLine()) != null) {
                  content.append(inputLine);
              }
  
              // Парсим JSON и извлекаем только нужное поле
              String responseContent = extractResponse(content.toString());
  
              // Логируем только текст ответа
              log.info("Ответ модели: " + responseContent);
              return responseContent;
          }
  
      } catch (Exception e) {
          e.printStackTrace();
          return null;
      }
  }
  
  private String extractResponse(String json) {
      try {
          // Используем ObjectMapper для парсинга JSON
          ObjectMapper objectMapper = new ObjectMapper();
          JsonNode jsonNode = objectMapper.readTree(json);
          
          // Извлекаем поле "response"
          return jsonNode.path("response").asText();
      } catch (Exception e) {
          e.printStackTrace();
          return "Ошибка при обработке ответа модели";
      }
}
}

