package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto;


import com.mypersonalportifolio.food_delivery_api.domain.model.FoodCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.UUID;

@Relation(collectionRelation = "foodCategories")
@Getter
@AllArgsConstructor
public class FoodCategoryRepresentationModel extends RepresentationModel<FoodCategoryRepresentationModel> {
    private UUID id;

    private String name;

    public FoodCategoryRepresentationModel(FoodCategory foodCategory) {
        this.id = foodCategory.getId();
        this.name = foodCategory.getName();
    }
}
