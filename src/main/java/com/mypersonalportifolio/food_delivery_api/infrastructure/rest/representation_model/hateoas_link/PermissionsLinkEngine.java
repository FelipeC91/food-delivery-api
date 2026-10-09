package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.hateoas_link;

import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.PermissionController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.UUID;

public class PermissionsLinkEngine {

    public static Link slashPermissions() {
        return WebMvcLinkBuilder.linkTo(PermissionController.class).withRel("/permissions");
    }

    public static Link slashPermissions(UUID permissionId) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PermissionController.class).getResourceById(permissionId)).withSelfRel();
    }

}
