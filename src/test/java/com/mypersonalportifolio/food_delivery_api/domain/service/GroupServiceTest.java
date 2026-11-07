package com.mypersonalportifolio.food_delivery_api.domain.service;

import com.mypersonalportifolio.food_delivery_api.domain.model.Permission;
import com.mypersonalportifolio.food_delivery_api.domain.model.PermissionEncapsulation;
import com.mypersonalportifolio.food_delivery_api.domain.model.UserGroup;
import com.mypersonalportifolio.food_delivery_api.domain.repository.GroupRepository;
import com.mypersonalportifolio.food_delivery_api.domain.repository.PermissionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private PermissionRepository permissionRepository;

    @InjectMocks
    private GroupService groupService;

    @Test
    void shouldGrantPermissionToExistingGroup() {
        var groupId = UUID.randomUUID();
        var permissionId = UUID.randomUUID();
        var group = new UserGroup();
        var permission = new Permission("READ_ORDERS", "Read orders", PermissionEncapsulation.PUBLIC);
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
        when(permissionRepository.findById(permissionId)).thenReturn(Optional.of(permission));

        var granted = groupService.grantPermission(groupId, permissionId);

        assertSame(permission, granted);
        assertTrue(group.getPermissions().contains(permission));
        verify(groupRepository).saveAndFlush(group);
    }
}
