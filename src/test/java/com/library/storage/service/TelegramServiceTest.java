package com.library.storage.service;

import com.library.storage.dto.Telegram;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TelegramServiceTest {

    private static final long CHAT = 1234L;
    private static final String TEXT = "\\books";

    @Autowired
    private TelegramService telegramService;
    @MockBean
    private RestTemplate restTemplate;
    @MockBean
    private AuthorServices authorServices;
    @MockBean
    private BookServices bookServices;

    @Test(expected = RuntimeException.class)
    public void throwException() {
        telegramService.throwException();
    }

    @Test
    public void setWebhook() {
        telegramService.hashCode();
        //telegramService.setWebhook(); - don't try to call it, because of @PostConstruct (called by Spring)
        Mockito.verify(restTemplate, Mockito.times(1))
                .postForEntity(Mockito.contains("/setWebhook"), any(), any());
    }

    @Test
    public void deleteWebhook() {
        telegramService.deleteWebhook();
        Mockito.verify(restTemplate, Mockito.times(1))
                .postForEntity(Mockito.contains("/deleteWebhook"), any(), any());
    }

    @Test
    public void sendMessage() {
        telegramService.sendMessage(CHAT, TEXT);
    }

    @Test
    public void processMessage() {
        List<Telegram.Entity> entities = new ArrayList<>();
        Telegram.Entity entity = new Telegram.Entity();
        entity.setLength(6);
        entity.setOffset(0);
        entity.setType("bot_command");
        entities.add(entity);
        telegramService.processMessage(CHAT, TEXT, entities);
    }
}