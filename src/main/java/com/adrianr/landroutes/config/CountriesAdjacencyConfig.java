package com.adrianr.landroutes.config;

import com.adrianr.landroutes.client.CountriesClient;
import com.adrianr.landroutes.model.Country;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CountriesAdjacencyConfig {

    @Bean
    public Map<String, Set<String>> countriesAdjacencyList(CountriesClient countriesClient) {
        return loadCountries(countriesClient.getCountries());
    }

    private Map<String, Set<String>> loadCountries(List<Country> countries) {
        return countries.stream().collect(Collectors.toMap(Country::getCode, Country::getBorders));
    }

}
