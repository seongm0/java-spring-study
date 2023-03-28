package com.example.app.modules.poll;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class PollScheduler {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

//    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        log.trace("trace log = {}", dateFormat.format(new Date()));
        log.debug("debug log = {}", dateFormat.format(new Date()));
        log.info("info log = {}", dateFormat.format(new Date()));
        log.warn("warn log = {}", dateFormat.format(new Date()));
        log.error("error log = {}", dateFormat.format(new Date()));
    }
}
