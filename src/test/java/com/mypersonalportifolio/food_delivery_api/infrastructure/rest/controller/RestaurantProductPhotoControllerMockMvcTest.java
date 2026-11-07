package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.application.use_case.RemoveProductPhotoUseCase;
import com.mypersonalportifolio.food_delivery_api.application.use_case.RetrieveProductPhotoUseCase;
import com.mypersonalportifolio.food_delivery_api.application.use_case.UploadProductPhotoUseCase;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.restaurant.RestaurantProductPhotoController;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantProductPhotoControllerMockMvcTest extends MockMvcTestSupport {

    @Test
    void handlesDeleteRequest() throws Exception {
        var controller = new RestaurantProductPhotoController(mock(UploadProductPhotoUseCase.class),
                mock(RetrieveProductPhotoUseCase.class),
                mock(RemoveProductPhotoUseCase.class));

        mockMvc(controller).perform(delete("/restaurants/{restaurantId}/products/{productId}/photo",
                        UUID.randomUUID(), UUID.randomUUID()))
                .andExpect(status().isNoContent());
    }
}
