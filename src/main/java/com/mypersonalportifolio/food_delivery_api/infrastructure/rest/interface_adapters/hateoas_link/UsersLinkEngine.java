package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.hateoas_link;

import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.user.UserController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.UUID;

public class UsersLinkEngine {

    public static Link slashUsers() {
        return WebMvcLinkBuilder.linkTo(UserController.class).withRel("/users");
    }

    public static Link slashUsersUserId(UUID userId) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).findOneResource(userId)).withSelfRel();
    }
}
