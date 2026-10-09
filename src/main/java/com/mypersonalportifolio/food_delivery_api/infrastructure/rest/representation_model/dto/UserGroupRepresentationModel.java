package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto;

import com.mypersonalportifolio.food_delivery_api.domain.model.UserGroup;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Getter
public class UserGroupRepresentationModel extends RepresentationModel<UserGroupRepresentationModel> {
    private final UUID id;
    private final String name;

    public UserGroupRepresentationModel(UserGroup userGroup) {
        this.id = userGroup.getId();
        this.name = userGroup.getName();
    }
}
