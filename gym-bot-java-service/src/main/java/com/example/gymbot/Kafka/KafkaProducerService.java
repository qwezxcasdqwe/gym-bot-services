package com.example.gymbot.Kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class KafkaProducerService {

  private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);
  @Autowired
  private final KafkaTemplate <String,String> kafkaTemplateString;
  
  @Autowired
  private final KafkaTemplate <Long,Long> kafkaTemplateId;

  public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate,KafkaTemplate <Long,Long> kafkaTemplateId) {
    this.kafkaTemplateString = kafkaTemplate;
    this.kafkaTemplateId =kafkaTemplateId;
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
}
