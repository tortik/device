package com.rely42.search.adapter.influx;

import com.rely42.search.core.model.SearchItem;
import org.influxdb.dto.QueryResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class SeriesMapper implements Function<QueryResult.Series, Set<SearchItem>> {
    @Override
    public Set<SearchItem> apply(QueryResult.Series series) {
        Set<SearchItem> result = new HashSet<>();
        List<String> columns = series.getColumns();
        List<List<Object>> values = series.getValues();

        for (int i = 0; i < values.size(); i++) {
            List<Object> fields = values.get(i);
            SearchItem searchItem = new SearchItem();
            for (int j = 0; j < columns.size(); j++) {
                searchItem.put(columns.get(j), fields.get(j));
            }
            result.add(searchItem);
        }
        return result;
    }
}
