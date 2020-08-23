package com.rely42.search.adapter.influx;

import com.rely42.search.core.model.SearchItem;
import com.rely42.search.core.model.SearchRequest;
import com.rely42.shared.core.model.DeviceType;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class InfluxSearchServiceTest {

    InfluxSearchService underTest;
    @Mock
    InfluxDB influxDB;

    @BeforeEach
    public void setUp() {
        underTest = new InfluxSearchService(influxDB, new QueryProvider(), new SeriesMapper());
    }

    @Test
    void search() {
        SearchRequest searchRequest = new SearchRequest(DeviceType.THERMOSTAT);
        when(influxDB.query(any(Query.class))).thenReturn(queryResult());

        Collection<SearchItem> result = underTest.search(searchRequest);

        assertEquals(testSearchItem(), result.iterator().next());
    }

    private QueryResult queryResult() {
        QueryResult qr = new QueryResult();
        QueryResult.Result result = new QueryResult.Result();
        QueryResult.Series series = new QueryResult.Series();
        series.setColumns(Arrays.asList("time", "deviceId", "value"));
        series.setValues(Collections.singletonList(Arrays.asList("2020-08-23T17:31:24.614Z", "test", "84")));
        result.setSeries(Collections.singletonList(series));
        qr.setResults(Collections.singletonList(result));
        return qr;
    }

    private SearchItem testSearchItem() {
        SearchItem searchItem = new SearchItem();
        searchItem.put("time", "2020-08-23T17:31:24.614Z");
        searchItem.put("deviceId", "test");
        searchItem.put("value", "84");
        return searchItem;
    }
}