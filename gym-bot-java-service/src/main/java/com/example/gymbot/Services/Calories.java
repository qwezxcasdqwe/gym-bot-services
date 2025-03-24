package com.example.gymbot.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gymbot.Configurations.WebHookBot;
import com.example.gymbot.Entities.UserInfo;

@Service
public class Calories {
  private  WebHookBot botConfig;
        @Autowired
        public Calories(WebHookBot botConfig) {
            this.botConfig = botConfig;
        }
      
      public void calculateCalories(UserInfo userInfo, Long chatId) {
        // Примерная формула для расчета калорий (Mifflin-St Jeor equation)
        double bmr = 10 * userInfo.getWeight() + 6.25 * userInfo.getHeight() - 5 * userInfo.getAge() + 5;  // Для мужчин
        double calories = bmr * 1.55;  // Умеренная активность

        // Выводим результат
        botConfig.sendMessage(chatId, "Ваша дневная норма калорий: " + calories + " ккал.");
    }
     public double caloriesReturner(UserInfo userInfo, Long chatId) {
      // Примерная формула для расчета калорий (Mifflin-St Jeor equation)
      double bmr = 10 * userInfo.getWeight() + 6.25 * userInfo.getHeight() - 5 * userInfo.getAge() + 5;  // Для мужчин
      double calories = bmr * 1.55;  // Умеренная активность
      return calories;  
}
}
