package com.adrianr.landroutes.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static java.util.Objects.isNull;

@Service
public class RoutingService {

    private final Map<String, Set<String>> adjacencyList;

    public RoutingService(Map<String, Set<String>> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    public Collection<String> getRoutes(String origin, String destination) {
        Map<String, String> prev = searchForPath(origin.toUpperCase(), destination.toUpperCase());

        if (!prev.containsKey(destination)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No land route found.");
        }

        return reconstructPath(destination, prev);
    }

    private Map<String, String> searchForPath(String origin, String destination) {
        validateCountryNodes(origin, destination);

        Queue<String> searchQueue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        Map<String, String> prev = new HashMap<>();

        visited.add(origin);
        searchQueue.add(origin);
        while (!searchQueue.isEmpty()) {
            String country = searchQueue.poll();

            for (String neighbour : adjacencyList.get(country)) {
                if (!visited.contains(neighbour)) {
                    searchQueue.add(neighbour);
                    visited.add(neighbour);
                    prev.put(neighbour, country);

                    if (neighbour.equals(destination)) {
                        break;
                    }
                }
            }
        }
        return prev;
    }

    private Collection<String> reconstructPath(String destination, Map<String, String> prev) {
        Deque<String> path = new LinkedList<>();
        String crawl = destination;
        path.add(crawl);

        while (prev.containsKey(crawl)) {
            path.addFirst(prev.get(crawl));
            crawl = prev.get(crawl);
        }

        return path;
    }

    private void validateCountryNodes(String origin, String destination) {
        if (isNull(origin) || isNull(destination) ||
                !adjacencyList.containsKey(origin) || !adjacencyList.containsKey(destination)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid country codes.");
        }
    }

}
