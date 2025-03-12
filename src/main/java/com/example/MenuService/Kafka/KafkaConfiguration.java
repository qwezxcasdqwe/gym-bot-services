package com.example.MenuService.Kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {
  @Bean
  public NewTopic newTopic(){
    return new NewTopic("menu-service", 1, (short) 1);//пока что одна партиция 
  }
}
