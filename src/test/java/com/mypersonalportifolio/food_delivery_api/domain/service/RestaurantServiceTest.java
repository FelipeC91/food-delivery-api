package com.mypersonalportifolio.food_delivery_api.domain.service;

import com.mypersonalportifolio.food_delivery_api.domain.model.FoodCategory;
import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RestaurantService Tests")
class RestaurantServiceTest {

    private RestaurantService restaurantService;
    private Restaurant restaurantTarget;
    private FoodCategory foodCategory;

    @BeforeEach
    void setUp() {
        restaurantService = new RestaurantService();
        foodCategory = new FoodCategory("Pizza");

        restaurantTarget = new Restaurant(
            "Original Restaurant",
            new BigDecimal("5.00"),
            foodCategory,
            new ArrayList<>()
        );
    }

    @Nested
    @DisplayName("mergeProperties - Null and Empty Map scenarios")
    class NullAndEmptyMapTests {

        @Test
        @DisplayName("should not modify target when map is null")
        void testNullMapDoesNotModifyTarget() {
            String originalName = restaurantTarget.getName();
            BigDecimal originalCost = restaurantTarget.getShippingCost();

            restaurantService.mergeProperties(null, restaurantTarget);

            assertEquals(originalName, restaurantTarget.getName());
            assertEquals(originalCost, restaurantTarget.getShippingCost());
        }

        @Test
        @DisplayName("should not modify target when map is empty")
        void testEmptyMapDoesNotModifyTarget() {
            String originalName = restaurantTarget.getName();
            BigDecimal originalCost = restaurantTarget.getShippingCost();

            restaurantService.mergeProperties(new HashMap<>(), restaurantTarget);

            assertEquals(originalName, restaurantTarget.getName());
            assertEquals(originalCost, restaurantTarget.getShippingCost());
        }
    }

    @Nested
    @DisplayName("mergeProperties - Single Property Update")
    class SinglePropertyUpdateTests {

        @Test
        @DisplayName("should update name property only")
        void testUpdateNameProperty() {
            Map<String, Object> sourceMap = new HashMap<>();
            sourceMap.put("name", "New Restaurant Name");

            restaurantService.mergeProperties(sourceMap, restaurantTarget);

            assertEquals("New Restaurant Name", restaurantTarget.getName());
            assertEquals(new BigDecimal("5.00"), restaurantTarget.getShippingCost());
            assertEquals(foodCategory, restaurantTarget.getFoodCategory());
        }

        @Test
        @DisplayName("should update shipping cost property only")
        void testUpdateShippingCostProperty() {
            Map<String, Object> sourceMap = new HashMap<>();
            sourceMap.put("shippingCost", new BigDecimal("10.50"));

            restaurantService.mergeProperties(sourceMap, restaurantTarget);

            assertEquals("Original Restaurant", restaurantTarget.getName());
            assertEquals(new BigDecimal("10.50"), restaurantTarget.getShippingCost());
            assertEquals(foodCategory, restaurantTarget.getFoodCategory());
        }
    }

    @Nested
    @DisplayName("mergeProperties - Multiple Properties Update")
    class MultiplePropertiesUpdateTests {

        @Test
        @DisplayName("should update multiple properties")
        void testUpdateMultipleProperties() {
            Map<String, Object> sourceMap = new HashMap<>();
            sourceMap.put("name", "Updated Restaurant");
            sourceMap.put("shippingCost", new BigDecimal("7.99"));

            restaurantService.mergeProperties(sourceMap, restaurantTarget);

            assertEquals("Updated Restaurant", restaurantTarget.getName());
            assertEquals(new BigDecimal("7.99"), restaurantTarget.getShippingCost());
            assertEquals(foodCategory, restaurantTarget.getFoodCategory());
        }

        @Test
        @DisplayName("should update all restaurant properties")
        void testUpdateAllProperties() {
            FoodCategory newFoodCategory = new FoodCategory( "Burger");

            Map<String, Object> sourceMap = new HashMap<>();
            sourceMap.put("name", "Burger House");
            sourceMap.put("shippingCost", new BigDecimal("3.50"));
            sourceMap.put("foodCategory", newFoodCategory);

            restaurantService.mergeProperties(sourceMap, restaurantTarget);

            assertEquals("Burger House", restaurantTarget.getName());
            assertEquals(new BigDecimal("3.50"), restaurantTarget.getShippingCost());
            assertEquals(newFoodCategory, restaurantTarget.getFoodCategory());
        }
    }

