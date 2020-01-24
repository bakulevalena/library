package com.library.storage.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.storage.dto.Telegram;
import com.library.storage.service.AuthorServices;
import com.library.storage.service.BookServices;
import com.library.storage.service.TelegramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TelegramWebhookController {
    public static final String BOOK_COMMAND = "/books";
    public static final String VERSION_COMMAND = "/version";
    public static final String AUTHORS_COMMAND = "/authors";
    private final TelegramService telegramService;
    private final BookServices bookServices;
    private final AuthorServices authorServices;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${version}")
    private String version;

    @PostMapping(value = "/api/webhook/telegram")
    public ResponseEntity processEvent(@RequestBody String raw) {
        log.debug("Got event from Telegram: Raw = {}", raw);
        try {
            Telegram.Update message = null;
            message = objectMapper.readValue(raw, Telegram.Update.class);
            createResponse(message.getMessage().getChat().getId(), message.getMessage().getText(), message.getMessage().getEntities());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }

    public void createResponse(Long chat, String text, List<Telegram.Entity> entities) {
        if (entities.isEmpty()) {
            telegramService.sendMessage(chat, "Use command");
            return;
        }
        for (Telegram.Entity entity : entities) {
            String task = text.substring(entity.getOffset(), entity.getLength() + entity.getOffset()).toLowerCase();
            switch (task) {
                case VERSION_COMMAND:
                    telegramService.sendMessage(chat, version);
                    break;
                case BOOK_COMMAND:
                    telegramService.sendMessage(chat, bookServices.getAllBooksString());
                    break;
                case AUTHORS_COMMAND:
                    telegramService.sendMessage(chat, authorServices.getAllAuthorsString());
                    break;
                default:
                    telegramService.sendMessage(chat, "Use proper command");
            }
        }
    }

}
