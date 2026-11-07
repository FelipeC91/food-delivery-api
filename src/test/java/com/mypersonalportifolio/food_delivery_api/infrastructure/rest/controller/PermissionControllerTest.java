package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.Permission;
import com.mypersonalportifolio.food_delivery_api.domain.model.PermissionEncapsulation;
import com.mypersonalportifolio.food_delivery_api.domain.repository.PermissionRepository;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.PermissionRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.PermissionRepresentationModelAssembler;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.security.resource_server.CustomSecurityPolicies;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PermissionControllerTest {

    @Mock
    private PermissionRepository permissionRepository;

    @Mock
    private PermissionRepresentationModelAssembler assembler;

    @Mock
    private PagedResourcesAssembler<Permission> pagedResourcesAssembler;

    @Mock
    private PagedModel<PermissionRepresentationModel> pagedModel;

    @Mock
    private CustomSecurityPolicies customSecurityPolicies;

    @Mock
    private Authentication authentication;

    @Mock
    private PermissionRepresentationModel permissionRepresentationModel;

    @InjectMocks
    private PermissionController permissionController;

    @Test
    void shouldListAllPermissions() {
        var permissions = List.of(
                new Permission("READ_ORDERS", "Read orders", PermissionEncapsulation.PUBLIC),
                new Permission("MANAGE_USERS", "Manage users", PermissionEncapsulation.PROTECTED)
        );
        var pageable = Pageable.unpaged();
        var permissionPage = new PageImpl<>(permissions, pageable, permissions.size());
        when(customSecurityPolicies.hasAccessToProtectedPermissions(authentication)).thenReturn(true);
        when(permissionRepository.findAll(pageable)).thenReturn(permissionPage);
        when(pagedResourcesAssembler.toModel(permissionPage, assembler)).thenReturn(pagedModel);

        var result = permissionController.listAllResources(pageable, authentication);

        assertSame(pagedModel, result);
        verify(customSecurityPolicies).hasAccessToProtectedPermissions(authentication);
        verify(permissionRepository).findAll(pageable);
        verify(pagedResourcesAssembler).toModel(permissionPage, assembler);
    }

    @Test
    void shouldFindPermissionById() {
        var permissionId = UUID.randomUUID();
        var permission = new Permission("READ_ORDERS", "Read orders", PermissionEncapsulation.PUBLIC);
        when(permissionRepository.findById(permissionId)).thenReturn(Optional.of(permission));
        when(assembler.toModel(permission)).thenReturn(permissionRepresentationModel);

        var result = permissionController.getResourceById(permissionId, authentication);

        assertEquals(200, result.getStatusCode().value());
        assertSame(permissionRepresentationModel, result.getBody());
        verify(permissionRepository).findById(permissionId);
        verify(assembler).toModel(permission);
    }

    @Test
    void shouldThrowWhenPermissionDoesNotExist() {
        var permissionId = UUID.randomUUID();
        when(permissionRepository.findById(permissionId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> permissionController.getResourceById(permissionId, authentication));
        verify(permissionRepository).findById(permissionId);
    }
}
