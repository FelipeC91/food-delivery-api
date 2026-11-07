package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.domain.exception.CandidateEntityInvalidException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityIntegrityViolationException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.Permission;
import com.mypersonalportifolio.food_delivery_api.domain.model.PermissionEncapsulation;
import com.mypersonalportifolio.food_delivery_api.domain.model.UserGroup;
import com.mypersonalportifolio.food_delivery_api.domain.repository.GroupRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.GroupService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.UserGroupRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.UserGroupRepresentationModelAssembler;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi.controller.GroupControllerOpenApi;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.security.resource_server.CustomSecurityPolicies;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/groups")
public class GroupController implements GroupControllerOpenApi {

    private final GroupRepository groupRepository;
    private final GroupService userGroupService;
    private final UserGroupRepresentationModelAssembler assembler;
    private final PagedResourcesAssembler<UserGroup> pagedResourcesAssembler;
    private final CustomSecurityPolicies customSecurityPolicies;

    public GroupController(GroupRepository groupRepository, GroupService userGroupService, UserGroupRepresentationModelAssembler assembler, PagedResourcesAssembler<UserGroup> pagedResourcesAssembler, CustomSecurityPolicies customSecurityPolicies) {
        this.groupRepository = groupRepository;
        this.userGroupService = userGroupService;
        this.assembler = assembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.customSecurityPolicies = customSecurityPolicies;
    }

    @GetMapping
    public PagedModel<UserGroupRepresentationModel> listAllResources(@PageableDefault(size = 10) Pageable pageable) {
        return pagedResourcesAssembler.toModel(groupRepository.findAll(pageable), assembler);
    }

    @GetMapping("/{userGroupId}")
    public ResponseEntity<UserGroupRepresentationModel> findOneResource(@PathVariable UUID userGroupId) {
        var userGroup = assembler.toModel(userGroupService.findVerifiedUserGroup(userGroupId));
        return ResponseEntity.ok(userGroup);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserGroupRepresentationModel createResource(@RequestBody @Valid UserGroup userGroupCandidate) {
        return assembler.toModel(userGroupService.validateAndCreate(userGroupCandidate));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{userGroupId}")
    public ResponseEntity<Void> updateResource(@PathVariable UUID userGroupId,
                                                     @RequestBody @Valid UserGroup userGroupSource,
                                                       Authentication authentication) {
        var isProtected = userGroupService.checkIfContainsProtectedPermissions(userGroupId);

        if (isProtected) {
            if (customSecurityPolicies.hasAccessToProtectedPermissions(authentication)) {
                assembler.toModel(userGroupService.updateProperties(userGroupId, userGroupSource));

                return ResponseEntity.noContent().build();

            } else
              return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        assembler.toModel(userGroupService.updateProperties(userGroupId, userGroupSource));

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userGroupId}")
    public ResponseEntity<Void> deleteResource(@PathVariable UUID userGroupId,
                                               Authentication authentication) {
        try {
            var isProtected = userGroupService.checkIfContainsProtectedPermissions(userGroupId);

            if (isProtected) {
                if (customSecurityPolicies.hasAccessToProtectedPermissions(authentication)) {
                    groupRepository.deleteById(userGroupId);

                    return ResponseEntity.noContent().build();

                } else
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

            }

            groupRepository.deleteById(userGroupId);

            return ResponseEntity.noContent().build();

        } catch (IllegalArgumentException e) {
            throw new EntityNotFoundException(UserGroup.class, userGroupId.toString());
        } catch (DataIntegrityViolationException e) {
            throw new EntityIntegrityViolationException(UserGroup.class, userGroupId.toString());
        }
    }
}
