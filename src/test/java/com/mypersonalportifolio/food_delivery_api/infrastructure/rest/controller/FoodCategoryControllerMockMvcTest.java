package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.domain.repository.FoodCategoryRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.FoodCategoryService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.FoodCategoryRepresentationModelAssembler;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FoodCategoryControllerMockMvcTest extends MockMvcTestSupport {

    @Test
    void handlesListRequest() throws Exception {
        var controller = new FoodCategoryController(mock(FoodCategoryRepository.class), mock(FoodCategoryService.class),
                mock(FoodCategoryRepresentationModelAssembler.class), mockPagedResourcesAssembler());

        mockMvc(controller).perform(get("/food-categories")).andExpect(status().isOk());
    }
}
