package com.example.gymbot.Kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class KafkaProducer {

  @Bean
  public NewTopic requestMenuTopic(){
    return new NewTopic("request_for_menu", 1,(short) 1);
  }

  @Bean
  public NewTopic caloriesNormTopic(){
    return new NewTopic("calorie_norm", 1,(short) 1);
  }  
  @Bean
  public NewTopic chatId(){
    return new NewTopic("chat_id", 1,(short) 1);
  } 
  
  @Bean
  public ConsumerFactory<String, Long> longConsumerFactory() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
    return new DefaultKafkaConsumerFactory<>(props);
}

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, Long> longKafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, Long> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(longConsumerFactory()); // Используем бин выше
    return factory;
}
@Bean
public ConsumerFactory<String, MessageWithChatId> messageWithChatIdConsumerFactory() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
    props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.example.MenuService.Kafka"); 
    return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(MessageWithChatId.class));
}
@Bean
public ConcurrentKafkaListenerContainerFactory<String, MessageWithChatId> messageWithChatIdKafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, MessageWithChatId> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(messageWithChatIdConsumerFactory());
    return factory;
}
@Bean
public ConsumerFactory<String, String> stringConsumerFactory() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    return new DefaultKafkaConsumerFactory<>(props);
}
@Bean
public ConcurrentKafkaListenerContainerFactory<String, String> stringKafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(stringConsumerFactory());
    return factory;
}
@Bean
public KafkaTemplate<Long, Long> kafkaTemplateId() {
    return new KafkaTemplate<>(producerFactory());
}

@Bean
public ProducerFactory<Long, Long> producerFactory() {
    Map<String, Object> producerProps = new HashMap<>();
    producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
    producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, LongSerializer.class); // Сериализатор для значения типа Long
    producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);  // Сериализатор для ключа типа Long
    return new DefaultKafkaProducerFactory<>(producerProps);
}

@Bean
public KafkaTemplate<String, String> kafkaTemplateString() {
    return new KafkaTemplate<>(stringProducerFactory());
}

@Bean
public ProducerFactory<String, String> stringProducerFactory() {
    Map<String, Object> producerProps = new HashMap<>();
    producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
    producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // Сериализатор для значения типа String
    producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);  // Сериализатор для ключа типа String
    return new DefaultKafkaProducerFactory<>(producerProps);
}







}
