package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.application.use_case.SwitchRestaurantOpeningUseCase;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.restaurant.RestaurantOpeningController;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantOpeningControllerMockMvcTest extends MockMvcTestSupport {

    @Test
    void handlesPatchRequest() throws Exception {
        var controller = new RestaurantOpeningController(mock(SwitchRestaurantOpeningUseCase.class));

        mockMvc(controller).perform(patch("/restaurants/{restaurantId}/opening", UUID.randomUUID())
                        .contentType("application/json").content("{\"opening\":true}"))
                .andExpect(status().isNoContent());
    }
}
