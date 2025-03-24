package com.example.gymbot.Kafka;

import org.springframework.stereotype.Service;

import com.example.gymbot.Configurations.WebHookBot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class KafkaConsumer {
  private WebHookBot botConfig;
  private String message = null;
  private Long chatId = null;

     @Autowired
    public  KafkaConsumer(WebHookBot botConfig) {
        this.botConfig = botConfig;
    }
  private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);
  
  @KafkaListener(topics = "menu-service", groupId = "menu_consumer",
               containerFactory = "messageWithChatIdKafkaListenerContainerFactory")
  public void listener(MessageWithChatId messageWithChatId) {
      Long chatId = messageWithChatId.getChatId();
      String message = messageWithChatId.getMessage();
      log.info("📥 Получено сообщение с chatId: {} и сообщением: {}", chatId, message);
      botConfig.sendMessage(chatId, message);
  }


}
