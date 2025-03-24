package com.example.gymbot.Services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import com.example.gymbot.Configurations.WebHookBot;
import com.example.gymbot.Repositories.UserRepository;

@Service
public class KeyCallBackHandler {
  private WebHookBot botConfig;
  private UserService userService;
  private  static final Logger log = LoggerFactory.getLogger(KeyCallBackHandler.class);

    @Autowired
    public  KeyCallBackHandler(WebHookBot botConfig, UserService userService) {
        this.botConfig = botConfig;
        this.userService = userService;
    }

  public void mainChoiseHandler(Update update, String callbackData, long chatId) {
    switch (callbackData) {
        case "choose_menu":
            log.info("Пользователь выбрал меню, чат: " + chatId);
            botConfig.sendMessage(chatId, "Отличный выбор! Сейчас подберем вам меню...");

            // Создаем новый набор кнопок для выбора типа меню
            SendMessage typeOfDietMessage = new SendMessage();
            typeOfDietMessage.setChatId(chatId);
            typeOfDietMessage.setText("Какой тип меню вы предпочитаете?");
            typeOfDietMessage.setReplyMarkup(createMenuOptionsKeyboard());


        // Отправляем сообщение с кнопками
            try {
               botConfig.executeForOtherServices(typeOfDietMessage);
            } catch (Exception e) {
               log.error("Ошибка при отправке сообщения с выбором меню", e);
            }
            break;

        case "choose_supplements":
            log.info("Пользователь выбрал спортивное питание, чат: " + chatId);
            botConfig.sendMessage(chatId, "Отлично! Сейчас подберем вам спортивное питание...");
            break;


        case "look_information":
            log.info("пользователь выбрал просмотр информации" + chatId);
            botConfig.sendMessage(chatId, userService.getUserInformation(chatId));
        default:
            log.warn("Неизвестный callbackData: " + callbackData);
          }
  }


  private ReplyKeyboardMarkup createMenuOptionsKeyboard() {
    KeyboardRow row1 = new KeyboardRow();
    row1.add(new KeyboardButton("Дефицит"));
    row1.add(new KeyboardButton("Профицит"));

    KeyboardRow row2 = new KeyboardRow();
    row2.add(new KeyboardButton("Поддержка веса"));

    row2.add(new KeyboardButton("Вернуться назад"));

    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
    keyboardMarkup.setKeyboard(List.of(row1, row2));
    keyboardMarkup.setResizeKeyboard(true); 

    return keyboardMarkup;
}
  
}
