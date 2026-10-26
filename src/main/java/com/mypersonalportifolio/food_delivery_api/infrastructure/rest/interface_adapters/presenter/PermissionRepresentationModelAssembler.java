package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters;

import com.mypersonalportifolio.food_delivery_api.domain.model.Permission;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.PermissionController;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.hateoas_link.GroupsLinkEngine;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.hateoas_link.PermissionsLinkEngine;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PermissionRepresentationModelAssembler extends RepresentationModelAssemblerSupport<Permission, PermissionRepresentationModel> {

    public PermissionRepresentationModelAssembler() {
        super(PermissionController.class, PermissionRepresentationModel.class);
    }

    @Override
    public PermissionRepresentationModel toModel(Permission entity) {
        var representationModel = new PermissionRepresentationModel(entity);

        representationModel.add(PermissionsLinkEngine.slashPermissions(entity.getId()))
                .add(GroupsLinkEngine.slashUserGroupsSlashPermissionsSlashPermissionIdDELETE(null, null));

        return  representationModel;
    }

    @Override
    public CollectionModel<PermissionRepresentationModel> toCollectionModel(Iterable<? extends Permission> entities) {
        return super.toCollectionModel(entities)
                .add(PermissionsLinkEngine.slashPermissions())
                .add(GroupsLinkEngine.slashUserGroupsSlashPermissionsSlashPermissionIdPUT(null, null));
    }
}
