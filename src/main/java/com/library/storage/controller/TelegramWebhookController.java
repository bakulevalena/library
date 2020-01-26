package com.library.storage.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.storage.dto.Telegram;
import com.library.storage.service.TelegramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TelegramWebhookController {

    private final TelegramService telegramService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${version}")
    private String version;

    @PostMapping(value = "/api/webhook/telegram")
    public ResponseEntity processEvent(@RequestBody String raw) {
        log.debug("Got event from Telegram: Raw = {}", raw);
        try {
            Telegram.Update message = null;
            message = objectMapper.readValue(raw, Telegram.Update.class);
            telegramService.createResponse(message.getMessage().getChat().getId(), message.getMessage().getText(), message.getMessage().getEntities());
        } catch (IOException e) {
            log.warn("Tried to process message - result: ", e);
        }
        return ResponseEntity.ok().build();
    }


}
