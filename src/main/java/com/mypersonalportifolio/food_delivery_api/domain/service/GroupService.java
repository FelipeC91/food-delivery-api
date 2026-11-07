package com.mypersonalportifolio.food_delivery_api.domain.service;

import com.mypersonalportifolio.food_delivery_api.domain.exception.BusinessConstraintsViolationException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.CandidateEntityInvalidException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityAlreadyExistsException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.Permission;
import com.mypersonalportifolio.food_delivery_api.domain.model.PermissionEncapsulation;
import com.mypersonalportifolio.food_delivery_api.domain.model.UserGroup;
import com.mypersonalportifolio.food_delivery_api.domain.repository.PermissionRepository;
import com.mypersonalportifolio.food_delivery_api.domain.repository.GroupRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final PermissionRepository permissionRepository;

    public GroupService(GroupRepository groupRepository, PermissionRepository permissionRepository) {
        this.groupRepository = groupRepository;
        this.permissionRepository = permissionRepository;
    }

    @Transactional
    public UserGroup updateProperties(UUID userGroupId, UserGroup userGroupSource) {
        if (groupRepository.existsByName(userGroupSource.getName()))
            throw new EntityAlreadyExistsException(userGroupSource.getClass(), userGroupSource.getName());

        var userGroupTarget = findVerifiedUserGroup(userGroupId);

        userGroupTarget.setName(userGroupSource.getName());

        var updatedUserGroup = groupRepository.saveAndFlush(userGroupTarget);

        log.debug("Updated user group {} with name {}", userGroupTarget.getId().toString(), userGroupTarget.getName());
        return updatedUserGroup;
    }

    @Transactional(readOnly = true)
    public Set<Permission> findPermissions(UUID userGroupId) {
        var userGroup = findVerifiedUserGroup(userGroupId);
        return userGroup.getPermissions();
    }

    @Transactional
    public Permission grantPermission(UUID userGroupId, UUID permissionId) {
        var userGroup = findVerifiedUserGroup(userGroupId);
        var permission = findPermission(permissionId);


        if (!userGroup.attachPermission(permission))
            throw new BusinessConstraintsViolationException("Permissão já está associada ao grupo.");

        groupRepository.saveAndFlush(userGroup);

        log.debug("Granted permission {} to {}", permission.toString(), userGroup.toString());

        return permission;
    }

    @Transactional
    public void revokePermission(UUID userGroupId, UUID permissionId) {
        var userGroup = findVerifiedUserGroup(userGroupId);

        var permission = findPermission(permissionId);

        userGroup.detachPermission(permission);

        log.debug("Revoked permission {} to {}", permission.toString(), userGroup.toString());

        groupRepository.saveAndFlush(userGroup);
    }

    public UserGroup findVerifiedUserGroup(UUID userGroupId) {
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

    public UserGroup validateAndCreate(@Valid UserGroup userGroupCandidate) {
        try {
            if (groupRepository.existsByName(userGroupCandidate.getName()))
                throw new EntityAlreadyExistsException(userGroupCandidate.getClass(), userGroupCandidate.getName());

            var savedUserGroup = groupRepository.save(userGroupCandidate);

            log.debug("Group has been created {}", savedUserGroup.toString());

            return savedUserGroup;
        } catch (IllegalArgumentException e) {
            throw new CandidateEntityInvalidException(UserGroup.class, userGroupCandidate.getName());
        }
    }

    public boolean checkIfContainsProtectedPermissions(UUID userGroupId) {
        var targetGroup = this.findVerifiedUserGroup(userGroupId);

        return targetGroup.getPermissions()
                .stream()
                .map(Permission::getEncapsulation)
                .anyMatch(PermissionEncapsulation.PROTECTED::equals);
    }
}
