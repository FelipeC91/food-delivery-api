package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.domain.repository.RestaurantRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.RestaurantService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.restaurant.RestaurantController;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.RestaurantRepresentationModelAssembler;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.RestaurantSimplifiedRepresentationModelAssembler;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.mapstruct.RestaurantMapper;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantControllerMockMvcTest extends MockMvcTestSupport {

    @Test
    void handlesListRequest() throws Exception {
        var controller = new RestaurantController(mock(RestaurantRepository.class), mock(RestaurantService.class),
                mock(RestaurantMapper.class),
                mock(RestaurantRepresentationModelAssembler.class), mock(RestaurantSimplifiedRepresentationModelAssembler.class),
                mockPagedResourcesAssembler());

        mockMvc(controller).perform(get("/restaurants")).andExpect(status().isOk());
    }
}
