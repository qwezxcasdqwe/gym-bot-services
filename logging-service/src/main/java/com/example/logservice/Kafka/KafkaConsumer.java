package com.example.logservice.Kafka;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import com.example.logservice.ClickHouse.ClickHouseLogRepository;
import com.example.logservice.ClickHouse.LogRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component
public class KafkaConsumer {
  private final ObjectMapper objectMapper;
  private final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);
  @Autowired
  private ClickHouseLogRepository clickHouseLogRepository;

  public KafkaConsumer(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @KafkaListener(topics = "json_log", groupId = "menu_consumer")
  public void listener(String message) throws JsonProcessingException {
     try {
      LogRecord logRecord = objectMapper.readValue(message, LogRecord.class);
      log.info("Received log record: {}", logRecord);
      clickHouseLogRepository.save(logRecord);
     } 
     catch (JsonProcessingException e) {
      e.printStackTrace();
     }
  }
}
