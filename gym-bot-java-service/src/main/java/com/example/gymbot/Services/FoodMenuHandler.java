package com.example.gymbot.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import com.example.gymbot.Configurations.WebHookBot;
import com.example.gymbot.Entities.UserEntity;
import com.example.gymbot.Kafka.KafkaProducerService;

@Service
public class FoodMenuHandler {
  
  private static final Logger log = LoggerFactory.getLogger(FoodMenuHandler.class);
  
  private WebHookBot	botConfig;
  private KafkaProducerService kafkaProducerService;
  private KeyMenu menu;
  @Autowired
  public FoodMenuHandler(WebHookBot botConfig, KafkaProducerService kafkaProducerService,KeyMenu menu){
    this.botConfig=botConfig;
    this.kafkaProducerService=kafkaProducerService;
    this.menu=menu;
  }

  public void sendMenu(Long chatId,String message, UserEntity user){//помимо меню, продумать проработку полученной инфы о юзере
    switch(message){
      case "Дефицит":
            log.info("пользователь выбрал меню на дефицит " + chatId);
            botConfig.sendMessage(chatId, "Вы выбрали дефицит калорий. Сейчас подберем вам меню... Процесс формирования меню может занять до 5 минут, пожалуйст подождите");
            generateMenu(chatId, "дефицит",user.getCalorieNorm(),user.getAge());
            break;   
    
    case "Профицит":
            botConfig.sendMessage(chatId, "Вы выбрали профицит калорий. Сейчас подберем вам меню...");
            generateMenu(chatId, "профицит",user.getCalorieNorm(),user.getAge());
            break;

        case "Поддержка веса":
            botConfig.sendMessage(chatId, "Вы выбрали поддержание веса. Сейчас подберем вам меню...");
            generateMenu(chatId, "поддержка веса", user.getCalorieNorm(),user.getAge());
            break;

        case "Вернуться назад":
            log.info("Вызов главного меню");
            SendMessage menuMessage = menu.sendMainMenu(chatId);
            botConfig.executeForOtherServices(menuMessage);
            break;
            
        default:
        botConfig.sendMessage(chatId, "Не понял ваш ответ. Выберите один из вариантов.");
  }
 }
 public void generateMenu(Long chatId,String typeOfMenu,Double calorieNorm,Integer age){
  if (age > 30) {
    calorieNorm *= 0.95;
  }
  try{
    log.info("Запрос микросервису отправлен");
    kafkaProducerService.sendCalories(calorieNorm);
    kafkaProducerService.sendTypeOfMenu(typeOfMenu);
    kafkaProducerService.sendChatId(chatId);
  }
  catch(Exception e){
    log.error(e.toString());
  }

 }

}
