package com.rely42.search.adapter.http;

import com.rely42.search.core.SearchService;
import com.rely42.search.core.model.SearchItem;
import com.rely42.search.core.model.SearchRequest;
import org.influxdb.InfluxDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.influx.InfluxDbProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ContextConfiguration(classes = {SearchController.class, SearchControllerTest.TestConfig.class})
@WebMvcTest
class SearchControllerTest {

    @MockBean
    SearchService searchService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("should return 400 error with empty body")
    void badRequestWithEmptyBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/search")
                .content("").contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(400));

    }

    @Test
    @DisplayName("should return 415 error with not application/json contentType")
    void unsupportedMediaTypeWithEmptyBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/search")
                .content("").contentType(MediaType.TEXT_PLAIN)).andExpect(status().is(415));

    }

    @Test
    @DisplayName("should return 400 without deviceType")
    void shouldHitSearchService() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/search")
                .content("{}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        verify(searchService, never()).search(any(SearchRequest.class));
    }

    @Test
    @DisplayName("should return 200 with response")
    void shouldHitSearchServiceWithResponse() throws Exception {
        SearchItem searchItem = new SearchItem();
        searchItem.put("time", "2020-08-23T17:31:24.614Z");
        searchItem.put("deviceId", "test");
        searchItem.put("value", "84");
        when(searchService.search(any(SearchRequest.class))).thenReturn(Collections.singleton(searchItem));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/search")
                .content("{\n" +
                        "    \"deviceType\": \"THERMOSTAT\",\n" +
                        "    \"deviceIds\":[\"c5affe69-73f9-409d-b92a-50ab55c465c6\"],\n" +
                        "    \"from\": \"2020-08-23T17:30:00Z\",\n" +
                        "    \"to\": \"2020-08-23T17:35:00Z\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        verify(searchService).search(any(SearchRequest.class));
        assertEquals("[{\"entries\":{\"time\":\"2020-08-23T17:31:24.614Z\",\"deviceId\":\"test\",\"value\":\"84\"}}]",
                result.getResponse().getContentAsString());
    }

    @TestConfiguration
    public static class TestConfig {

        @Bean
        public InfluxDbProperties influxDbProperties() {
            return new InfluxDbProperties();
        }

    }


}