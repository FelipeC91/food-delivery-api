package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model;

import com.mypersonalportifolio.food_delivery_api.domain.model.FoodCategory;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.FoodCategoryController;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto.FoodCategoryRepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class FoodCategoryRepresentationModelAssembler extends RepresentationModelAssemblerSupport<FoodCategory, FoodCategoryRepresentationModel> {

    public FoodCategoryRepresentationModelAssembler() {
        super(FoodCategoryController.class, FoodCategoryRepresentationModel.class);
    }

    @Override
    public FoodCategoryRepresentationModel toModel(FoodCategory entity) {
        var representationModel = new FoodCategoryRepresentationModel(entity);

        return representationModel.add(WebMvcLinkBuilder.linkTo(FoodCategoryController.class).withRel("/food-categories"))
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FoodCategoryController.class).findSingleResource(entity.getId())).withSelfRel());
    }
}
