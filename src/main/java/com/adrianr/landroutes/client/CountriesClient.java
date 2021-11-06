package com.adrianr.landroutes.client;

import com.adrianr.landroutes.model.Country;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class CountriesClient {

    private final String routesUrl;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public CountriesClient(@Value("${routesUrl}") String routesUrl, ObjectMapper objectMapper, RestTemplate restTemplate) {
        this.routesUrl = routesUrl;
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    public List<Country> getCountries() {
        log.info("Loading countries from: {}", routesUrl);

        ResponseEntity<String> response = restTemplate.getForEntity(routesUrl, String.class);

        if (200 == response.getStatusCodeValue()) {
            String json = response.getBody();

            try {
                return objectMapper.readValue(json, new TypeReference<>() {});
            } catch (JsonProcessingException e) {
                log.error(e.getMessage(), e);
            }
        }
        return Collections.emptyList();
    }

}
