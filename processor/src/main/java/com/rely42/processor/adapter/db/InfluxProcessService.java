package com.rely42.processor.adapter.db;

import com.rely42.processor.core.process.ProcessService;
import com.rely42.shared.core.model.Message;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class InfluxProcessService implements ProcessService {
    private static final Logger log = LoggerFactory.getLogger(InfluxProcessService.class);

    InfluxDB influxDB;

    public InfluxProcessService(InfluxDB influxDB) {
        this.influxDB = influxDB;
    }

    @Override
    public void process(Message message) {
        log.info("Saving data to influx {}", message);
        influxDB.write(Point.measurement(message.getDeviceType().name())
                .time(message.getCreatedAt(), TimeUnit.MILLISECONDS)
                .tag("deviceId", message.getDeviceId())
                .addField("value", message.getValue())
                .build());
    }
}
