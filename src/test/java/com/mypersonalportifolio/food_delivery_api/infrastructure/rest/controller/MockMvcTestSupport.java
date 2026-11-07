package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;

abstract class MockMvcTestSupport {

    protected MockMvc mockMvc(Object controller) {
        return MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @SuppressWarnings("unchecked")
    protected <T> PagedResourcesAssembler<T> mockPagedResourcesAssembler() {
        return mock(PagedResourcesAssembler.class);
    }
}
