package com.example.MenuService.Kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.apache.kafka.common.serialization.StringSerializer;


@Configuration
public class KafkaConfiguration {

      @Bean
    public MessageWithIdSerializer messageWithIdSerializer() {
        return new MessageWithIdSerializer();  // Ваш сериализатор
    }

    // Бин для KafkaTemplate для отправки MessageWithId
    @Bean
    public KafkaTemplate<String, MessageWithId> kafkaTemplate(ProducerFactory<String, MessageWithId> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    // Конфигурация ProducerFactory для MessageWithId
    @Bean
    public ProducerFactory<String, MessageWithId> producerFactory() {
        Map<String, Object> producerProps = new HashMap<>();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");  // Укажите ваш брокер Kafka
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return new DefaultKafkaProducerFactory<>(producerProps);
    }
  @Bean
  public NewTopic newTopic(){
    return new NewTopic("menu-service", 1, (short) 1);//пока что одна партиция 
  }

  @Bean
  public NewTopic chatIdBackTopic(){
    return new NewTopic("chatIdBack", 1, (short) 1);//пока что одна партиция 
  }
}
