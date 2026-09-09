package com.mypersonalportifolio.food_delivery_api.integration_tests;

import com.mypersonalportifolio.food_delivery_api.domain.model.FoodCategory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

@TestPropertySource("classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:import-test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
public class FoodCategoryResourceIT {

    @LocalServerPort
    private int port;

    @Autowired
    private Flyway flyway;

    @BeforeEach
    void setUp() {
        // Point REST Assured to the dynamically assigned local port
        RestAssured.port = port;
        RestAssured.basePath = "/food-categories";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    }
    @Test
    public void shouldReturn200WhenGetFoodCategory() throws Exception {
        RestAssured.given()
                .log().all()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value())
                        .body("name", Matchers.hasItems("Tailandesa", "Indiana"))
        ;
    }

    @Test
    public void shouldReturn200WhenPostValidFoodCategory() {
        RestAssured.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "name": "Asian",
                            "description": null
                        }
                        """
                )
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }


    @Test
    public void shouldReturn404WhenGetNonExistentFoodCategory() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .pathParam("foodCategoryId", UUID.randomUUID().toString())
                .when()
                .get("/{foodCategoryId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}

