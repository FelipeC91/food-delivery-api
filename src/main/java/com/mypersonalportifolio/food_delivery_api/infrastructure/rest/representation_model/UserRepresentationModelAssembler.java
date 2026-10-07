package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model;

import com.mypersonalportifolio.food_delivery_api.domain.model.User;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.user.UserController;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.user.UserGroupController;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto.UserRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.mapstruct.UserMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserRepresentationModelAssembler extends RepresentationModelAssemblerSupport<User, UserRepresentationModel> {

    private final UserMapper userMapper;


    public UserRepresentationModelAssembler(UserMapper userMapper) {
        super(UserController.class, UserRepresentationModel.class);
        this.userMapper = userMapper;
    }

    @Override
    public UserRepresentationModel toModel(User entity) {
        var representationModel = userMapper.toRepresentationModel(entity);

        return assembleHateoasLinks(representationModel, entity.getId());
    }

    private UserRepresentationModel assembleHateoasLinks(UserRepresentationModel representationModel, UUID userId) {
        representationModel.add(WebMvcLinkBuilder.linkTo(UserController.class).withRel("/users"));
        representationModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserGroupController.class).getUserGroups(userId)).withRel("/user-groups"));

        return representationModel;
    }

    @Override
    public CollectionModel<UserRepresentationModel> toCollectionModel(Iterable<? extends User> entities) {
        return super.toCollectionModel(entities)
                .add( WebMvcLinkBuilder.linkTo(UserController.class).withSelfRel() );
    }
}
