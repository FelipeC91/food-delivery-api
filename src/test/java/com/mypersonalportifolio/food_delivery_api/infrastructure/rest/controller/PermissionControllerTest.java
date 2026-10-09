package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.Permission;
import com.mypersonalportifolio.food_delivery_api.domain.repository.PermissionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @InjectMocks
    private PermissionController permissionController;

    @Test
    void shouldListAllPermissions() {
        var permissions = List.of(
                new Permission("READ_ORDERS", "Read orders"),
                new Permission("MANAGE_USERS", "Manage users")
        );
        when(permissionRepository.findAll()).thenReturn(permissions);

        var result = permissionController.listAllResources();

        assertSame(permissions, result);
        verify(permissionRepository).findAll();
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
