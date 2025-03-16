package com.example.MenuService.Kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

  private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);
  
  @KafkaListener(topics = "request_for_menu", groupId = "menu_consumer")
  public void onMenuRequest(String typeOfMenu) {
    log.info("Получен запрос типа меню: {}", typeOfMenu);
  }

  @KafkaListener(topics = {"calorie_norm", "request_for_menu"}, groupId = "menu_consumer")
  public void KafkaListener(@Payload String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
    switch (topic) {
      case "request_for_menu":
        log.info("Получен запрос типа меню: {}", message);
        break;
      case "calorie_norm":
        log.info("Получена норма калорий: {}", message);
        break;
      default:
        log.warn("Неизвестный топик: {}", topic);
    }
  }
}
