package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.hateoas_link;

import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.FoodCategoryController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.UUID;

public class FoodCategoryLinkEngine extends ApiBasePathLinkResolver{
    public static Link slashFoodCategory() {
        return withApiBasePath(WebMvcLinkBuilder.linkTo(FoodCategoryController.class).withRel("/food-categories"));
    }

    public static Link slashFoodCategorySlashFoodCategoryId(UUID foodCategoryId) {
        return withApiBasePath(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FoodCategoryController.class).findSingleResource(foodCategoryId)).withSelfRel());
    }
}
