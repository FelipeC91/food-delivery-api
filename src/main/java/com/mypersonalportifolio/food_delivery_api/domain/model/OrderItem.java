package com.mypersonalportifolio.food_delivery_api.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @ManyToOne
    @JoinColumn(name = "product_id")
    @Setter
    private Product product;

    private Integer quantity;

    @Column(name = "unit_price")
    @Setter
    private BigDecimal unitPrice;

    @Setter
    @Column(name = "total_price")
    private BigDecimal totalPrice;

    private String note;

}
