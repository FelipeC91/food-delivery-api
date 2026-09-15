package com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.output;

import com.mypersonalportifolio.food_delivery_api.domain.model.OrderStatus;
import com.mypersonalportifolio.food_delivery_api.domain.model.PaymentMethod;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record OrderBasicInfoOutputDTO(
        RestaurantBasicInfoOutputDTO restaurant,
        PaymentMethod paymentMethod,
        OffsetDateTime createdAt,
        OffsetDateTime confirmedAt,
        OffsetDateTime deliveredIn,
        OrderStatus orderStatus,
        String customerName,
        BigDecimal shippingCost,
        BigDecimal subtotal,
        BigDecimal totalPrice
) {
}
