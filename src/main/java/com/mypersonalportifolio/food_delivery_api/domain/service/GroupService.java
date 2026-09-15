package com.mypersonalportifolio.food_delivery_api.domain.service;

import com.mypersonalportifolio.food_delivery_api.domain.exception.BusinessConstraintsViolationException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.Permission;
import com.mypersonalportifolio.food_delivery_api.domain.model.UserGroup;
import com.mypersonalportifolio.food_delivery_api.domain.repository.PermissionRepository;
import com.mypersonalportifolio.food_delivery_api.domain.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Transactional
    public UserGroup updateProperties(UserGroup userGroupTarget, UserGroup userGroupSource) {
        userGroupTarget.setName(userGroupSource.getName());

        return groupRepository.saveAndFlush(userGroupTarget);
    }

    @Transactional(readOnly = true)
    public Set<Permission> findPermissions(UUID userGroupId) {
        var userGroup = findUserGroup(userGroupId);
        return userGroup.getPermissions();
    }

    @Transactional
    public Permission addPermission(UUID userGroupId, UUID permissionId) {
        var userGroup = findUserGroup(userGroupId);
        var permission = findPermission(permissionId);

        if (!userGroup.attachPermission(permission)) {
            throw new BusinessConstraintsViolationException(
                    "Permissão já está associada ao grupo."
            );
        }

        groupRepository.saveAndFlush(userGroup);
        return permission;
    }

    @Transactional
    public void removePermission(UUID userGroupId, UUID permissionId) {
        var userGroup = findUserGroup(userGroupId);

        var permission = findPermission(permissionId);

        userGroup.detachPermission(permission);
        groupRepository.saveAndFlush(userGroup);
    }

    private UserGroup findUserGroup(UUID userGroupId) {
        return groupRepository.findById(userGroupId)
                .orElseThrow(() -> new EntityNotFoundException(
                        UserGroup.class,
                        userGroupId.toString()
                ));
    }

    private Permission findPermission(UUID permissionId) {
        return permissionRepository.findById(permissionId)
                .orElseThrow(() -> new EntityNotFoundException(
                        Permission.class,
                        permissionId.toString()
                ));
    }
}
