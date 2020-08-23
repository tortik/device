package com.rely42.search.core.model;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SearchItem {

    private Map<String, Object> pair = new HashMap<>();

    public SearchItem() {
    }

    SearchItem(Map<String, Object> pair) {
        this.pair = pair;
    }

    @JsonGetter
    public Map<String, Object> entries() {
        return pair;
    }

    public void put(String key, Object value) {
        pair.put(key, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchItem that = (SearchItem) o;
        return Objects.equals(pair, that.pair);
    }

    @Override
    public int hashCode() {

        return Objects.hash(pair);
    }
}
