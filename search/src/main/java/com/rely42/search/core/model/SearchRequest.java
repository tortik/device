package com.rely42.search.core.model;

import com.rely42.shared.core.model.DeviceType;

import java.time.OffsetDateTime;

public class SearchRequest {

    private DeviceType deviceType;
    private String[] deviceIds = new String[]{};
    private AggregateFunction[] aggregates;
    private OffsetDateTime from;
    private OffsetDateTime to;

    public SearchRequest() {
    }

    public SearchRequest(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String[] getDeviceIds() {
        return deviceIds;
    }

    public SearchRequest setDeviceIds(String[] deviceIds) {
        this.deviceIds = deviceIds;
        return this;
    }

    public AggregateFunction[] getAggregates() {
        return aggregates;
    }

    public SearchRequest setAggregates(AggregateFunction[] aggregates) {
        this.aggregates = aggregates;
        return this;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public SearchRequest setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
        return this;
    }

    public OffsetDateTime getFrom() {
        return from;
    }

    public void setFrom(OffsetDateTime from) {
        this.from = from;
    }

    public OffsetDateTime getTo() {
        return to;
    }

    public void setTo(OffsetDateTime to) {
        this.to = to;
    }

    public enum AggregateFunction {
        MAX, MIN, AVG, MEDIAN
    }

}
