package com.mypersonalportifolio.food_delivery_api.domain.model.dto;

import com.mypersonalportifolio.food_delivery_api.domain.model.PaymentMethod;
import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record RestaurantBasicInfoDTO(UUID id, String name, BigDecimal shippingCost, UUID foodCategoryId, String foodCategoryName, List<PaymentMethod> paymentMethods) {

    public RestaurantBasicInfoDTO(Restaurant r) {
        this(
                r.getId(),
                r.getName(),
                r.getShippingCost(),
                r.getFoodCategory().getId(),
                r.getFoodCategory().getName(),
                List.copyOf(r.getPaymentMethods())
        );
    }
}
