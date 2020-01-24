package com.library.storage.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class Telegram {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Update {
        @JsonProperty("update_id")
        private Long updateId;
        private Message message;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class User {
        private Long id;
        @JsonProperty("is_bot")
        private Boolean isBot;
        @JsonProperty("first_name")
        private String firstName;
        @JsonProperty("last_name")
        private String lastName;
        private String username;
        @JsonProperty("language_code")
        private String languageCode;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Chat {
        private Long id;
        @JsonProperty("first_name")
        private String firstName;
        @JsonProperty("last_name")
        private String lastName;
        private String username;
        private String type;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Entity {
        private Integer offset;
        private Integer length;
        private String type;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Message {
        @JsonProperty("message_id")
        private Long messageId;
        private User from;
        private Chat chat;
        private String date;
        private String text;
        private List<Entity> entities = new ArrayList<>();
    }

    @Data
    public static class Webhook {
        private String url;
    }
}
