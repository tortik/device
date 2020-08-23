package com.rely42.search.adapter.influx;

import com.rely42.search.core.model.SearchRequest;
import com.rely42.shared.core.model.DeviceType;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

class QueryProviderTest {

    QueryProvider underTest = new QueryProvider();

    @Test
    void shouldGetBaseThermostatQuery() {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setDeviceType(DeviceType.THERMOSTAT);

        String query = underTest.get(searchRequest).getCommand();

        assertEquals("SELECT * from THERMOSTAT WHERE 1 = 1", query);
    }

    @Test
    void shouldGetBaseCarFuelQuery() {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setDeviceType(DeviceType.CAR_FUEL_READING);

        String query = underTest.get(searchRequest).getCommand();

        assertEquals("SELECT * from CAR_FUEL_READING WHERE 1 = 1", query);
    }

    @Test
    void shouldGetBaseHeartRateQuery() {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setDeviceType(DeviceType.HEART_RATE_METER);

        String query = underTest.get(searchRequest).getCommand();

        assertEquals("SELECT * from HEART_RATE_METER WHERE 1 = 1", query);
    }

    @Test
    void shouldAddDeviceIdsIntoQuery() {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setDeviceType(DeviceType.THERMOSTAT);
        searchRequest.setDeviceIds(new String[]{"test1", "test2"});

        String query = underTest.get(searchRequest).getCommand();

        assertEquals("SELECT * from THERMOSTAT WHERE 1 = 1 and (deviceId='test1' or deviceId='test2')", query);
    }

    @Test
    void shouldAddFromToIntoQuery() {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setDeviceType(DeviceType.THERMOSTAT);
        searchRequest.setFrom(OffsetDateTime.ofInstant(Instant.ofEpochSecond(100000000), ZoneOffset.UTC));
        searchRequest.setTo(OffsetDateTime.ofInstant(Instant.ofEpochSecond(200000000), ZoneOffset.UTC));

        String query = underTest.get(searchRequest).getCommand();

        assertEquals("SELECT * from THERMOSTAT WHERE 1 = 1 and time >= '1973-03-03T09:46:40.000Z' and time <= '1976-05-03T19:33:20.000Z'", query);
    }

}