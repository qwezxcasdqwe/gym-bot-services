package com.example.MenuService.Kafka;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

  private final KafkaService kafkaProducer;

    public KafkaController(KafkaService kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }
  
  @PostMapping("/kafka/send")
  public String send(@RequestBody String message){

    kafkaProducer.sendMessage(message);
    return "kafka success";
  }

  
}
