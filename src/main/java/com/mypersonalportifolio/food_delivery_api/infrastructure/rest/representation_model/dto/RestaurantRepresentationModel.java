package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Getter
@AllArgsConstructor
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
