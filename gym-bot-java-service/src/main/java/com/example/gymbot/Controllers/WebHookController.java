package com.example.gymbot.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.example.gymbot.Configurations.WebHookBot;

@RestController
public class WebHookController {

    private static final Logger log = LoggerFactory.getLogger(WebHookController.class);
    @Autowired
    private WebHookBot bot;

    @RequestMapping("/webhook")
    public void handleWebhook(@RequestBody Update update) {
        log.info("Получено обновление ");
        bot.onUpdateReceived(update);
    }
}
