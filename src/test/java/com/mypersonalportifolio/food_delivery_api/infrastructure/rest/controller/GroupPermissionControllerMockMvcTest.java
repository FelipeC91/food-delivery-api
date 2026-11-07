package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.domain.service.GroupService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.PermissionRepresentationModelAssembler;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GroupPermissionControllerMockMvcTest extends MockMvcTestSupport {

    @Test
    void handlesListRequest() throws Exception {
        var controller = new GroupPermissionController(mock(GroupService.class),
                mock(PermissionRepresentationModelAssembler.class));

        mockMvc(controller).perform(get("/groups/{groupId}/permissions", UUID.randomUUID()))
                .andExpect(status().isOk());
    }
}