    @Nested
    @DisplayName("mergeProperties - Null Property Handling")
    class NullPropertyHandlingTests {

        @Test
        @DisplayName("should not overwrite target property when source property is null")
        void testNullPropertyDoesNotOverwriteTarget() {
            Map<String, Object> sourceMap = new HashMap<>();
            sourceMap.put("name", "Updated Name");
            sourceMap.put("shippingCost", null);

            restaurantService.mergeProperties(sourceMap, restaurantTarget);

            assertEquals("Updated Name", restaurantTarget.getName());
            assertEquals(new BigDecimal("5.00"), restaurantTarget.getShippingCost());
        }

        @Test
        @DisplayName("should handle map with only null values")
        void testMapWithOnlyNullValues() {
            String originalName = restaurantTarget.getName();
            BigDecimal originalCost = restaurantTarget.getShippingCost();

            Map<String, Object> sourceMap = new HashMap<>();
            sourceMap.put("name", null);
            sourceMap.put("shippingCost", null);

            restaurantService.mergeProperties(sourceMap, restaurantTarget);

            assertEquals(originalName, restaurantTarget.getName());
            assertEquals(originalCost, restaurantTarget.getShippingCost());
        }

        @Test
        @DisplayName("should update target property when null in both source and target")
        void testUpdateWhenBothNull() {
            restaurantTarget = new Restaurant(null, null, foodCategory, new ArrayList<>());

            Map<String, Object> sourceMap = new HashMap<>();
            sourceMap.put("name", "New Name");
            sourceMap.put("shippingCost", new BigDecimal("2.99"));

            restaurantService.mergeProperties(sourceMap, restaurantTarget);

            assertEquals("New Name", restaurantTarget.getName());
            assertEquals(new BigDecimal("2.99"), restaurantTarget.getShippingCost());
        }
    }

    @Nested
    @DisplayName("mergeProperties - Edge Cases")
    class EdgeCaseTests {

        @Test
        @DisplayName("should handle map with unknown properties")
        void testMapWithUnknownProperties() {
            Map<String, Object> sourceMap = new HashMap<>();
            sourceMap.put("name", "Restaurant");
            sourceMap.put("unknownProperty", "value");

            restaurantService.mergeProperties(sourceMap, restaurantTarget);

            assertEquals("Restaurant", restaurantTarget.getName());
        }

        @Test
        @DisplayName("should handle BigDecimal with zero value")
        void testBigDecimalZeroValue() {
            Map<String, Object> sourceMap = new HashMap<>();
            sourceMap.put("shippingCost", BigDecimal.ZERO);

            restaurantService.mergeProperties(sourceMap, restaurantTarget);

            assertEquals(BigDecimal.ZERO, restaurantTarget.getShippingCost());
        }

        @Test
        @DisplayName("should handle empty string for name")
        void testEmptyStringProperty() {
            Map<String, Object> sourceMap = new HashMap<>();
            sourceMap.put("name", "");

            restaurantService.mergeProperties(sourceMap, restaurantTarget);

            assertEquals("", restaurantTarget.getName());
        }
    }

    @Nested
    @DisplayName("mergeProperties - Integration Scenarios")
    class IntegrationScenarios {

        @Test
        @DisplayName("should perform partial update with mixed null and non-null values")
        void testPartialUpdateWithMixedValues() {
            FoodCategory newCategory = new FoodCategory( "Sushi");

            Map<String, Object> sourceMap = new HashMap<>();
            sourceMap.put("name", "Sushi Bar");
            sourceMap.put("shippingCost", null);
            sourceMap.put("foodCategory", newCategory);

            restaurantService.mergeProperties(sourceMap, restaurantTarget);

            assertEquals("Sushi Bar", restaurantTarget.getName());
            assertEquals(new BigDecimal("5.00"), restaurantTarget.getShippingCost());
            assertEquals(newCategory, restaurantTarget.getFoodCategory());
        }

        @Test
        @DisplayName("should merge properties multiple times (idempotent updates)")
        void testMultipleMerges() {
            Map<String, Object> firstUpdate = new HashMap<>();
            firstUpdate.put("name", "First Update");

            Map<String, Object> secondUpdate = new HashMap<>();
            secondUpdate.put("shippingCost", new BigDecimal("8.99"));

            restaurantService.mergeProperties(firstUpdate, restaurantTarget);
            restaurantService.mergeProperties(secondUpdate, restaurantTarget);

            assertEquals("First Update", restaurantTarget.getName());
            assertEquals(new BigDecimal("8.99"), restaurantTarget.getShippingCost());
        }
    }
}
