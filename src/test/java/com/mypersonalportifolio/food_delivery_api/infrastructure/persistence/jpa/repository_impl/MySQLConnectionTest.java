package com.mypersonalportifolio.food_delivery_api.infrastructure.persistence.jpa.repository_impl;

import com.mypersonalportifolio.food_delivery_api.domain.model.FoodCategory;
import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import com.mypersonalportifolio.food_delivery_api.domain.repository.FoodCategoryRepository;
import com.mypersonalportifolio.food_delivery_api.domain.repository.RestaurantRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
@DisplayName("MySQL Connection Integration Tests with TestContainers")
class MySQLConnectionTest {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("food-delivery-test-db")
            .withUsername("test-user")
            .withPassword("test-password")
            .withReuse(false);

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create");
        registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.MySQLDialect");
        registry.add("spring.jpa.show-sql", () -> "false");
    }

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private FoodCategoryRepository foodCategoryRepository;

    @Test
    @DisplayName("Should successfully connect to MySQL container")
    void testMySQLConnection() {
        assertTrue(mysql.isRunning(), "MySQL container should be running");
        assertNotNull(mysql.getJdbcUrl(), "JDBC URL should be available");
    }


    @Test
    @DisplayName("Should handle concurrent database operations with MySQL")
    void testConcurrentOperations() throws InterruptedException {
        var category = new FoodCategory("Mediterranean");
        var savedCategory = foodCategoryRepository.save(category);

        Thread thread1 = new Thread(() -> {
            var r1 = new Restaurant("Greek Taverna", new BigDecimal("5.50"), savedCategory);
            restaurantRepository.save(r1);
        });

        Thread thread2 = new Thread(() -> {
            var r2 = new Restaurant("Turkish Kebab", new BigDecimal("4.50"), savedCategory);
            restaurantRepository.save(r2);
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        var restaurants = restaurantRepository.findAll();
        assertEquals(2, restaurants.size(), "Both restaurants should be saved to MySQL");
    }


    @Test
    @DisplayName("Should properly use MySQL-specific features")
    void testMySQLSpecificFeatures() {
        var category = new FoodCategory("Korean");
        var savedCategory = foodCategoryRepository.save(category);

        var restaurant = new Restaurant("Korean BBQ", new BigDecimal("15.00"), savedCategory);
        var savedRestaurant = restaurantRepository.save(restaurant);

        assertNotNull(savedRestaurant.getId(), "UUID should be generated and stored in MySQL");
        assertTrue(savedRestaurant.getId().toString().matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"),
                "Should use UUID format supported by MySQL 8.0");
    }

    @Test
    @DisplayName("Should verify MySQL container details")
    void testContainerDetails() {
        assertTrue(mysql.getDockerImageName().contains("mysql:8.0"), "Should use MySQL 8.0");
        assertTrue(mysql.isRunning(), "Container should be running");
        assertNotNull(mysql.getJdbcUrl(), "JDBC URL should not be null");
        assertEquals("test-user", mysql.getUsername(), "Username should match");
        assertEquals("food-delivery-test-db", mysql.getDatabaseName(), "Database name should match");
    }
}
