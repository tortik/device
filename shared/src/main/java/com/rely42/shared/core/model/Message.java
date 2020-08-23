package com.rely42.shared.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Message {
    private String deviceId;
    private DeviceType deviceType;
    private String value;
    private long createdAt;
    private MetaData metaData;

    public Message(String deviceId, DeviceType deviceType, String value, long createdAt, MetaData metaData) {
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.value = value;
        this.createdAt = createdAt;
        this.metaData = metaData;
    }

    public Message(String deviceId, DeviceType deviceType, String value, long createdAt) {
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.value = value;
        this.createdAt = createdAt;
    }

    public Message() {
    }

    public String getDeviceId() {
        return deviceId;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public String getValue() {
        return value;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public MetaData getMetaData() {
        return metaData;
    }


    @Override
    public String toString() {
        return "Message{" +
                "deviceId='" + deviceId + '\'' +
                ", deviceType=" + deviceType +
                ", value='" + value + '\'' +
                ", createdAt=" + createdAt +
                ", metaData=" + metaData +
                '}';
    }

    public static class MetaData {
        private String producer;
        private String version;
        private String valueType;

        public MetaData setProducer(String producer) {
            this.producer = producer;
            return this;
        }

        public MetaData setVersion(String version) {
            this.version = version;
            return this;
        }

        public MetaData setValueType(String valueType) {
            this.valueType = valueType;
            return this;
        }

        @Override
        public String toString() {
            return "MetaData{" +
                    "producer='" + producer + '\'' +
                    ", version='" + version + '\'' +
                    ", valueType='" + valueType + '\'' +
                    '}';
        }
    }
}
