package com.example.gymbot.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gymbot.Configurations.WebHookBot;


@Service
public class Asker {

    private WebHookBot botConfig;

    @Autowired
    public void setBotConfig(WebHookBot botConfig) {
        this.botConfig = botConfig;
    }

    public void askQuestion(Long chatId, int step) {
        String question;

        switch (step) {
            case 1 -> question = "Сколько вам лет?";
            case 2 -> question = "Какой у вас вес в килограммах?";
            case 3 -> question = "Какой ваш рост в сантиметрах?";
            default -> question = "Спасибо за вашу информацию! Сейчас я подсчитаю вашу дневную норму калорий.";
        }

        botConfig.sendMessage(chatId, question);
    }
}
