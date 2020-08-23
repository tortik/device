package com.rely42.device.core.sender;


import com.rely42.shared.core.model.Message;
import io.micrometer.core.instrument.util.NamedThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class ScheduledPublisher implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger log = LoggerFactory.getLogger(ScheduledPublisher.class);

    private SendService sendService;
    private DeviceConfig deviceConfig;
    private ScheduledExecutorService scheduledExecutorService;

    public ScheduledPublisher(SendService sendService, DeviceConfig deviceConfig) {
        this.sendService = sendService;
        this.deviceConfig = deviceConfig;
        scheduledExecutorService = Executors.newScheduledThreadPool(1, new NamedThreadFactory("device"));
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("Start sending messages from device={} type={}", deviceConfig.getId(), deviceConfig.getType());
        scheduledExecutorService.scheduleAtFixedRate(command(), 0, 1, TimeUnit.SECONDS);

    }

    Runnable command() {
        return () -> {
            String value = String.valueOf(ThreadLocalRandom.current().nextLong(100));
            Message message = new Message(deviceConfig.getId(), deviceConfig.getType(), value, System.currentTimeMillis());
            sendService.send(message);
        };
    }


}
