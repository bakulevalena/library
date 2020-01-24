package com.library.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StorageApplication {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "local");
        System.setProperty("user.timezone", "UTC");
        SpringApplication.run(StorageApplication.class, args);
    }

}
