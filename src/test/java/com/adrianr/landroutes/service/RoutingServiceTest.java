package com.adrianr.landroutes.service;

import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RoutingServiceTest {

    @Test
    void givenNotExistingOrigin_whenCallingGetRoute_thenThrowResponseStatusException() {
        RoutingService routingService = new RoutingService(getAdjacencyList());

        assertThrows(ResponseStatusException.class, () -> routingService.getRoutes("ROU", "1"));
    }

    @Test
    void givenNotExistingDestination_whenCallingGetRoute_thenThrowResponseStatusException() {
        RoutingService routingService = new RoutingService(getAdjacencyList());

        assertThrows(ResponseStatusException.class, () -> routingService.getRoutes("1", "GRE"));
    }

    @Test
    void givenNoPath_whenCallingGetRoute_thenThrowResponseStatusException() {
        RoutingService routingService = new RoutingService(getAdjacencyList());

        assertThrows(ResponseStatusException.class, () -> routingService.getRoutes("2", "10"));
    }

    @Test
    void givenExistingPath_whenCallingGetRoute_thenReturnValidPathOfCorrectSize() {
        RoutingService routingService = new RoutingService(getAdjacencyList());

        Collection<String> routes = routingService.getRoutes("2", "3");
        assertEquals(4, routes.size());
        assertTrue(List.of(List.of("2", "0", "4", "3"), List.of("2", "5", "4", "3")).contains(routes));
    }

    private Map<String, Set<String>> getAdjacencyList() {
        Map<String, Set<String>> adjacencyMap = new HashMap<>();

        adjacencyMap.put("0", Set.of("1", "2", "4"));
        adjacencyMap.put("1", Set.of("0", "2"));
        adjacencyMap.put("2", Set.of("0", "1", "5"));
        adjacencyMap.put("3", Set.of("4"));
        adjacencyMap.put("4", Set.of("3", "5", "0", "6"));
        adjacencyMap.put("5", Set.of("4", "2"));
        adjacencyMap.put("6", Set.of("4"));
        adjacencyMap.put("7", Set.of("8"));
        adjacencyMap.put("8", Set.of("7", "10"));
        adjacencyMap.put("10", Set.of("7"));

        return adjacencyMap;
    }

}
