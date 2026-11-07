package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import com.mypersonalportifolio.food_delivery_api.domain.service.RestaurantService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.restaurant.RestaurantManagersController;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.UserRepresentationModelAssembler;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantManagersControllerMockMvcTest extends MockMvcTestSupport {

    @Test
    void handlesListRequest() throws Exception {
        var restaurantService = mock(RestaurantService.class);
        when(restaurantService.findValidRestaurant(any())).thenReturn(mock(Restaurant.class));
        var controller = new RestaurantManagersController(restaurantService, mock(UserRepresentationModelAssembler.class),
                mockPagedResourcesAssembler());

        mockMvc(controller).perform(get("/restaurants/{restaurantId}/managers", UUID.randomUUID()))
                .andExpect(status().isOk());
    }
}
