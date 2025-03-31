package com.example.gymbot.Configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.annotation.PostConstruct;

@Configuration
public class WebHookInstaller {
   private final String botToken;

  //  private final String webHookUrl;
   
  // public WebHookInstaller(@Value("${telegram.bot.token}") String botToken, @Value("${telegram.webhook.url}") String webHookUrl){
  //   this.botToken=botToken; 
  //   this.webHookUrl=webHookUrl;
  // }

  private String webHookUrl="https://qfs8lz-91-224-99-241.ru.tuna.am/webhook";

  public WebHookInstaller(@Value("${telegram.bot.token}") String botToken){
    this.botToken=botToken;
  }

  @PostConstruct
  public void init(){
    System.out.println(webHookUrl);
    setWebhook();
  }
  
  public void setWebhook(){
      String url = UriComponentsBuilder.fromHttpUrl("https://api.telegram.org/bot" + botToken + "/setWebhook")
       .queryParam("url", webHookUrl)
       .toUriString();
      try {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        if (response != null && response.contains("\"ok\":true")) {
            System.out.println("Webhook successfully set.");
        } else {
            System.out.println("Error setting webhook: " + response);
        }
      } catch (Exception e) {
          e.printStackTrace();
          System.out.println("Error during webhook setup.");
      }
  }
  
}
