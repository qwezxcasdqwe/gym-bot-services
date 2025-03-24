package com.example.gymbot.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    private Long chatId;

    public Long getChatId() {
      return chatId;
    }
    public void setChatId(Long chatId) {
      this.chatId = chatId;
    }
    private int age;
    public int getAge() {
      return age;
    }
    public void setAge(int age) {
      this.age = age;
    }
    private Double weight;
    public Double getWeight() {
      return weight;
    }
    public void setWeight(Double weight) {
      this.weight = weight;
    }
    private Double height;
    public Double getHeight() {
      return height;
    }
    public void setHeight(Double height) {
      this.height = height;
    }
    private String activityLevel;
    public String getActivityLevel() {
      return activityLevel;
    }
    public void setActivityLevel(String activityLevel) {
      this.activityLevel = activityLevel;
    }
    private Double calorieNorm;
    public Double getCalorieNorm() {
      return calorieNorm;
    }
    public void setCalorieNorm(Double calorieNorm) {
      this.calorieNorm = calorieNorm;
    }
}
