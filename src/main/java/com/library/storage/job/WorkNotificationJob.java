package com.library.storage.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WorkNotificationJob {
    private static final Logger logger = LoggerFactory.getLogger(WorkNotificationJob.class);

    @Scheduled(fixedDelayString = "${job.notification.delay}")
    public void scheduleFixedRateWithInitialDelayTask() {
        logger.debug("App is alive");
    }
}
