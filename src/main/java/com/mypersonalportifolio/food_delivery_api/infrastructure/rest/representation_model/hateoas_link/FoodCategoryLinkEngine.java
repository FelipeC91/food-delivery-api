package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.hateoas_link;

import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.FoodCategoryController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.UUID;

public class FoodCategoryLinkEngine {
    public static Link slashFoodCategory() {
        return WebMvcLinkBuilder.linkTo(FoodCategoryController.class).withRel("/food-categories");
    }

    public static Link slashFoodCategorySlashFoodCategoryId(UUID foodCategoryId) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FoodCategoryController.class).findSingleResource(foodCategoryId)).withSelfRel();
    }
}
