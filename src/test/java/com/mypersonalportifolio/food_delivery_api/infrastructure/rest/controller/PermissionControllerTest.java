package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.Permission;
import com.mypersonalportifolio.food_delivery_api.domain.repository.PermissionRepository;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.PermissionRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.PermissionRepresentationModelAssembler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @InjectMocks
    private PermissionController permissionController;

    @Test
    void shouldListAllPermissions() {
        var permissions = List.of(
                new Permission("READ_ORDERS", "Read orders"),
                new Permission("MANAGE_USERS", "Manage users")
        );
        var pageable = Pageable.unpaged();
        var permissionPage = new PageImpl<>(permissions, pageable, permissions.size());
        when(permissionRepository.findAll(pageable)).thenReturn(permissionPage);
        when(pagedResourcesAssembler.toModel(permissionPage, assembler)).thenReturn(pagedModel);

        var result = permissionController.listAllResources(pageable);

        assertSame(pagedModel, result);
        verify(permissionRepository).findAll(pageable);
        verify(pagedResourcesAssembler).toModel(permissionPage, assembler);
    }

    @Test
    void shouldFindPermissionById() {
        var permissionId = UUID.randomUUID();
        var permission = new Permission("READ_ORDERS", "Read orders");
        when(permissionRepository.findById(permissionId)).thenReturn(Optional.of(permission));

        var result = permissionController.getResourceById(permissionId);

        assertSame(permission, result);
        verify(permissionRepository).findById(permissionId);
    }

    @Test
    void shouldThrowWhenPermissionDoesNotExist() {
        var permissionId = UUID.randomUUID();
        when(permissionRepository.findById(permissionId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> permissionController.getResourceById(permissionId));
        verify(permissionRepository).findById(permissionId);
    }
}
