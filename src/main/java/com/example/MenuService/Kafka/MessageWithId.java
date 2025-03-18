package com.example.MenuService.Kafka;

public class MessageWithId {
  private Long chatId;
  private String message;

  public MessageWithId(Long chatId, String message) {
      this.chatId = chatId;
      this.message = message;
  }

  // getters and setters
  public Long getChatId() {
      return chatId;
  }

  public void setChatId(Long chatId) {
      this.chatId = chatId;
  }

  public String getMessage() {
      return message;
  }

  public void setMessage(String message) {
      this.message = message;
  }
  
}
