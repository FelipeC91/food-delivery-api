package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RootEntryPointControllerMockMvcTest extends MockMvcTestSupport {

    @Test
    void handlesRootRequest() throws Exception {
        mockMvc(new RootEntryPointController()).perform(get("/")).andExpect(status().isOk());
    }
}
