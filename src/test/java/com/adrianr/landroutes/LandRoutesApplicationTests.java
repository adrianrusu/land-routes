package com.adrianr.landroutes;

import com.adrianr.landroutes.client.CountriesClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class LandRoutesApplicationTests {

	@MockBean
	private CountriesClient countriesClient;

	@Test
	void contextLoads() {
	}

}
