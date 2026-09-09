package com.mypersonalportifolio.food_delivery_api.infrastructure.persistence.jpa.repository_impl;

import com.mypersonalportifolio.food_delivery_api.domain.model.*;
import com.mypersonalportifolio.food_delivery_api.domain.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DisplayName("CustomRestaurantRepositoryImpl Tests")
class CustomRestaurantRepositoryImplTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private FoodCategoryRepository foodCategoryRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private EntityManager entityManager;

    private FoodCategory testCategory;
    private City testCity;
    private Restaurant restaurant1;
    private Restaurant restaurant2;
    private Restaurant restaurant3;

    @BeforeEach
    void setUp() {
        entityManager.createNativeQuery("DELETE FROM product").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM restaurant_payment_method").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM restaurant").executeUpdate();
        
        testCategory = new FoodCategory("Italian", null);
        testCategory = foodCategoryRepository.save(testCategory);

        var testState = stateRepository.save(new State("Sao Paulo"));
        testCity = cityRepository.save(new City("Sao Paulo", testState));

        restaurant1 = restaurant("McDonald's", new BigDecimal("5.00"));
        restaurant2 = restaurant("Subway", new BigDecimal("3.50"));
        restaurant3 = restaurant("Pizza Hut", new BigDecimal("7.99"));

        restaurantRepository.saveAll(List.of(restaurant1, restaurant2, restaurant3));
    }

    @Test
    @DisplayName("Should find restaurants by name filter only")
    void testFindByNameFilterOnly() {
        var filterDTO = new CustomRestaurantRepository.ByNameLikeAndShippingCostBetweenFilterDTO(
                "McDonald",
                null,
                null
        );

        var result = restaurantRepository.customQueryByNameLikeAndShippingCostBetween(filterDTO);

        assertEquals(1, result.size());
        assertEquals("McDonald's", result.get(0).getName());
    }

    @Test
    @DisplayName("Should find restaurants by minimum shipping cost filter only")
    void testFindByMinimumShippingCostOnly() {
        var filterDTO = new CustomRestaurantRepository.ByNameLikeAndShippingCostBetweenFilterDTO(
                null,
                new BigDecimal("5.00"),
                null
        );

        var result = restaurantRepository.customQueryByNameLikeAndShippingCostBetween(filterDTO);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(r -> r.getShippingCost().compareTo(new BigDecimal("5.00")) >= 0));
    }

    @Test
    @DisplayName("Should find restaurants by maximum shipping cost filter only")
    void testFindByMaximumShippingCostOnly() {
        var filterDTO = new CustomRestaurantRepository.ByNameLikeAndShippingCostBetweenFilterDTO(
                null,
                null,
                new BigDecimal("5.00")
        );

        var result = restaurantRepository.customQueryByNameLikeAndShippingCostBetween(filterDTO);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(r -> r.getShippingCost().compareTo(new BigDecimal("5.00")) <= 0));
    }

    @Test
    @DisplayName("Should find restaurants by shipping cost range")
    void testFindByShippingCostRange() {
        var filterDTO = new CustomRestaurantRepository.ByNameLikeAndShippingCostBetweenFilterDTO(
                null,
                new BigDecimal("3.00"),
                new BigDecimal("6.00")
        );

        var result = restaurantRepository.customQueryByNameLikeAndShippingCostBetween(filterDTO);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(r ->
                r.getShippingCost().compareTo(new BigDecimal("3.00")) >= 0 &&
                        r.getShippingCost().compareTo(new BigDecimal("6.00")) <= 0
        ));
    }

    @Test
    @DisplayName("Should find restaurants by name and minimum shipping cost")
    void testFindByNameAndMinimumShippingCost() {
        var filterDTO = new CustomRestaurantRepository.ByNameLikeAndShippingCostBetweenFilterDTO(
                "Pizza",
                new BigDecimal("6.00"),
                null
        );

        var result = restaurantRepository.customQueryByNameLikeAndShippingCostBetween(filterDTO);

        assertEquals(1, result.size());
        assertEquals("Pizza Hut", result.get(0).getName());
        assertEquals(new BigDecimal("7.99"), result.get(0).getShippingCost());
    }

    @Test
    @DisplayName("Should find restaurants by name and maximum shipping cost")
    void testFindByNameAndMaximumShippingCost() {
        var filterDTO = new CustomRestaurantRepository.ByNameLikeAndShippingCostBetweenFilterDTO(
                "Sub",
                null,
                new BigDecimal("4.00")
        );

        var result = restaurantRepository.customQueryByNameLikeAndShippingCostBetween(filterDTO);

        assertEquals(1, result.size());
        assertEquals("Subway", result.get(0).getName());
    }

    @Test
    @DisplayName("Should find restaurants by all three filters combined")
    void testFindByAllFiltersCombined() {
        var filterDTO = new CustomRestaurantRepository.ByNameLikeAndShippingCostBetweenFilterDTO(
                "McDonald",
                new BigDecimal("4.00"),
                new BigDecimal("6.00")
        );

        var result = restaurantRepository.customQueryByNameLikeAndShippingCostBetween(filterDTO);

        assertEquals(1, result.size());
        assertEquals("McDonald's", result.get(0).getName());
    }

    @Test
    @DisplayName("Should return all restaurants when name filter is null")
    void testFindWithNullNameFilter() {
        var filterDTO = new CustomRestaurantRepository.ByNameLikeAndShippingCostBetweenFilterDTO(
                null,
                null,
                null
        );

        var result = restaurantRepository.customQueryByNameLikeAndShippingCostBetween(filterDTO);

        assertEquals(3, result.size());
    }

    @Test
    @DisplayName("Should return all restaurants when name filter is blank")
    void testFindWithBlankNameFilter() {
        var filterDTO = new CustomRestaurantRepository.ByNameLikeAndShippingCostBetweenFilterDTO(
                "   ",
                null,
                null
        );

        var result = restaurantRepository.customQueryByNameLikeAndShippingCostBetween(filterDTO);

        assertEquals(3, result.size());
    }

    @Test
    @DisplayName("Should return empty list when no restaurants match filters")
    void testFindWithNoMatches() {
        var filterDTO = new CustomRestaurantRepository.ByNameLikeAndShippingCostBetweenFilterDTO(
                "NonExistent",
                null,
                null
        );

        var result = restaurantRepository.customQueryByNameLikeAndShippingCostBetween(filterDTO);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list when shipping cost range doesn't match")
    void testFindWithNoMatchesShippingCost() {
        var filterDTO = new CustomRestaurantRepository.ByNameLikeAndShippingCostBetweenFilterDTO(
                null,
                new BigDecimal("100.00"),
                new BigDecimal("200.00")
        );

        var result = restaurantRepository.customQueryByNameLikeAndShippingCostBetween(filterDTO);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should perform case-insensitive name search")
    void testCaseInsensitiveNameSearch() {
        var filterDTO = new CustomRestaurantRepository.ByNameLikeAndShippingCostBetweenFilterDTO(
                "mcdonald",
                null,
                null
        );

        var result = restaurantRepository.customQueryByNameLikeAndShippingCostBetween(filterDTO);

        assertEquals(1, result.size());
        assertEquals("McDonald's", result.get(0).getName());
    }

    @Test
    @DisplayName("Should find restaurants with exact shipping cost minimum")
    void testFindWithExactMinimumShippingCost() {
        var filterDTO = new CustomRestaurantRepository.ByNameLikeAndShippingCostBetweenFilterDTO(
                null,
                new BigDecimal("3.50"),
                null
        );

        var result = restaurantRepository.customQueryByNameLikeAndShippingCostBetween(filterDTO);

        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(r -> r.getName().equals("Subway")));
    }

    @Test
    @DisplayName("Should find restaurants with exact shipping cost maximum")
    void testFindWithExactMaximumShippingCost() {
        var filterDTO = new CustomRestaurantRepository.ByNameLikeAndShippingCostBetweenFilterDTO(
                null,
                null,
                new BigDecimal("7.99")
        );

        var result = restaurantRepository.customQueryByNameLikeAndShippingCostBetween(filterDTO);

        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(r -> r.getName().equals("Pizza Hut")));
    }

    @Test
    @DisplayName("Should return result list in predictable order")
    void testResultConsistency() {
        var filterDTO = new CustomRestaurantRepository.ByNameLikeAndShippingCostBetweenFilterDTO(
                null,
                null,
                null
        );

        var result1 = restaurantRepository.customQueryByNameLikeAndShippingCostBetween(filterDTO);
        var result2 = restaurantRepository.customQueryByNameLikeAndShippingCostBetween(filterDTO);

        assertEquals(result1.size(), result2.size());
        assertNotNull(result1);
        assertNotNull(result2);
    }

    @Test
    @DisplayName("Should handle empty string name filter same as null")
    void testEmptyStringNameFilter() {
        var filterDTO = new CustomRestaurantRepository.ByNameLikeAndShippingCostBetweenFilterDTO(
                "",
                null,
                null
        );

        var result = restaurantRepository.customQueryByNameLikeAndShippingCostBetween(filterDTO);

        assertEquals(3, result.size());
    }

    @Test
    @DisplayName("Should find restaurants with partial name match")
    void testPartialNameMatch() {
        var filterDTO = new CustomRestaurantRepository.ByNameLikeAndShippingCostBetweenFilterDTO(
                "a",
                null,
                null
        );

        var result = restaurantRepository.customQueryByNameLikeAndShippingCostBetween(filterDTO);

        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(r -> r.getName().equals("McDonald's")));
        assertTrue(result.stream().anyMatch(r -> r.getName().equals("Subway")));
        assertTrue(result.stream().anyMatch(r -> r.getName().equals("Pizza Hut")));
    }

    private Restaurant restaurant(String name, BigDecimal shippingCost) {
        var address = new Address("Vila Medeiros", "02219001", "Av. Nossa Sra. do Loreto", 1100, testCity);
        return new Restaurant(
                name,
                shippingCost,
                testCategory,
                address,
                new ArrayList<>(),
                null,
                null,
                new ArrayList<>(),
                true
        );
    }
}
