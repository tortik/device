package com.rely42.device.adapter.jms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rely42.device.core.sender.SendService;
import com.rely42.shared.adapter.jms.DestinationNameProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;

@EnableConfigurationProperties(DestinationNameProvider.TopicNameConfig.class)
public class JmsConfiguration {

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    DestinationNameProvider destinationNameProvider(DestinationNameProvider.TopicNameConfig topicNameConfig) {
        return new DestinationNameProvider(topicNameConfig);
    }

    @Bean
    SendService sendJmsService(ObjectMapper objectMapper, DestinationNameProvider destinationNameProvider, JmsTemplate jmsTemplate) {
        return new SendJmsService(objectMapper, jmsTemplate, destinationNameProvider);
    }
}
