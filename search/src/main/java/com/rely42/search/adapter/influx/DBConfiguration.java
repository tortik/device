package com.rely42.search.adapter.influx;

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
    QueryProvider queryProvider(){
        return new QueryProvider();
    }

    @Bean
    SeriesMapper seriesMapper(){
        return new SeriesMapper();
    }

    @Bean
    InfluxSearchService InfluxSearchService(InfluxDB influxDB, QueryProvider queryProvider, SeriesMapper seriesMapper){
        return new InfluxSearchService(influxDB, queryProvider, seriesMapper);
    }

    @Bean
    InfluxDB influxDb(@Value("${spring.influx.db}")String db, InfluxDbProperties properties, ObjectProvider<InfluxDbOkHttpClientBuilderProvider> builder) {
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
