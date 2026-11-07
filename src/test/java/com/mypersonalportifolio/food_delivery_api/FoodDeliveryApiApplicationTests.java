package com.mypersonalportifolio.food_delivery_api;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class FoodDeliveryApiApplicationTests {

	@MockitoBean(name = "jwkSource")
	private JWKSource<SecurityContext> jwkSource;

	@Test
	void contextLoads() {
	}

}
