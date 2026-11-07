package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.Permission;
import com.mypersonalportifolio.food_delivery_api.domain.model.PermissionEncapsulation;
import com.mypersonalportifolio.food_delivery_api.domain.repository.PermissionRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.GroupService;
import com.mypersonalportifolio.food_delivery_api.domain.service.PermissionService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.PermissionRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.PermissionRepresentationModelAssembler;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi.controller.GroupPermissionControllerOpenApi;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.security.resource_server.CustomSecurityPolicies;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/groups/{userGroupId}/permissions")
public class GroupPermissionController implements GroupPermissionControllerOpenApi {

    private final GroupService userGroupService;
    private final PermissionRepresentationModelAssembler assembler;
    private final PermissionService permissionService;
    private final CustomSecurityPolicies customSecurityPolicies;

    public GroupPermissionController(GroupService userGroupService,
                                     PermissionRepresentationModelAssembler assembler,
                                     PermissionService permissionService,
                                     CustomSecurityPolicies customSecurityPolicies) {
        this.userGroupService = userGroupService;
        this.assembler = assembler;
        this.permissionService = permissionService;
        this.customSecurityPolicies = customSecurityPolicies;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<PermissionRepresentationModel>> listPermissions(@PathVariable UUID userGroupId) {
        var permissions = assembler.toCollectionModel(userGroupService.findPermissions(userGroupId));

        return ResponseEntity.ok(permissions);
    }

    @PutMapping("/{permissionId}")
    public ResponseEntity<Void> associateResource(@PathVariable UUID userGroupId,
                                                  @PathVariable UUID permissionId,
                                                  Authentication authentication) {
        var validPermission = permissionService.findValidPermission(permissionId);

        if (validPermission.getEncapsulation() == PermissionEncapsulation.PROTECTED) {
            if (customSecurityPolicies.hasAccessToProtectedPermissions(authentication)) {
                userGroupService.grantPermission(userGroupId, permissionId);
                return ResponseEntity.noContent().build();

            } else
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        userGroupService.grantPermission(userGroupId, permissionId);

        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{permissionId}")
    public ResponseEntity<Void> detachResource(@PathVariable UUID userGroupId,
                                                @PathVariable UUID permissionId,
                                               Authentication authentication) {
        var validPermission = permissionService.findValidPermission(permissionId);

        if (validPermission.getEncapsulation() == PermissionEncapsulation.PROTECTED) {
            if (customSecurityPolicies.hasAccessToProtectedPermissions(authentication)) {
                userGroupService.revokePermission(userGroupId, permissionId);
                return ResponseEntity.noContent().build();
            } else
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        }

        userGroupService.revokePermission(userGroupId, permissionId);
        return ResponseEntity.noContent().build();
    }
}
