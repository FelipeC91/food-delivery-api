package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.hateoas_link;

import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.GroupController;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.GroupPermissionController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.UUID;

public class GroupsLinkEngine extends ApiBasePathLinkResolver{
    public static Link slashUserGroups() {
        return withApiBasePath(WebMvcLinkBuilder.linkTo(GroupController.class).withRel("/groups"));
    }

    public static Link slashUserGroups(UUID userGroupId) {
        return withApiBasePath(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GroupController.class).findOneResource(userGroupId)).withSelfRel());
    }

    public static Link slashUserGroupsSlashPermissions(UUID userGroupId) {
        return withApiBasePath(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GroupPermissionController.class).listPermissions(userGroupId)).withSelfRel());
    }

    public static Link slashUserGroupsSlashPermissionsSlashPermissionIdPUT(UUID userGroupId, UUID permissionId) {
        return withApiBasePath(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GroupPermissionController.class).associateResource(userGroupId, permissionId, null))
                .withRel("/grant")
                .andAffordance(WebMvcLinkBuilder.afford(WebMvcLinkBuilder.methodOn(GroupPermissionController.class).associateResource(userGroupId, permissionId, null))));
    }

    public static Link slashUserGroupsSlashPermissionsSlashPermissionIdDELETE(UUID userGroupId, UUID permissionId) {
        return withApiBasePath(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GroupPermissionController.class).detachResource(userGroupId, permissionId, null))
                .withRel("/revoke")
                .andAffordance(WebMvcLinkBuilder.afford(WebMvcLinkBuilder.methodOn(GroupPermissionController.class).detachResource(userGroupId, permissionId, null))));
    }
}

