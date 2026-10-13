package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Relation(value = "restaurant", collectionRelation = "restaurants")
@Getter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RestaurantRepresentationModel extends RepresentationModel<RestaurantRepresentationModel> {
    private UUID id;
    private String name;
    private AddressRepresentationModel address;
    private BigDecimal shippingCost;
    private FoodCategoryRepresentationModel foodCategory;
    private boolean active;
    private boolean open;
    private Set<UserRepresentationModel> managers;
    private Set<PaymentMethodRepresentationModel> paymentMethods;
}
