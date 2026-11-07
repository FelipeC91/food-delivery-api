package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.domain.model.User;
import com.mypersonalportifolio.food_delivery_api.domain.repository.UserRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.UserService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.user.UserGroupController;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.UserGroupRepresentationModelAssembler;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserGroupControllerMockMvcTest extends MockMvcTestSupport {

    @Test
    void handlesListRequest() throws Exception {
        var userRepository = mock(UserRepository.class);
        when(userRepository.findById(any())).thenReturn(Optional.of(mock(User.class)));
        var controller = new UserGroupController(userRepository, mock(UserService.class),
                mock(UserGroupRepresentationModelAssembler.class));

        mockMvc(controller).perform(get("/users/{userId}/groups", UUID.randomUUID()))
                .andExpect(status().isOk());
    }
}
