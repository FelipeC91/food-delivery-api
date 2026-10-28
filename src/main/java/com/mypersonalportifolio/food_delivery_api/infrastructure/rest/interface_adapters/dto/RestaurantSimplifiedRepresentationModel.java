package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.util.UUID;

@Relation(value = "restaurant", collectionRelation = "restaurant")
@Getter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RestaurantSimplifiedRepresentationModel extends RepresentationModel<RestaurantSimplifiedRepresentationModel> {
    private UUID id;
    private String name;
    private BigDecimal shippingCost;
    private String foodCategoryName;
}
