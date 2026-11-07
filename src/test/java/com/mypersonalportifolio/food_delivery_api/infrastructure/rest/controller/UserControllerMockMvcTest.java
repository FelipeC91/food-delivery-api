package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.domain.repository.UserRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.UserService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.user.UserController;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.UserRepresentationModelAssembler;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerMockMvcTest extends MockMvcTestSupport {

    @Test
    void handlesListRequest() throws Exception {
        var controller = new UserController(mock(UserRepository.class), mock(UserService.class),
                mock(UserRepresentationModelAssembler.class));

        mockMvc(controller).perform(get("/users")).andExpect(status().isOk());
    }
}
