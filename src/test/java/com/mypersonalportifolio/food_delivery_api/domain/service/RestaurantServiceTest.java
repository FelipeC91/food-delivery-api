package com.mypersonalportifolio.food_delivery_api.domain.service;

import com.mypersonalportifolio.food_delivery_api.domain.exception.CandidateEntityInvalidException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.FailOnValidateEntityPropertiesException;
import com.mypersonalportifolio.food_delivery_api.domain.model.FoodCategory;
import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import com.mypersonalportifolio.food_delivery_api.domain.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.SmartValidator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private SmartValidator validator;

    @InjectMocks
    private RestaurantService restaurantService;

    private FoodCategory foodCategory;
    private Restaurant restaurantTarget;

    @BeforeEach
    void setUp() {
        foodCategory = new FoodCategory("Pizza", null);
        restaurantTarget = restaurant("Original Restaurant", new BigDecimal("5.00"), foodCategory);
    }

    @Test
    void shouldRejectNullPropertiesWhenMerging() {
        assertThrows(CandidateEntityInvalidException.class,
                () -> restaurantService.mergeProperties(null, restaurantTarget));
    }

    @Test
    void shouldRejectEmptyPropertiesWhenMerging() {
        assertThrows(CandidateEntityInvalidException.class,
                () -> restaurantService.mergeProperties(Map.of(), restaurantTarget));
    }

    @Test
    void shouldUpdateNameWhenMergingProperties() {
        restaurantService.mergeProperties(Map.of("name", "New Restaurant Name"), restaurantTarget);

        assertEquals("New Restaurant Name", restaurantTarget.getName());
        assertEquals(new BigDecimal("5.00"), restaurantTarget.getShippingCost());
        assertSame(foodCategory, restaurantTarget.getFoodCategory());
    }

    @Test
    void shouldUpdateShippingCostWhenMergingProperties() {
        restaurantService.mergeProperties(Map.of("shippingCost", new BigDecimal("10.50")), restaurantTarget);

        assertEquals("Original Restaurant", restaurantTarget.getName());
        assertEquals(new BigDecimal("10.50"), restaurantTarget.getShippingCost());
    }

    @Test
    void shouldUpdateMultiplePropertiesWhenMerging() {
        var sourceProperties = new HashMap<String, Object>();
        sourceProperties.put("name", "Updated Restaurant");
        sourceProperties.put("shippingCost", new BigDecimal("7.99"));

        restaurantService.mergeProperties(sourceProperties, restaurantTarget);

        assertEquals("Updated Restaurant", restaurantTarget.getName());
        assertEquals(new BigDecimal("7.99"), restaurantTarget.getShippingCost());
    }

    @Test
    void shouldNotOverwritePropertiesWithNullWhenMerging() {
        var sourceProperties = new HashMap<String, Object>();
        sourceProperties.put("name", "Updated Name");
        sourceProperties.put("shippingCost", null);

        restaurantService.mergeProperties(sourceProperties, restaurantTarget);

        assertEquals("Updated Name", restaurantTarget.getName());
        assertEquals(new BigDecimal("5.00"), restaurantTarget.getShippingCost());
    }

    @Test
    void shouldRejectUnknownPropertiesWhenMerging() {
        var sourceProperties = new HashMap<String, Object>();
        sourceProperties.put("name", "Restaurant");
        sourceProperties.put("unknownProperty", "value");

        assertThrows(IllegalArgumentException.class,
                () -> restaurantService.mergeProperties(sourceProperties, restaurantTarget));
    }

    @Test
    void shouldRejectInvalidMergedRestaurant() {
        doAnswer(invocation -> {
            var bindingResult = invocation.getArgument(1, org.springframework.validation.Errors.class);
            bindingResult.reject("invalid.restaurant");
            return null;
        }).when(validator).validate(org.mockito.ArgumentMatchers.any(),
                org.mockito.ArgumentMatchers.any());

        assertThrows(FailOnValidateEntityPropertiesException.class,
                () -> restaurantService.mergeProperties(Map.of("name", "Updated Name"), restaurantTarget));
    }

    @Test
    void shouldSaveUpdatedRestaurantProperties() {
        var source = restaurant("Updated Restaurant", new BigDecimal("8.99"), foodCategory);
        when(restaurantRepository.save(restaurantTarget)).thenReturn(restaurantTarget);

        var result = restaurantService.updateProperties(source, restaurantTarget);

        assertSame(restaurantTarget, result);
        assertEquals("Updated Restaurant", restaurantTarget.getName());
        verify(restaurantRepository).save(restaurantTarget);
    }

    private Restaurant restaurant(String name, BigDecimal shippingCost, FoodCategory category) {
        return new Restaurant(
                name,
                shippingCost,
                category,
                null,
                new ArrayList<>(),
                null,
                null,
                new ArrayList<>()
        );
    }
}
