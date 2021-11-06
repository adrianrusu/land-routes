package com.adrianr.landroutes.client;

import com.adrianr.landroutes.model.Country;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Files;
import java.util.Set;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CountriesClientTest {

    private static final String URL = "countries-url";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public void init() {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Test
    void givenUrlAndWrongResponseCode_whenGettingCountriesList_thenReturnEmptyList() {
        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restTemplate.getForEntity(URL, String.class)).thenReturn(ResponseEntity.badRequest().build());

        CountriesClient client = new CountriesClient(URL, objectMapper, restTemplate);
        assertEquals(emptyList(), client.getCountries());
    }

    @Test
    void givenUrlAndCorrectResponseCodeButInvalidJSON_whenGettingCountriesList_thenReturnEmptyList() {
        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restTemplate.getForEntity(URL, String.class)).thenReturn(ResponseEntity.ok("test"));

        CountriesClient client = new CountriesClient(URL, objectMapper, restTemplate);
        assertEquals(emptyList(), client.getCountries());
    }

    @Test
    void givenUrlAndCorrectResponseCodeAndJSON_whenGettingCountriesList_thenReturnCorrectList() throws Exception {
        RestTemplate restTemplate = mock(RestTemplate.class);
        String json = Files.readString(new ClassPathResource("countries.json").getFile().toPath());
        when(restTemplate.getForEntity(URL, String.class)).thenReturn(ResponseEntity.ok(json));

        CountriesClient client = new CountriesClient(URL, objectMapper, restTemplate);
        assertEquals(2, client.getCountries().size());
        assertEquals(Country.builder().code("ROU").borders(Set.of("BGR", "HUN", "MDA", "SRB", "UKR")).build(), client.getCountries().get(0));
        assertEquals(Country.builder().code("USA").borders(Set.of("CAN", "MEX")).build(), client.getCountries().get(1));
    }

}
