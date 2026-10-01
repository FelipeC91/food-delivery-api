package com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.output;

import com.mypersonalportifolio.food_delivery_api.domain.model.PaymentMethod;
import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record RestaurantOutputDTO(
        UUID id,
        String name,
        AddressOutputDTO address,
        BigDecimal shippingCost,
        UUID foodCategoryId,
        boolean active,
        List<PaymentMethod> paymentMethods

) {
    public RestaurantOutputDTO(Restaurant r) {
        this(
                r.getId(),
                r.getName(),
                new AddressOutputDTO(r.getAddress()),
                r.getShippingCost(),
                r.getFoodCategory().getId(),
                r.isActive(),
                List.copyOf(r.getAllowedPaymentMethods())
        );
    }
}
