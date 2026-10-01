package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto.output;

import com.mypersonalportifolio.food_delivery_api.domain.model.OrderStatus;
import com.mypersonalportifolio.food_delivery_api.domain.model.PaymentMethod;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record OrderBasicInfoOutputDTO(
        UUID id,
        RestaurantBasicInfoOutputDTO restaurant,
        PaymentMethod paymentMethod,
        OffsetDateTime createdAt,
        //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        OffsetDateTime confirmedAt,
        OffsetDateTime deliveredIn,
        OrderStatus orderStatus,
        String customerName,
        BigDecimal shippingCost,
        BigDecimal subtotal,
        BigDecimal totalPrice
) {
}
