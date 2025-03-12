package com.example.MenuService.Kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

  private final KafkaService kafkaProducer;

  private static final Logger log = LoggerFactory.getLogger(KafkaController.class);

    public KafkaController(KafkaService kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }
  
  @PostMapping("/kafka/send")
  public String send(@RequestBody String message){
    log.info("контроллер kafka получил сообщение " + message);
    kafkaProducer.sendMessage(message);
    return "kafka success";
  }  
}
