package com.library.storage.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WorkNotificationJob {

    @Scheduled(fixedDelayString = "${job.notification.delay}")
    public void scheduleFixedRateWithInitialDelayTask() {
        log.debug("App is alive");
    }
}
