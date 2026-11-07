package com.mypersonalportifolio.food_delivery_api.application.use_case;

import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.RestaurantOpeningStateInputDTO;
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

class SwitchRestaurantOpeningUseCaseTest {

    @Test
    void opensRestaurant() {
        var restaurantService = mock(RestaurantService.class);
        var useCase = new SwitchRestaurantOpeningUseCase(restaurantService);
        var restaurantId = UUID.randomUUID();
        var restaurant = mock(Restaurant.class);
        when(restaurantService.findValidRestaurant(restaurantId)).thenReturn(restaurant);
        when(restaurant.getOpen()).thenReturn(true);

        var input = new RestaurantOpeningStateInputDTO(true);
        input.setRestaurantId(restaurantId);
        useCase.execute(input);

        verify(restaurant).setOpen(true);
    }

    @Test
    void closesRestaurantWhenItHasNoActiveOrders() {
        var restaurantService = mock(RestaurantService.class);
        var useCase = new SwitchRestaurantOpeningUseCase(restaurantService);
        var restaurantId = UUID.randomUUID();
        var restaurant = mock(Restaurant.class);
        when(restaurantService.findValidRestaurant(restaurantId)).thenReturn(restaurant);
        when(restaurantService.isRestaurantIReadyToDeactivateOrClose(restaurant)).thenReturn(false);
        when(restaurant.getOpen()).thenReturn(false);

        var input = new RestaurantOpeningStateInputDTO(false);
        input.setRestaurantId(restaurantId);
        useCase.execute(input);

        verify(restaurant).setOpen(false);
    }

    @Test
    void refusesToCloseRestaurantWhileOrdersAreActive() {
        var restaurantService = mock(RestaurantService.class);
        var useCase = new SwitchRestaurantOpeningUseCase(restaurantService);
        var restaurantId = UUID.randomUUID();
        var restaurant = mock(Restaurant.class);
        when(restaurantService.findValidRestaurant(restaurantId)).thenReturn(restaurant);
        when(restaurant.getId()).thenReturn(restaurantId);
        when(restaurantService.isRestaurantIReadyToDeactivateOrClose(restaurant)).thenReturn(true);

        var input = new RestaurantOpeningStateInputDTO(false);
        input.setRestaurantId(restaurantId);
        assertThrows(RestaurantCantBeClosedException.class, () -> useCase.execute(input));

        verify(restaurant, never()).setOpen(false);
    }
}
