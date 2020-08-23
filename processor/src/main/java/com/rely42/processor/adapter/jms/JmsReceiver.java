package com.rely42.processor.adapter.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rely42.processor.core.process.ProcessService;
import com.rely42.shared.core.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.MessageListener;

public class JmsReceiver implements MessageListener {

    private static final Logger log = LoggerFactory.getLogger(JmsReceiver.class);

    private ObjectMapper objectMapper;
    private ProcessService processService;

    public JmsReceiver(ObjectMapper objectMapper, ProcessService processService) {
        this.objectMapper = objectMapper;
        this.processService = processService;
    }

    @Override
    public void onMessage(javax.jms.Message message) {
        try {
            log.debug("Received message {}", message);
            Message internalMessage = objectMapper.readValue(message.getBody(String.class), Message.class);
            processService.process(internalMessage);
        } catch (JsonProcessingException e) {
            log.error("Error on serializing message to pojo. Message={}. Ex={}", message,e );
        } catch (JMSException e) {
            log.error("Error on getting body from Message={}. Ex={}", message, e);
        }
    }
}
