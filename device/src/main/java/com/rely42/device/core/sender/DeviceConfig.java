package com.rely42.device.core.sender;

import com.rely42.shared.core.model.DeviceType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties("device")
@ConstructorBinding
public class DeviceConfig {

    private String id;
    private DeviceType type;

    public DeviceConfig(String id, DeviceType type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public DeviceType getType() {
        return type;
    }
}
