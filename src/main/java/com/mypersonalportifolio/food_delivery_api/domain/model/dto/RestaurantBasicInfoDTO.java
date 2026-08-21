package com.mypersonalportifolio.food_delivery_api.domain.model.dto;

import com.mypersonalportifolio.food_delivery_api.domain.model.PaymentMethod;
import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;

import java.math.BigDecimal;
import java.util.List;

public record RestaurantBasicInfoDTO(String name, BigDecimal shippingCost, String foodCategoryName, List<PaymentMethod> paymentMethods) {

    public RestaurantBasicInfoDTO(Restaurant r) {
        this(
                r.getName(),
                r.getShippingCost(),
                r.getFoodCategory().getName(),
                List.copyOf(r.getPaymentMethods())
        );
    }
}
