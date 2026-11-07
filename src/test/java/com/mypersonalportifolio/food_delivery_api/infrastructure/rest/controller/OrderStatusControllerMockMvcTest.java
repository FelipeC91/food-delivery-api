package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.application.use_case.UpdateOrderStatusUseCase;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.order.OrderStatusController;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderStatusControllerMockMvcTest extends MockMvcTestSupport {

    @Test
    void handlesPatchRequest() throws Exception {
        var controller = new OrderStatusController(mock(UpdateOrderStatusUseCase.class));

        mockMvc(controller).perform(patch("/orders/{orderId}/status", UUID.randomUUID())
                        .contentType("application/json").content("{\"orderStatus\":\"PAID\"}"))
                .andExpect(status().isNoContent());
    }
}
