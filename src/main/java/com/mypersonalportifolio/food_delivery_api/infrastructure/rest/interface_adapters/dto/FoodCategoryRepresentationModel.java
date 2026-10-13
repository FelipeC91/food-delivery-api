package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto;


import com.mypersonalportifolio.food_delivery_api.domain.model.FoodCategory;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.UUID;

@Relation(value = "foodCategory",collectionRelation = "foodCategories")
@Getter
@EqualsAndHashCode(callSuper = true)
public class FoodCategoryRepresentationModel extends RepresentationModel<FoodCategoryRepresentationModel> {
    private UUID id;

    private String name;

    public FoodCategoryRepresentationModel(FoodCategory foodCategory) {
        this.id = foodCategory.getId();
        this.name = foodCategory.getName();
    }
}
