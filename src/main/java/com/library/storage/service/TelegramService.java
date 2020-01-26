package com.library.storage.service;

import com.library.storage.config.TelegramConfig;
import com.library.storage.dto.Telegram;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TelegramService {
    private static final String SET_WEBHOOK_ACTION = "/setWebhook";
    private static final String DELETE_WEBHOOK_ACTION = "/deleteWebhook";
    private static final String SEND_MESSAGE_ACTION = "/sendMessage";
    private static final String BOOK_COMMAND = "/books";
    private static final String VERSION_COMMAND = "/version";
    private static final String AUTHORS_COMMAND = "/authors";

    private final RestTemplate restTemplate;
    private final TelegramConfig telegramConfig;
    private final BookServices bookServices;
    private final AuthorServices authorServices;

    @Value("${external-url}")
    private String externalUrl;
    @Value("${version}")
    private String version;


    @PostConstruct
    public void setWebhook() {
        String url = buildUrl(SET_WEBHOOK_ACTION);
        Telegram.Webhook dto = new Telegram.Webhook();
        dto.setUrl(externalUrl);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<Telegram.Webhook> request = new HttpEntity<>(dto, headers);
        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request, String.class);
            log.info("Tried to set webhook - result: {}", responseEntity);
        } catch (RestClientException ex) {
            log.warn("Tried to set webhook - result: ", ex);
        }
    }

    @PreDestroy
    public void deleteWebhook() {
        String url = buildUrl(DELETE_WEBHOOK_ACTION);
        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, null, String.class);
            log.info("Tried to delete webhook - result: {}", responseEntity);
        } catch (RestClientException ex) {
            log.warn("Tried to delete webhook - result: ", ex);
        }
    }


    public void sendMessage(Long chat, String text) {
        String url = buildSendUrl(SEND_MESSAGE_ACTION, chat, text);
        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, null, String.class);
            log.info("Tried to send message - result: {}", responseEntity);
        } catch (RestClientException ex) {
            log.warn("Tried to send message - result: ", ex);
        }
    }

    private String buildSendUrl(String action, Long chat, String text) {
        return new StringBuilder()
                .append(buildUrl(action))
                .append("?chat_id=")
                .append(chat)
                .append("&text=")
                .append(text)
                .toString();
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    private String buildUrl(String action) {
        return new StringBuilder()
                .append(telegramConfig.getApiUrl())
                .append(telegramConfig.getToken())
                .append(action)
                .toString();
    }

    public void createResponse(Long chat, String text, List<Telegram.Entity> entities) {
        log.debug("Got message from Telegram: Text = {}, chat = {}", text, chat);
        if (entities.isEmpty()) {
            sendMessage(chat, "Use command");
            return;
        }
        for (Telegram.Entity entity : entities) {
            String task = text.substring(entity.getOffset(), entity.getLength() + entity.getOffset()).toLowerCase();
            switch (task) {
                case VERSION_COMMAND:
                    sendMessage(chat, version);
                    break;
                case BOOK_COMMAND:
                    sendMessage(chat, bookServices.getAllBooksString());
                    break;
                case AUTHORS_COMMAND:
                    sendMessage(chat, authorServices.getAllAuthorsString());
                    break;
                default:
                    sendMessage(chat, "Use proper command");
            }
        }
    }


}
