package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.application.use_case.SwitchRestaurantActivationUseCase;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.restaurant.RestaurantActivationController;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantActivationControllerMockMvcTest extends MockMvcTestSupport {

    @Test
    void handlesPatchRequest() throws Exception {
        var controller = new RestaurantActivationController(mock(SwitchRestaurantActivationUseCase.class));

        mockMvc(controller).perform(patch("/restaurants/{restaurantId}/activation", UUID.randomUUID())
                        .contentType("application/json").content("{\"activation\":true}"))
                .andExpect(status().isNoContent());
    }
}
