package com.mypersonalportifolio.food_delivery_api.domain.exception;

import java.util.UUID;

public class RestaurantCantBeClosedException extends BusinessConstraintsViolationException {
    private static final String STD_TEMPLATE_MESSAGE = "Restaurante %s não pode ser fechado pois possui pédidos em aberto";
    public RestaurantCantBeClosedException(UUID restaurantTargetId) {
        super(String.format(STD_TEMPLATE_MESSAGE, restaurantTargetId.toString()));
    }
}
