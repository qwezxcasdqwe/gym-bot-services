package com.example.MenuService.Kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

  private final KafkaTemplate<String, MessageWithChatId> kafkaTemplateMessage;
  private static final Logger log = LoggerFactory.getLogger(KafkaService.class);
     
    public KafkaService(KafkaTemplate<String, MessageWithChatId> kafkaTemplateMessage) {
        this.kafkaTemplateMessage=kafkaTemplateMessage;
    }

    public void sendMessage(MessageWithChatId messageWithChatId) {
      log.info("kafka получил запрос на отправление в бота " + messageWithChatId.getMessage());
      kafkaTemplateMessage.send("menu-service", messageWithChatId);
      log.info("✅ Сообщение успешно отправлено в Kafka с chatId {}", messageWithChatId.getChatId());
  }
    
  }
  