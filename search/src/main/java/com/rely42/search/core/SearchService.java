package com.rely42.search.core;

import com.rely42.search.core.model.SearchItem;
import com.rely42.search.core.model.SearchRequest;

import java.util.Collection;

public interface SearchService {

    Collection<SearchItem> search(SearchRequest searchRequest);

}
