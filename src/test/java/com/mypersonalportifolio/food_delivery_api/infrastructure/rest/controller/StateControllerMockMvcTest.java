package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.domain.repository.StateRepository;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StateControllerMockMvcTest extends MockMvcTestSupport {

    @Test
    void handlesListRequest() throws Exception {
        mockMvc(new StateController(mock(StateRepository.class)))
                .perform(get("/states")).andExpect(status().isOk());
    }
}
