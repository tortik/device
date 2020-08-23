package com.rely42.search.adapter.http;

import com.rely42.search.core.SearchService;
import com.rely42.search.core.model.SearchItem;
import com.rely42.search.core.model.SearchRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
public class SearchController {
    private static final Logger log = LoggerFactory.getLogger(SearchController.class);

    private SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/api/v1/search")
    public ResponseEntity<Collection<SearchItem>> search(@RequestBody SearchRequest searchRequest) {
        log.debug("Receive search request {}", searchRequest);
        if (searchRequest.getDeviceType() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "deviceType mandatory");
        }

        Collection<SearchItem> result = searchService.search(searchRequest);
        log.debug("Search result {}", result);
        return ResponseEntity.ok(result);
    }

}
