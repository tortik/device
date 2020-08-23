package com.rely42.device.core.sender;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DeviceConfig.class)
public class SenderConfiguration {

    @Bean
    public ScheduledPublisher scheduledPublisher(SendService sendService, DeviceConfig deviceConfig) {
        return new ScheduledPublisher(sendService, deviceConfig);
    }

}
