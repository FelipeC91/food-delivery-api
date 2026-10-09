package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.domain.model.Permission;
import com.mypersonalportifolio.food_delivery_api.domain.service.GroupService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.PermissionRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.PermissionRepresentationModelAssembler;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.UserGroupRepresentationModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/groups/{userGroupId}/permissions")
public class GroupPermissionController {

    private final GroupService userGroupService;
    private final PermissionRepresentationModelAssembler assembler;

    public GroupPermissionController(GroupService userGroupService, PermissionRepresentationModelAssembler assembler) {
        this.userGroupService = userGroupService;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<PermissionRepresentationModel>> listPermissions(@PathVariable UUID userGroupId) {
        var permissions = assembler.toCollectionModel(userGroupService.findPermissions(userGroupId));

        return ResponseEntity.ok(permissions);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{permissionId}")
    public ResponseEntity<Void> associateResource(@PathVariable UUID userGroupId,
                                                    @PathVariable UUID permissionId) {
        userGroupService.grantPermission(userGroupId, permissionId);

        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{permissionId}")
    public ResponseEntity<Void> detachResource(@PathVariable UUID userGroupId,
                                   @PathVariable UUID permissionId) {
        userGroupService.revokePermission(userGroupId, permissionId);

        return ResponseEntity.noContent().build();
    }
}
