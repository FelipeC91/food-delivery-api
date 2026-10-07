package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class RestaurantSimplifiedRepresentationModel extends RepresentationModel<RestaurantSimplifiedRepresentationModel> {
    private UUID id;
    private String name;
    private BigDecimal shippingCost;
}
