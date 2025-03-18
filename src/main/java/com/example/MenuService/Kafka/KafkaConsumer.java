package com.example.MenuService.Kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.example.MenuService.AI.OllamaService;

@Service
public class KafkaConsumer {

  private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

  private String goal;
  private String calorieNorm;
  private String promtForModel="Ты языковая модель созданная чтобы помогать людям с составлением меню для определенных целей: набора веса, похудения или поддержки веса, дальше я попрошу тебя составить мне меню, отвечай только по факту, напиши 3 приема исходя из целей, также напиши граммовку каждого блюда, конечное колличество белков жиров и углеводов.Отвечай только по русски. Ответ должен быть в таком виде: 'Завтрак - яичница из 3 яиц(20 грамм белка)' вот я написал тебе пример сделай также. просьба будет такой: ";
  private Long chat_id;
  @Autowired
  private KafkaService kafkaService;

  @KafkaListener(topics = "request_for_menu", groupId = "menu_consumer")
  public void onRequestForMenu(@Payload String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
      log.info("Получен запрос типа меню: {}", message);
      this.goal = extractGoalFromMessage(message);
      generatePrompt();
  }

  @KafkaListener(topics = "calorie_norm", groupId = "menu_consumer")
  public void onCalorieNorm(@Payload String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
      log.info("Получена норма калорий: {}", message);
      try {
          this.calorieNorm = message;
      } catch (NumberFormatException e) {
          log.warn("Не удалось разобрать норму калорий: {}", message);
      }
      generatePrompt();
  }

  @KafkaListener(topics= "chat_id", groupId = "menu_consumer")
  private void onChatId(@Payload Long chatId,@Header(KafkaHeaders.RECEIVED_TOPIC) String topic){
    this.chat_id=chatId;
    generatePrompt();
  }

  private void generatePrompt() {
    if (goal != null && calorieNorm != null) {
        String prompt = String.format("Подбери меню для человека с целью: %s и с дневной нормой калорий: %s", goal, calorieNorm);
        log.info("Сформирован промт: {}", prompt);
        String answerFromModel = sendToModel(prompt);

        // Создаем объект с chatId и ответом от модели
        MessageWithId messageWithChatId = new MessageWithId(chat_id, answerFromModel);

        // Отправляем объект в Kafka
        kafkaService.sendMessage(messageWithChatId);
        log.info("Ответ модели направлен в kafka");
    }
}

  private String sendToModel(String message) {
      OllamaService ollamaService = new OllamaService();
      String response = ollamaService.generateResponse("mistral", promtForModel, message);
      log.info("Ответ от модели: {}", response);
      return response;
  }

  private String extractGoalFromMessage(String message) {
      if (message.contains("дефицит")) {
          return "снижение веса";
      } else if (message.contains("поддержка веса")) {
          return "поддержание";
      } else if (message.contains("профицит")) {
          return "набор массы";
      } else {
          return "неизвестная цель";
      }
  }
}
