package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto;

import com.mypersonalportifolio.food_delivery_api.domain.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class OrderSimplifiedRepresentationModel extends RepresentationModel<OrderSimplifiedRepresentationModel> {
    private UUID id;
    private RestaurantSimplifiedRepresentationModel restaurant;
    private PaymentMethodRepresentationModel paymentMethod;
    private OffsetDateTime createdAt;
    private OffsetDateTime confirmedAt;
    private OffsetDateTime deliveredIn;
    private OrderStatus status;
    private String customerName;
    private BigDecimal shippingCost;
    private BigDecimal subtotal;
    private BigDecimal totalPrice;
}
