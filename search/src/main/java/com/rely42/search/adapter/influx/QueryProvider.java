package com.rely42.search.adapter.influx;

import com.rely42.search.core.model.SearchRequest;
import com.rely42.shared.core.model.DeviceType;
import org.influxdb.dto.Query;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class QueryProvider {

    Map<DeviceType, String> baseQueries = new HashMap<>();
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public QueryProvider() {
        baseQueries.put(DeviceType.THERMOSTAT, "SELECT * from THERMOSTAT WHERE 1 = 1");
        baseQueries.put(DeviceType.CAR_FUEL_READING, "SELECT * from CAR_FUEL_READING WHERE 1 = 1");
        baseQueries.put(DeviceType.HEART_RATE_METER, "SELECT * from HEART_RATE_METER WHERE 1 = 1");
    }

    public Query get(SearchRequest searchRequest) {
        String baseQuery = baseQueries.get(searchRequest.getDeviceType());
        if (searchRequest.getDeviceIds().length > 0) {
            baseQuery = String.format(baseQuery + " and (deviceId='%s'", searchRequest.getDeviceIds()[0]);
            for (int i = 1; i < searchRequest.getDeviceIds().length; i++) {
                baseQuery = String.format(baseQuery + " or deviceId='%s'", searchRequest.getDeviceIds()[i]);
            }
            baseQuery += ")";
        }
        if (searchRequest.getFrom() != null) {
            baseQuery = String.format(baseQuery + " and time >= '%s'", dateTimeFormatter.format(searchRequest.getFrom()));
        }
        if (searchRequest.getTo() != null) {
            baseQuery = String.format(baseQuery + " and time <= '%s'", dateTimeFormatter.format(searchRequest.getTo()));
        }
        return new Query(baseQuery);
    }
}
