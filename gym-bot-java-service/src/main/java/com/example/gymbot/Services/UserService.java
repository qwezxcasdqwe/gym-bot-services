package com.example.gymbot.Services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

import com.example.gymbot.Configurations.WebHookBot;
import com.example.gymbot.Entities.UserEntity;
import com.example.gymbot.Repositories.UserRepository;

@Service
public class UserService {

  private final UserRepository userRepository;

  private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUserData(Long chatId, Integer age, Double weight, Double height, Double calorieNorm) {
        UserEntity user = new UserEntity();
        user.setChatId(chatId);
        user.setAge(age);
        user.setWeight(weight);
        user.setHeight(height);
        user.setCalorieNorm(calorieNorm);
        if (userRepository.findById(user.getChatId()).isEmpty()) {
          log.info("Пользователь с chatId {} не найден. Добавление нового пользователя.", user.getChatId());
      } else {
          log.info("Пользователь с chatId {} уже существует.", user.getChatId());
      }
      
      userRepository.save(user);
    }
  
  public String getUserInformation(Long chatId){
      UserEntity user = userRepository.findById(chatId).orElse(null);
      if (user == null) {
        return "Пользователь не найден";
      }
      String answer = "Ваш возраст: " + user.getAge() + "\n" +
           "Ваш вес: " + user.getWeight() + " кг\n" +
           "Ваш рост: " + user.getHeight() + " см\n" +
           "Ваша дневная норма калорий: " + user.getCalorieNorm() + " ккал";
      return answer;
   } 
   
  public UserEntity takeUserById(Long chatId){
    UserEntity user = userRepository.findById(chatId).orElse(null);
    return user;
  }

  public Optional<UserEntity> findUserByIdService(Long chatid){
    Optional<UserEntity> user = userRepository.findById(chatid);
    return user;
  }
}
