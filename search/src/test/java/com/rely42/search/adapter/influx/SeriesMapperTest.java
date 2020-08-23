package com.rely42.search.adapter.influx;

import com.rely42.search.core.model.SearchItem;
import org.influxdb.dto.QueryResult;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SeriesMapperTest {

    SeriesMapper underTest = new SeriesMapper();

    @Test
    void testMapper() {
        QueryResult.Series series = new QueryResult.Series();
        series.setColumns(Arrays.asList("time", "deviceId", "value"));
        series.setValues(Collections.singletonList(Arrays.asList("2020-08-23T17:31:24.614Z", "test", "84")));
        Set<SearchItem> result = underTest.apply(series);

        assertEquals(1, result.size());

        SearchItem searchItem = testSearchItem();
        assertEquals(searchItem, result.iterator().next());
    }

    private SearchItem testSearchItem() {
        SearchItem searchItem = new SearchItem();
        searchItem.put("time", "2020-08-23T17:31:24.614Z");
        searchItem.put("deviceId", "test");
        searchItem.put("value", "84");
        return searchItem;
    }
}