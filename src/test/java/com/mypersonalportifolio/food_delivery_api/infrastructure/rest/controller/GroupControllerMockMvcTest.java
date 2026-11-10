package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.domain.repository.GroupRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.GroupService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.UserGroupRepresentationModelAssembler;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.security.resource_server.CustomSecurityPolicies;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GroupControllerMockMvcTest extends MockMvcTestSupport {

    @Test
    void handlesListRequest() throws Exception {
        var controller = new GroupController(mock(GroupRepository.class), mock(GroupService.class),
                mock(UserGroupRepresentationModelAssembler.class), mockPagedResourcesAssembler(),
                mock(CustomSecurityPolicies.class));

        mockMvc(controller).perform(get("/groups")).andExpect(status().isOk());
    }
}
