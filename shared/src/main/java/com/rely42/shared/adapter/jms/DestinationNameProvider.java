package com.rely42.shared.adapter.jms;


import com.rely42.shared.core.model.DeviceType;
import org.apache.activemq.artemis.jms.client.ActiveMQTopic;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.util.StringUtils;

import javax.jms.JMSException;
import javax.jms.Topic;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DestinationNameProvider {

    private TopicNameConfig topicNameConfig;

    public DestinationNameProvider(TopicNameConfig topicNameConfig) {
        this.topicNameConfig = topicNameConfig;
    }

    public Topic destination(DeviceType deviceType) {
        return topicNameConfig.getTopic(deviceType);
    }

    @ConfigurationProperties("device")
    @ConstructorBinding
    public static class TopicNameConfig {
        Map<DeviceType, Topic> topic;

        public TopicNameConfig(Map<DeviceType, String> topic) {
            Set<DeviceType> allTypes = Arrays.stream(DeviceType.values()).collect(Collectors.toSet());
            boolean isValid = topic.entrySet().stream().allMatch(e -> allTypes.contains(e.getKey()) && !StringUtils.isEmpty(e.getValue()));
            if (!isValid) throw new IllegalStateException("Configuration missed for DeviceType topics");
            Map<DeviceType, Topic> destinations = new HashMap<>(topic.size());
            topic.forEach((key, value) -> destinations.put(key, new ActiveMQTopic(value)));
            this.topic = destinations;
        }


        public Topic getTopic(DeviceType deviceType) {
            return topic.get(deviceType);
        }

    }
}
