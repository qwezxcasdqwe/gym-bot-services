package com.example.gymbot.Entities;

public class UserInfo {

  private int age;
  private double weight;
  private double height;
  private double calorieNorm;

  public double getCalorieNorm() {
    return calorieNorm;
}

public void setCalorieNorm(double calorieNorm) {
    this.calorieNorm = calorieNorm;
}

public Integer getAge() {
      return age;
  }

  public void setAge(Integer age) {
      this.age = age;
  }

  public double getWeight() {
      return weight;
  }

  public void setWeight(double weight) {
      this.weight = weight;
  }

  public double getHeight() {
      return height;
  }

  public void setHeight(double height) {
      this.height = height;
  }

  
}
