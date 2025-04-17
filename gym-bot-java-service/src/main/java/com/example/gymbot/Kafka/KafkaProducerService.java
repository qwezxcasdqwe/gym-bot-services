package com.example.gymbot.Kafka;

import java.time.Instant;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class KafkaProducerService {

  private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);
  @Autowired
  private final KafkaTemplate <String,String> kafkaTemplateString;
  
  @Autowired
  private final KafkaTemplate <Long,Long> kafkaTemplateId;

  private final ObjectMapper objectMapper;

  public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate,KafkaTemplate <Long,Long> kafkaTemplateId,ObjectMapper objectMapper) {
    this.kafkaTemplateString = kafkaTemplate;
    this.kafkaTemplateId =kafkaTemplateId;
    this.objectMapper=objectMapper;
}

  public void sendTypeOfMenu(String typeOfMenu){
    log.info("Отправлен запрос меню в сервис");
    kafkaTemplateString.send("request_for_menu", typeOfMenu);
  }

  public void sendCalories(Double calories){
    log.info("Отправлена норма каллорий в сервис");
    kafkaTemplateString.send("calorie_norm", String.valueOf(calories));
  }
  public void sendChatId(Long chatId){
    try{
    kafkaTemplateId.send("chat_id", chatId);
    log.info("Передан номер чата " + chatId);
  }  
  catch(Exception e){
    log.error(e.toString());
  }  
}
 public void sendJsonLog(String message,Long chatId){
  try{
    Map<String,Object> log  = Map.of(
      "chatId", chatId,
      "message", message,
      "timestamp", Instant.now().toString()
    );
    String json = objectMapper.writeValueAsString(log);
    kafkaTemplateString.send("json_log", json);
  }
  catch(Exception e){
    log.info("Не удалось отправить лог", e);
  }
 }
 public void sendJsonError(String errorMessage, Long chatId, Long errorLevel){
  try{
    Map<String,Object> error = Map.of(
      "chatId", chatId,
      "message", errorMessage,
      "errorLevel", errorLevel,
      "timestamp", Instant.now().toString()
    );
    String jsonError = objectMapper.writeValueAsString(error);
    kafkaTemplateString.send("error_log",jsonError);
  }
  catch(Exception e){
    log.info("Ошибка при отправке лога об ошибке", e);
  }
 }
}
  