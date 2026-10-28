package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto;

import com.mypersonalportifolio.food_delivery_api.domain.model.Permission;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(value = "permission", collectionRelation = "permissions")
@Getter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PermissionRepresentationModel extends RepresentationModel<PermissionRepresentationModel> {
    private String name;
    private String description;

    public PermissionRepresentationModel(Permission permission) {
        this.name = permission.getName();
        this.description = permission.getDescription();
    }
}
