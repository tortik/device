package com.rely42.search.adapter.influx;

import com.rely42.search.core.SearchService;
import com.rely42.search.core.model.SearchItem;
import com.rely42.search.core.model.SearchRequest;
import org.influxdb.InfluxDB;
import org.influxdb.dto.QueryResult;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class InfluxSearchService implements SearchService {

    private InfluxDB influxDB;
    private QueryProvider queryProvider;
    private SeriesMapper seriesMapper;

    public InfluxSearchService(InfluxDB influxDB, QueryProvider queryProvider, SeriesMapper seriesMapper) {
        this.influxDB = influxDB;
        this.queryProvider = queryProvider;
        this.seriesMapper = seriesMapper;
    }

    @Override
    public Collection<SearchItem> search(SearchRequest searchRequest) {
        QueryResult qr = influxDB.query(queryProvider.get(searchRequest));
        return qr.getResults().stream()
                .map(QueryResult.Result::getSeries).filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .map(seriesMapper)
                .flatMap(Set::stream).collect(Collectors.toSet());
    }
}
