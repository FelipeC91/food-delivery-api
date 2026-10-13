package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto;

import com.mypersonalportifolio.food_delivery_api.domain.model.UserGroup;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.UUID;

@Relation(value = "group", collectionRelation = "groups")
@Getter
@EqualsAndHashCode(callSuper = true)
public class UserGroupRepresentationModel extends RepresentationModel<UserGroupRepresentationModel> {
    private final UUID id;
    private final String name;

    public UserGroupRepresentationModel(UserGroup userGroup) {
        this.id = userGroup.getId();
        this.name = userGroup.getName();
    }
}
