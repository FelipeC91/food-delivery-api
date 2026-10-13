package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.util.UUID;

@Relation(value = "order", collectionRelation = "order")
@Getter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderItemRepresentationModel extends RepresentationModel<OrderItemRepresentationModel> {
    private UUID productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private String note;
}
