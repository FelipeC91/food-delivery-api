package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto;

import com.mypersonalportifolio.food_delivery_api.domain.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class OrderRepresentationModel extends RepresentationModel<OrderRepresentationModel> {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime confirmedAt;
    private OrderStatus status;
    private BigDecimal shippingCost;
    private BigDecimal subtotal;
    private BigDecimal totalPrice;
    private PaymentMethodRepresentationModel paymentMethod;//
    private RestaurantSimplifiedRepresentationModel restaurant;//
    private UserRepresentationModel customer;//
    private AddressRepresentationModel shippingAddress;//
    private List<OrderItemRepresentationModel> items;
}


