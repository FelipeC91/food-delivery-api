package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.domain.repository.CityRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.CityService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.CityRepresentationModelAssembler;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CityControllerMockMvcTest extends MockMvcTestSupport {

    @Test
    void handlesListRequest() throws Exception {
        var controller = new CityController(mock(CityRepository.class), mock(CityService.class),
                mock(CityRepresentationModelAssembler.class), mockPagedResourcesAssembler());

        mockMvc(controller).perform(get("/cities")).andExpect(status().isOk());
    }
}
