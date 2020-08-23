package com.rely42.device.adapter.jms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rely42.device.core.sender.SendService;
import com.rely42.shared.adapter.jms.DestinationNameProvider;
import com.rely42.shared.core.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;

public class SendJmsService implements SendService {

    private static final Logger log = LoggerFactory.getLogger(SendJmsService.class);

    private ObjectMapper objectMapper;
    private JmsTemplate jmsTemplate;
    private DestinationNameProvider destinationNameProvider;

    public SendJmsService(ObjectMapper objectMapper, JmsTemplate jmsTemplate, DestinationNameProvider destinationNameProvider) {
        this.objectMapper = objectMapper;
        this.jmsTemplate = jmsTemplate;
        this.destinationNameProvider = destinationNameProvider;
    }

    public void send(Message message) {
        Destination destination = destinationNameProvider.destination(message.getDeviceType());
        String jsonMessage = null;
        try {
            jsonMessage = objectMapper.writeValueAsString(message);
            log.debug("Sending via Jms Message value={}", message.getValue());
            jmsTemplate.setPubSubDomain(true);
            jmsTemplate.convertAndSend(destination, jsonMessage);
        } catch (Exception e) {
            log.warn("Can't convert message {} to json, wouldn't send it. Ex:{}", message, e);
        }

    }
}
