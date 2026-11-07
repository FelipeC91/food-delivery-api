package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.domain.repository.ProductRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.ProductService;
import com.mypersonalportifolio.food_delivery_api.domain.service.RestaurantService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.restaurant.RestaurantProductController;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.ProductRepresentationModelAssembler;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantProductControllerMockMvcTest extends MockMvcTestSupport {

    @Test
    void handlesListRequest() throws Exception {
        var controller = new RestaurantProductController(mock(ProductRepository.class), mock(RestaurantService.class),
                mock(ProductService.class),
                mock(ProductRepresentationModelAssembler.class));

        mockMvc(controller).perform(get("/restaurants/{restaurantId}/products", UUID.randomUUID()))
                .andExpect(status().isOk());
    }
}
