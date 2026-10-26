package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters;

import com.mypersonalportifolio.food_delivery_api.domain.model.UserGroup;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.user.UserGroupController;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.UserGroupRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.hateoas_link.GroupsLinkEngine;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class UserGroupRepresentationModelAssembler extends RepresentationModelAssemblerSupport<UserGroup, UserGroupRepresentationModel> {


    public UserGroupRepresentationModelAssembler() {
        super(UserGroupController.class, UserGroupRepresentationModel.class);
    }

    @Override
    public UserGroupRepresentationModel toModel(UserGroup entity) {
        var representationModel = new UserGroupRepresentationModel(entity);

        representationModel.add(GroupsLinkEngine.slashUserGroups(entity.getId()));
        representationModel.add(GroupsLinkEngine.slashUserGroupsSlashPermissions(entity.getId()));
        representationModel.add(GroupsLinkEngine.slashUserGroupsSlashPermissionsSlashPermissionIdDELETE(entity.getId(), null));

        return representationModel;
    }

    @Override
    public CollectionModel<UserGroupRepresentationModel> toCollectionModel(Iterable<? extends UserGroup> entities) {
        return super.toCollectionModel(entities)
                .add(GroupsLinkEngine.slashUserGroupsSlashPermissionsSlashPermissionIdPUT(entities.iterator().next().getId(), null));
    }
}
