package com.rely42.shared.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum DeviceType {

    THERMOSTAT, HEART_RATE_METER, CAR_FUEL_READING;

    @JsonCreator
    public DeviceType fromString(String name){
        return DeviceType.valueOf(name);
    }
}
