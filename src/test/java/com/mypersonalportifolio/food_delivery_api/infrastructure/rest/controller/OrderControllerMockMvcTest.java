package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.application.use_case.ProcessNewOrderUseCase;
import com.mypersonalportifolio.food_delivery_api.domain.model.Order;
import com.mypersonalportifolio.food_delivery_api.domain.repository.OrderRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.OrderService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.order.OrderController;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.OrderRepresentationModelAssembler;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.OrderSimplifiedRepresentationModelAssembler;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerMockMvcTest extends MockMvcTestSupport {

    @Test
    void handlesFindRequest() throws Exception {
        var orderService = mock(OrderService.class);
        when(orderService.findValidOrder(any())).thenReturn(mock(Order.class));
        var controller = new OrderController(mock(OrderRepository.class), orderService,
                mock(ProcessNewOrderUseCase.class),
                mock(OrderRepresentationModelAssembler.class), mock(OrderSimplifiedRepresentationModelAssembler.class),
                mockPagedResourcesAssembler());

        mockMvc(controller).perform(get("/orders/{orderId}", UUID.randomUUID()))
                .andExpect(status().isOk());
    }
}
