package com.mypersonalportifolio.food_delivery_api.integration_tests;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

@TestPropertySource("classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:import-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class FoodCategoryResourceIT {

    @MockitoBean(name = "jwkSource")
    private JWKSource<SecurityContext> jwkSource;

    @LocalServerPort
    private int port;

    private JwtEncoder jwtEncoder;

    private RSAKey rsaKey;

    @BeforeEach
    void setUp() throws Exception {
        RestAssured.port = port;
        RestAssured.basePath = "/api/v1/food-categories";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        var keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        rsaKey = new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey((RSAPrivateKey) keyPair.getPrivate())
                .keyID("integration-test-key")
                .build();
        when(jwkSource.get(any(JWKSelector.class), isNull()))
                .thenAnswer(invocation -> invocation.<JWKSelector>getArgument(0).select(new JWKSet(rsaKey)));
        jwtEncoder = new NimbusJwtEncoder(jwkSource);
    }

    private String bearerToken(String... authorities) {
        var now = Instant.now();
        var claims = JwtClaimsSet.builder()
                .issuer("http://127.0.0.1:8080")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(300))
                .claim("authorities", List.of(authorities))
                .build();
        var header = JwsHeader.with(SignatureAlgorithm.RS256)
                .keyId("integration-test-key")
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }

    @Test
    public void shouldReturn200WhenGetFoodCategory() {
        RestAssured.given()
                .log().all()
                .header("Authorization", "Bearer " + bearerToken())
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value())
                        .body("_embedded.foodCategories.name", Matchers.hasItems("Tailandesa", "Indiana"))
        ;
    }

    @Test
    public void shouldReturn200WhenPostValidFoodCategory() {
        var categoryName = "Asian-" + UUID.randomUUID();
        RestAssured.given()
                .log().all()
                .header("Authorization", "Bearer " + bearerToken("EDITAR_COZINHAS"))
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "name": "%s",
                            "description": null
                        }
                        """.formatted(categoryName))
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }


    @Test
    public void shouldReturn404WhenGetNonExistentFoodCategory() {
        RestAssured.given()
                .header("Authorization", "Bearer " + bearerToken())
                .accept(ContentType.JSON)
                .pathParam("foodCategoryId", UUID.randomUUID().toString())
                .when()
                .get("/{foodCategoryId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
