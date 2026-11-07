package com.mypersonalportifolio.food_delivery_api.application.use_case;

import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.RestaurantActivationStateInputDTO;
import com.mypersonalportifolio.food_delivery_api.domain.exception.RestaurantCantBeClosedException;
import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import com.mypersonalportifolio.food_delivery_api.domain.service.RestaurantService;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SwitchRestaurantActivationUseCaseTest {

    @Test
    void activatesRestaurant() {
        var restaurantService = mock(RestaurantService.class);
        var useCase = new SwitchRestaurantActivationUseCase(restaurantService);
        var restaurantId = UUID.randomUUID();
        var restaurant = mock(Restaurant.class);
        when(restaurantService.findValidRestaurant(restaurantId)).thenReturn(restaurant);
        when(restaurant.getOpen()).thenReturn(false);

        var input = new RestaurantActivationStateInputDTO(true);
        input.setRestaurantId(restaurantId);
        useCase.execute(input);

        verify(restaurant).setActive(true);
    }

    @Test
    void deactivatesRestaurantWhenItHasNoActiveOrders() {
        var restaurantService = mock(RestaurantService.class);
        var useCase = new SwitchRestaurantActivationUseCase(restaurantService);
        var restaurantId = UUID.randomUUID();
        var restaurant = mock(Restaurant.class);
        when(restaurantService.findValidRestaurant(restaurantId)).thenReturn(restaurant);
        when(restaurantService.isRestaurantIReadyToDeactivateOrClose(restaurant)).thenReturn(false);
        when(restaurant.getOpen()).thenReturn(false);

        var input = new RestaurantActivationStateInputDTO(false);
        input.setRestaurantId(restaurantId);
        useCase.execute(input);

        verify(restaurant).setActive(false);
    }

    @Test
    void refusesDeactivationWhileOrdersAreActive() {
        var restaurantService = mock(RestaurantService.class);
        var useCase = new SwitchRestaurantActivationUseCase(restaurantService);
        var restaurantId = UUID.randomUUID();
        var restaurant = mock(Restaurant.class);
        when(restaurantService.findValidRestaurant(restaurantId)).thenReturn(restaurant);
        when(restaurant.getId()).thenReturn(restaurantId);
        when(restaurantService.isRestaurantIReadyToDeactivateOrClose(restaurant)).thenReturn(true);

        var input = new RestaurantActivationStateInputDTO(false);
        input.setRestaurantId(restaurantId);
        assertThrows(RestaurantCantBeClosedException.class, () -> useCase.execute(input));

        verify(restaurant, never()).setActive(false);
    }
}
