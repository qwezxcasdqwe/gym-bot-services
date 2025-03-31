package com.example.gymbot.Configurations;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import com.example.gymbot.Entities.UserEntity;
import com.example.gymbot.Entities.UserInfo;
import com.example.gymbot.Services.Asker;
import com.example.gymbot.Services.Calories;
import com.example.gymbot.Services.KeyCallBackHandler;
import com.example.gymbot.Services.KeyMenu;
import com.example.gymbot.Services.FoodMenuHandler;
import com.example.gymbot.Services.UserService;

import org.springframework.context.annotation.Lazy;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class WebHookBot extends TelegramLongPollingBot{
  @Value("${telegram.bot.username}")
  private  String botUsername;
  @Value("${telegram.bot.token}") 
  private String botToken;
  private  String botPath="/webhook";

  private final Asker asker;
  private final Calories caloriesService;
  private final KeyMenu menu;
  private final KeyCallBackHandler keyCallBackHandler;
  private final UserService userService;
  private final FoodMenuHandler menuHandler;

  @SuppressWarnings("deprecation")
  @Autowired
  public WebHookBot(@Lazy Asker asker,@Lazy Calories caloriesService,KeyMenu menu, @Lazy KeyCallBackHandler keyCallBackHandler,UserService userService,@Lazy FoodMenuHandler menuHandler) { // Lazy загрузка, чтобы избежать цикла
      this.asker = asker;
      this.caloriesService=caloriesService;
      this.menu=menu;
      this.keyCallBackHandler=keyCallBackHandler;
      this.userService=userService;
      this.menuHandler=menuHandler;
  }

  private static final Logger log = LoggerFactory.getLogger(WebHookBot.class);

  private String helloText = "Здравствуй, я чат бот созданный для того чтобы помогать людям инетересующимся железным спортом ! Давайте начнем с подсчета вшей дневной нормы каллорий.";
  private String errorResponse = "Извините, не понял ваше сообщение.";
  private String errorUserFinding = "Ваши данные не найдены, сначала пройдите стартовый опрос !"; 
  private Map<Long, UserInfo> userInfoMap = new HashMap<>();
  private Map<Long, Integer> userStepMap = new HashMap<>();
  

  @Override
  public String getBotUsername() {
    return botUsername;
  }
  @Override
  public String getBotToken() {
      return botToken; 
  }

  @Override
public void onUpdateReceived(Update update) {
    if (update.hasMessage() && update.getMessage() != null) {
        log.info("получено текст сообщение");
        Message message = update.getMessage();
        if (message.hasText()) {
            String messageText = message.getText();
            long chatId = message.getChatId();
            if (messageText.equals("/start")) {
                log.info("Сообщение /start получено от чата " + chatId);
                userInfoMap.put(chatId, new UserInfo());
                userStepMap.put(chatId, 1);

                SendMessage helloMessage = new SendMessage();
                helloMessage.setChatId(chatId);
                helloMessage.setText(helloText);

                try {
                    execute(helloMessage);
                    asker.askQuestion(chatId, 1);
                } catch (TelegramApiException e) {
                    log.error("Ошибка отправки сообщения пользователю " + chatId, e);
                }
            } else {
                if (userStepMap.containsKey(chatId)) {
                    processUserInput(chatId, messageText);
                }
            }
            if(messageText.equals("Дефицит") || messageText.equals("Профицит") || messageText.equals("Поддержка веса") || messageText.equals("Вернуться назад")){
                  log.info("Пользователь выбрал тип меню " + chatId);
                  UserEntity user = userService.takeUserById(chatId);
                  if(user==null){
                  sendMessage(chatId, errorUserFinding);
                  }
                  else{
                  menuHandler.sendMenu(chatId, messageText,user);
            }
         }
        }
    }  if (update.hasCallbackQuery()) { // Обработка callback-запроса
        log.info("получено callback сообщение");
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String callbackData = callbackQuery.getData();
        
        if (callbackData != null) {
            long chatId = callbackQuery.getFrom().getId();
            log.info("Обработка callback-запроса для чата " + chatId + " с данными: " + callbackData);
            try{keyCallBackHandler.mainChoiseHandler(update, callbackData, chatId);}
            catch(Exception e){log.info(e.toString());}
            
        } else {
            log.warn("Получен пустой callbackData от чата " + callbackQuery.getFrom().getId());
        }
    }
}


  public void processUserInput(long chatId, String messageText){
    Integer step = userStepMap.get(chatId);
    UserInfo userInfo = userInfoMap.get(chatId);

    if (step == 1) {  // Вопрос 1: Возраст
        try {
            userInfo.setAge(Integer.parseInt(messageText));
            userStepMap.put(chatId, 2);  // Переходим ко второму вопросу
            asker.askQuestion(chatId, 2);  // Задаем второй вопрос
        } catch (NumberFormatException e) {
            sendMessage(chatId, "Пожалуйста, введите ваш возраст числом.");
        }
    } else if (step == 2) {  // Вопрос 2: Вес
        try {
            userInfo.setWeight(Double.parseDouble(messageText));
            userStepMap.put(chatId, 3);  // Переходим к третьему вопросу
            asker.askQuestion(chatId, 3);  // Задаем третий вопрос
        } catch (NumberFormatException e) {
            sendMessage(chatId, "Пожалуйста, введите ваш вес числом.");
        }
    } else if (step == 3) {  // Вопрос 3: Рост
        try {
            userInfo.setHeight(Double.parseDouble(messageText));
            userStepMap.put(chatId, 4);  // Все данные собраны, теперь подсчитаем калории
            sendMessage(chatId, "Спасибо за информацию! Ваши данные собраны.");
            caloriesService.calculateCalories(userInfo, chatId);  // Подсчитаем калории
            userInfo.setCalorieNorm(caloriesService.caloriesReturner(userInfo, chatId));
            log.info("Собраны данные для сохранения пользователя " + chatId );
            userService.saveUserData(chatId, userInfo.getAge(), userInfo.getWeight(), userInfo.getHeight(), userInfo.getCalorieNorm());
            SendMessage menuMessage = menu.sendMainMenu(chatId);//Отправляем наше основное меню
            try{
                execute(menuMessage);
            }
            catch(TelegramApiException e) {
                log.info("ошибка отправки меню выбор пользователю ");
                e.printStackTrace();
            }
        } catch (NumberFormatException e) {
            sendMessage(chatId, "Пожалуйста, введите ваш рост числом.");
        }
    }

  }
  public void sendMessage(Long chatId, String text) {
    SendMessage message = new SendMessage();
    message.setChatId(String.valueOf(chatId));
    message.setText(text);
    try {
        execute(message);
    } catch (TelegramApiException e) {
        e.printStackTrace();
    }
  }
  public void sendMessageWithMessage(SendMessage message){
    try {
        execute(message);
    } catch (TelegramApiException e) {
        e.printStackTrace();
    }
  }

  public void executeForOtherServices(SendMessage message){
    try{
        execute(message);
    }
    catch(TelegramApiException e){
        log.info("Ошибка отправки сообщения дополнительного сервиса " + e.toString());
    }
  }

}
