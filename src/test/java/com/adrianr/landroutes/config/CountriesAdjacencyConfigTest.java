package com.adrianr.landroutes.config;

import com.adrianr.landroutes.client.CountriesClient;
import com.adrianr.landroutes.model.Country;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CountriesAdjacencyConfigTest {

    @Test
    void givenACountryList_whenCallingAdjacencyList_thenConstructTheCorrectModel() {
        CountriesAdjacencyConfig countriesAdjacencyConfig = new CountriesAdjacencyConfig();
        CountriesClient client = mock(CountriesClient.class);

        List<Country> countries = asList(Country.builder().code("ROU").borders(Set.of("BGN", "SRB")).build(),
                Country.builder().code("BGN").borders(Set.of("ROU", "BGN")).build(),
                Country.builder().code("SRB").borders(Set.of("ROU", "BGN")).build());

        when(client.getCountries()).thenReturn(countries);

        Map<String, Set<String>> adjacencyList = countriesAdjacencyConfig.countriesAdjacencyList(client);
        assertEquals(countries.size(), adjacencyList.size());
        countries.forEach(country -> assertEquals(country.getBorders(), adjacencyList.get(country.getCode())));
    }

}
