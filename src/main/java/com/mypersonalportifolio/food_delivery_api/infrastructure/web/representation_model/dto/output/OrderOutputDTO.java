package com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.output;

import com.mypersonalportifolio.food_delivery_api.domain.model.OrderStatus;
import com.mypersonalportifolio.food_delivery_api.domain.model.PaymentMethod;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record OrderOutputDTO(
        UUID id,
        RestaurantBasicInfoOutputDTO restaurant,
        PaymentMethod paymentMethod,
        OffsetDateTime confirmedAt,
        OrderStatus status,
        String customerName,
        AddressOutputDTO shippingAddress,
        BigDecimal shippingCost,
        BigDecimal subtotal,
        BigDecimal totalPrice,
        List<OrderItemOutputDTO> items

) {
    public record OrderItemOutputDTO (
            UUID productId,
            String productName,
            BigDecimal productPrice,
            Integer quantity,
            BigDecimal unitPrice,
            BigDecimal totalPrice,
            String note
    ){}

}
