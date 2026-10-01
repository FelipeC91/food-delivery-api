package com.mypersonalportifolio.food_delivery_api.infrastructure.web.controller;

import com.mypersonalportifolio.food_delivery_api.domain.model.Permission;
import com.mypersonalportifolio.food_delivery_api.domain.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/groups/{userGroupId}/permissions")
public class GroupPermissionController {

    @Autowired
    private GroupService userGroupService;

    @GetMapping
    public ResponseEntity<Set<Permission>> listPermissions(@PathVariable UUID userGroupId) {
        return ResponseEntity.ok(userGroupService.findPermissions(userGroupId));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{permissionId}")
    public void addPermission(@PathVariable UUID userGroupId,
                                                    @PathVariable UUID permissionId) {
        userGroupService.addPermission(userGroupId, permissionId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{permissionId}")
    public void removePermission(@PathVariable UUID userGroupId,
                                 @PathVariable UUID permissionId) {
        userGroupService.removePermission(userGroupId, permissionId);

    }
}
