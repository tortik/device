package com.rely42.processor.adapter.db;

import okhttp3.OkHttpClient;
import org.influxdb.InfluxDB;
import org.influxdb.impl.InfluxDBImpl;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.influx.InfluxDbOkHttpClientBuilderProvider;
import org.springframework.boot.autoconfigure.influx.InfluxDbProperties;
import org.springframework.context.annotation.Bean;

public class DBConfiguration {

    @Bean
    InfluxProcessService influxProcessService(InfluxDB influxDB){
        return new InfluxProcessService(influxDB);
    }

    @Bean
    public InfluxDB influxDb(@Value("${spring.influx.db}")String db, InfluxDbProperties properties, ObjectProvider<InfluxDbOkHttpClientBuilderProvider> builder) {
        InfluxDB influxDB = new InfluxDBImpl(properties.getUrl(), properties.getUser(), properties.getPassword(),
                determineBuilder(builder.getIfAvailable()));
        influxDB.setDatabase(db);
        influxDB.setRetentionPolicy("autogen");
        return influxDB;
    }

    private static OkHttpClient.Builder determineBuilder(InfluxDbOkHttpClientBuilderProvider builder) {
        return builder != null ? builder.get() : new OkHttpClient.Builder();
    }
}
