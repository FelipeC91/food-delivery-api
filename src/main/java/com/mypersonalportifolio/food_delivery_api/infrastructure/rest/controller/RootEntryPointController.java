package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.hateoas_link.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping({"", "/"})
@RestController
public class RootEntryPointController {

    @GetMapping
    public ResponseEntity<RootEntryPointRepresentationModel> rootEntryPoint() {
        var rootEntryPointModel = new RootEntryPointRepresentationModel();

        rootEntryPointModel.add(FoodCategoryLinkEngine.slashFoodCategory());
        rootEntryPointModel.add(RestaurantLinkEngine.slashRestaurants());
        rootEntryPointModel.add(OrdersLinkEngine.slashOrders());
        rootEntryPointModel.add(PaymentMethodsLinkEngine.slashPaymentMethods());
        rootEntryPointModel.add(GroupsLinkEngine.slashUserGroups());
        rootEntryPointModel.add(UsersLinkEngine.slashUsers());
        rootEntryPointModel.add(PermissionsLinkEngine.slashPermissions());
        rootEntryPointModel.add(CitiesLinkEngine.slashCities());
        rootEntryPointModel.add(PermissionsLinkEngine.slashPermissions());

        return ResponseEntity.ok(rootEntryPointModel);
    }

    private static class RootEntryPointRepresentationModel extends RepresentationModel<RootEntryPointRepresentationModel> {}
}
