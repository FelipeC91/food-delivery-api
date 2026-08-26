package com.mypersonalportifolio.food_delivery_api.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mypersonalportifolio.food_delivery_api.domain.concept.DomainEntityUUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

import java.math.BigDecimal;

@Entity(name = "order_item")
@Getter
public class OrderItem extends DomainEntityUUID {

    @ManyToOne
    private Product product;

    private Integer quantity;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    private String observation;

    @JsonIgnore
    @ManyToOne
    private Order order;
}
