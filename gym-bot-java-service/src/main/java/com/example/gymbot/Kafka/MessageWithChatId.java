package com.example.gymbot.Kafka;

public class MessageWithChatId {
  private Long chatId;
  private String message;

  // Конструктор по умолчанию
  public MessageWithChatId() {}

  public MessageWithChatId(Long chatId, String message) {
      this.chatId = chatId;
      this.message = message;
  }

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

