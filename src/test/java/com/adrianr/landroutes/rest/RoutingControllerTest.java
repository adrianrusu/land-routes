package com.adrianr.landroutes.rest;

import com.adrianr.landroutes.client.CountriesClient;
import com.adrianr.landroutes.service.RoutingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RoutingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountriesClient countriesClient;

    @MockBean
    private RoutingService routingService;

    @Test
    void givenIncorrectParameters_whenCallingGetRoute_ThenExpectNotFoundResponseStatus() throws Exception {
        mockMvc.perform(get("/routing/a/b"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void givenCorrectParameters_whenCallingGetRoute_ThenExpectOkResponseStatus() throws Exception {
        when(routingService.getRoutes("ABC", "DEF")).thenReturn(List.of("ABC", "XYZ" ,"DEF"));

        mockMvc.perform(get("/routing/ABC/DEF"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.route").isArray())
                .andExpect(jsonPath("$.route[0]").value("ABC"))
                .andExpect(jsonPath("$.route[1]").value("XYZ"))
                .andExpect(jsonPath("$.route[2]").value("DEF"));
    }

}
