package com.example.gymbot.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class KeyMenu {
  public SendMessage sendMainMenu(long chatId){
    SendMessage message = new SendMessage();
    message.setChatId(chatId);
    message.setText("Что вы хотите сделать дальше?");

    // Создаем клавиатуру
    InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
    List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

    // Кнопка "Подобрать меню"
    InlineKeyboardButton menuButton = new InlineKeyboardButton();
    menuButton.setText("📋 Подобрать меню");
    menuButton.setCallbackData("choose_menu"); 

    // Кнопка "Подобрать спортпит"
    InlineKeyboardButton supplementsButton = new InlineKeyboardButton();
    supplementsButton.setText("🏋 Подобрать спортпит");
    supplementsButton.setCallbackData("choose_supplements");

    InlineKeyboardButton infoButton = new InlineKeyboardButton();
    infoButton.setText("📋  Посмотреть свою информацию");
    infoButton.setCallbackData("look_information");

    // Добавляем кнопки в строку
    List<InlineKeyboardButton> row = List.of(menuButton, supplementsButton,infoButton);
    keyboard.add(row);

    keyboardMarkup.setKeyboard(keyboard);
    message.setReplyMarkup(keyboardMarkup);

    return message;
  }
  public SendMessage sendDeficitOrProfecitQuestion(long chatId) {
    SendMessage message = new SendMessage();
    message.setChatId(chatId);
    message.setText("Какую цель вы преследуете?");

    InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();//инциализация меню
    List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

    InlineKeyboardButton bulkButton = new InlineKeyboardButton();//инциализация кнопок
    bulkButton.setText("🍗 Набор массы");
    bulkButton.setCallbackData("menu_bulk");

    InlineKeyboardButton cutButton = new InlineKeyboardButton();
    cutButton.setText("🥗 Сушка");
    cutButton.setCallbackData("menu_cut");

    List<InlineKeyboardButton> row = List.of(bulkButton, cutButton);
    keyboard.add(row);

    keyboardMarkup.setKeyboard(keyboard);
    message.setReplyMarkup(keyboardMarkup);
    
    return message;
}
  
}
