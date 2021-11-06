package com.adrianr.landroutes.rest;

import com.adrianr.landroutes.service.RoutingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("routing")
public class RoutingController {

    private final RoutingService routingService;

    public RoutingController(RoutingService routingService) {
        this.routingService = routingService;
    }

    @GetMapping("{origin:^[a-zA-Z]{3}$}/{destination:^[a-zA-Z]{3}$}")
    public ResponseEntity<Map<String, Collection<String>>> getRoute(@PathVariable String origin, @PathVariable String destination) {
        return ResponseEntity.ok(Collections.singletonMap("route", routingService.getRoutes(origin, destination)));
    }

}
