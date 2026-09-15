package com.mypersonalportifolio.food_delivery_api.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mypersonalportifolio.food_delivery_api.domain.concept.DomainEntityUUID;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Embeddable
//@Entity(name = "order_item")
@Getter
public class OrderItem //extends DomainEntityUUID {
{
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    private String note;

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Order order;
}
