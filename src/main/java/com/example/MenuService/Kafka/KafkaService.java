package com.example.MenuService.Kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

  private final KafkaTemplate <String,String> kafkaTemplate;

  private static final Logger log = LoggerFactory.getLogger(KafkaController.class);

    public KafkaService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
      try {
          kafkaTemplate.send("menu-service", message).get(); // Ожидаем подтверждение
          log.info("✅ Сообщение успешно отправлено в Kafka: {}", message);
      } catch (Exception e) {
          log.error("❌ Ошибка при отправке сообщения в Kafka", e);
      }
  }
  
}
