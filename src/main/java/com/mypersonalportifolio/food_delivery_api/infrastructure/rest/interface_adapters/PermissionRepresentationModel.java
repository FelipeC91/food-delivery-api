package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters;

import com.mypersonalportifolio.food_delivery_api.domain.model.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class PermissionRepresentationModel extends RepresentationModel<PermissionRepresentationModel> {
    private UUID id;
    private String name;
    private String description;

    public PermissionRepresentationModel(Permission entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
    }
}
