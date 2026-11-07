package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.domain.repository.PaymentMethodRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.PaymentMethodService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.PaymentMethodRepresentationModelAssembler;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PaymentMethodControllerMockMvcTest extends MockMvcTestSupport {

    @Test
    void handlesListRequest() throws Exception {
        var controller = new PaymentMethodController(mock(PaymentMethodRepository.class),
                mock(PaymentMethodService.class), mock(PaymentMethodRepresentationModelAssembler.class));

        mockMvc(controller).perform(get("/payment-methods")).andExpect(status().isOk());
    }
}
