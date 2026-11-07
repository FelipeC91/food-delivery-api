package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.domain.repository.OrderRepository;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.restaurant.RestaurantOrderController;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.OrderSimplifiedRepresentationModelAssembler;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantOrderControllerMockMvcTest extends MockMvcTestSupport {

    @Test
    void handlesListRequest() throws Exception {
        var controller = new RestaurantOrderController(mock(OrderRepository.class),
                mock(OrderSimplifiedRepresentationModelAssembler.class),
                mockPagedResourcesAssembler());

        mockMvc(controller).perform(get("/restaurants/{restaurantId}/orders", UUID.randomUUID()))
                .andExpect(status().isOk());
    }
}
