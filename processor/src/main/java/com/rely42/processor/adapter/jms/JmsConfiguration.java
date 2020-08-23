package com.rely42.processor.adapter.jms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rely42.processor.core.process.ProcessService;
import com.rely42.shared.adapter.jms.DestinationNameProvider;
import com.rely42.shared.core.model.DeviceType;
import org.apache.activemq.artemis.jms.client.ActiveMQJMSConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.support.converter.SimpleMessageConverter;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

@EnableConfigurationProperties({DestinationNameProvider.TopicNameConfig.class, ArtemisProperties.class})
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
    JmsReceiver sendJmsService(ObjectMapper objectMapper, ProcessService processService) {
        return new JmsReceiver(objectMapper, processService);
    }

    @Bean
    public MessageListenerContainer listenerContainer1(@Qualifier("connectionFactory") ConnectionFactory connectionFactory,
                                                       JmsReceiver jmsReceiver, DestinationNameProvider destinationNameProvider,
                                                       @Value("${device.type}") DeviceType deviceType) {
        DefaultMessageListenerContainer defaultMessageListenerContainer =
                new DefaultMessageListenerContainer();
        defaultMessageListenerContainer.setSubscriptionDurable(false);
        defaultMessageListenerContainer.setPubSubDomain(true);
        defaultMessageListenerContainer.setConnectionFactory(connectionFactory);
        defaultMessageListenerContainer.setDestination(destinationNameProvider.destination(deviceType));
        defaultMessageListenerContainer.setMessageListener(jmsReceiver);
        defaultMessageListenerContainer.setSessionAcknowledgeMode(1);
        defaultMessageListenerContainer.setSubscriptionName("mySub");
        defaultMessageListenerContainer.setMessageConverter(new SimpleMessageConverter());
        return defaultMessageListenerContainer;

    }

    @Bean("connectionFactory")
    public ConnectionFactory activeMQJMSConnectionFactory(ArtemisProperties artemisProperties) throws JMSException {
        String uri = "tcp://" + artemisProperties.getHost() + ":" + +artemisProperties.getPort();
        return new ActiveMQJMSConnectionFactory(uri, artemisProperties.getUser(),
                artemisProperties.getPassword());
    }
}
